package agents;

import constants.MessageTypes;
import ontology.Node;
import ontology.VehicleInfo;
import jade.core.Agent;
import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class VehicleAgent extends Agent {

    private VehicleInfo vehicleInfo;
    private String currentIntersection = "intersection_A";

    @Override
    protected void setup() {
        // Initialisation avec l'ontologie de ton collègue
        Node currentPos = new Node("N1", "Zone_A", "URBAN");
        Node destination = new Node("N5", "Zone_C", "URBAN");

        vehicleInfo = new VehicleInfo(
                getLocalName(),
                "CAR",
                currentPos,
                destination,
                50.0,
                "MOVING"
        );

        System.out.println("[Vehicle " + vehicleInfo.getVehicleId()
                + "] Démarré à " + vehicleInfo.getCurrentPosition().getName()
                + " → destination : " + vehicleInfo.getDestination().getName());

        // Comportement 1 : signaler congestion après 1 seconde
        addBehaviour(new OneShotBehaviour() {
            @Override
            public void action() {
                try { Thread.sleep(1000); } catch (Exception e) {}
                sendCongestionAlert();
            }
        });

        // Comportement 2 : écouter route alternative du Superviseur
        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.PROPOSE);
                ACLMessage msg = myAgent.receive(mt);

                if (msg != null) {
                    String content = msg.getContent();
                    if (content != null && content.contains(MessageTypes.PROPOSE_ALTERNATIVE_ROUTE)) {
                        String newRoute = content.split(":").length > 1
                                ? content.split(":")[1] : "route_inconnue";
                        System.out.println("[Vehicle " + vehicleInfo.getVehicleId()
                                + "] 🔀 Route alternative reçue : " + newRoute);
                        updatePosition(newRoute);
                    }
                } else {
                    block();
                }
            }
        });

        // Comportement 3 : écouter état de la route (GatewayAgent)
        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
                ACLMessage msg = myAgent.receive(mt);

                if (msg != null) {
                    String content = msg.getContent();
                    if (content != null && content.contains(MessageTypes.INFORM_ROUTE_STATUS)) {
                        System.out.println("[Vehicle " + vehicleInfo.getVehicleId()
                                + "] 📡 Statut route reçu : " + content);
                    }
                } else {
                    block();
                }
            }
        });
    }

    private void sendCongestionAlert() {
        ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
        msg.addReceiver(new AID(currentIntersection, AID.ISLOCALNAME));

        msg.setContent(MessageTypes.INFORM_CONGESTION
                + ":vehicleId=" + vehicleInfo.getVehicleId()
                + ":position=" + vehicleInfo.getCurrentPosition().getName()
                + ":speed=" + vehicleInfo.getSpeed()
                + ":level=HIGH");

        send(msg);
        System.out.println("[Vehicle " + vehicleInfo.getVehicleId()
                + "] 🚨 Alerte congestion envoyée à " + currentIntersection);
    }

    private void updatePosition(String newRoute) {
        vehicleInfo.getCurrentPosition().setName(newRoute);
        vehicleInfo.setStatus("REROUTED");
        System.out.println("[Vehicle " + vehicleInfo.getVehicleId()
                + "] 📍 Position mise à jour → " + newRoute);
    }

    @Override
    protected void takeDown() {
        System.out.println("[Vehicle " + vehicleInfo.getVehicleId() + "] Arrêté.");
    }
}