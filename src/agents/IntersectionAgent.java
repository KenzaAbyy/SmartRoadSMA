package agents;

import constants.MessageTypes;
import ontology.TrafficState;
import jade.core.Agent;
import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.util.ArrayList;
import java.util.List;

public class IntersectionAgent extends Agent {

    private String intersectionId;
    private TrafficState trafficState;
    private List<String> neighborIntersections = new ArrayList<>();
    private String associatedLight;

    @Override
    protected void setup() {
        intersectionId = getLocalName();
        associatedLight = "light_" + intersectionId;

        // Initialisation état trafic avec l'ontologie
        trafficState = new TrafficState(intersectionId, "LOW", 50.0, 0, false);

        // Définir les voisins selon le nom de l'intersection
        if (intersectionId.equals("intersection_A")) {
            neighborIntersections.add("intersection_B");
            neighborIntersections.add("intersection_C");
        } else if (intersectionId.equals("intersection_B")) {
            neighborIntersections.add("intersection_A");
            neighborIntersections.add("intersection_C");
        } else {
            neighborIntersections.add("intersection_A");
            neighborIntersections.add("intersection_B");
        }

        System.out.println("[Intersection " + intersectionId
                + "] Démarrée. Voisins : " + neighborIntersections);

        // Comportement 1 : recevoir alertes congestion des véhicules
        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                MessageTemplate mt = MessageTemplate.and(
                        MessageTemplate.MatchPerformative(ACLMessage.INFORM),
                        MessageTemplate.MatchContent(MessageTypes.INFORM_CONGESTION + "*")
                );
                // Filtre simple sur performative
                MessageTemplate filter = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
                ACLMessage msg = myAgent.receive(filter);

                if (msg != null) {
                    String content = msg.getContent();
                    if (content != null && content.contains(MessageTypes.INFORM_CONGESTION)) {
                        System.out.println("[Intersection " + intersectionId
                                + "] 🚨 Congestion reçue de "
                                + msg.getSender().getLocalName() + " : " + content);

                        // Mettre à jour l'état
                        trafficState.setTrafficLevel("HIGH");
                        trafficState.setCongested(true);
                        trafficState.setVehicleCount(trafficState.getVehicleCount() + 1);

                        // Lancer la détection et négociation
                        addBehaviour(new OneShotBehaviour() {
                            @Override
                            public void action() {
                                detectAndNegotiate();
                            }
                        });
                    }
                } else {
                    block();
                }
            }
        });

        // Comportement 2 : répondre aux CFP des voisins
        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.CFP);
                ACLMessage cfp = myAgent.receive(mt);

                if (cfp != null) {
                    System.out.println("[Intersection " + intersectionId
                            + "] 📩 CFP reçu de " + cfp.getSender().getLocalName());

                    ACLMessage propose = cfp.createReply();
                    propose.setPerformative(ACLMessage.PROPOSE);

                    // Proposer capacité selon notre état actuel
                    String capacity = trafficState.isCongested() ? "low" : "high";
                    double score = trafficState.isCongested() ? 30.0 : 80.0;

                    propose.setContent(MessageTypes.PROPOSE
                            + ":intersection=" + intersectionId
                            + ":capacity=" + capacity
                            + ":score=" + score
                            + ":vehicleCount=" + trafficState.getVehicleCount());

                    send(propose);
                    System.out.println("[Intersection " + intersectionId
                            + "] 📤 PROPOSE envoyé : capacity=" + capacity
                            + ", score=" + score);

                } else {
                    block();
                }
            }
        });

        // Comportement 3 : recevoir ACCEPT ou REJECT
        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                MessageTemplate mt = MessageTemplate.or(
                        MessageTemplate.MatchPerformative(ACLMessage.ACCEPT_PROPOSAL),
                        MessageTemplate.MatchPerformative(ACLMessage.REJECT_PROPOSAL)
                );
                ACLMessage reply = myAgent.receive(mt);

                if (reply != null) {
                    if (reply.getPerformative() == ACLMessage.ACCEPT_PROPOSAL) {
                        System.out.println("[Intersection " + intersectionId
                                + "] ✅ ACCEPT_PROPOSAL reçu — je prends le flux redirigé !");
                        notifySupervisor("flow_redirected_to=" + intersectionId);
                        adaptTrafficLights("HIGH");
                    } else {
                        System.out.println("[Intersection " + intersectionId
                                + "] ❌ REJECT_PROPOSAL reçu.");
                    }
                } else {
                    block();
                }
            }
        });
    }

    private void detectAndNegotiate() {
        System.out.println("[Intersection " + intersectionId
                + "] ⚡ Congestion détectée → lancement Contract-Net");

        // Notifier superviseur
        notifySupervisor(MessageTypes.INFORM_CONGESTION
                + ":level=HIGH:intersection=" + intersectionId);

        // Adapter les feux locaux
        adaptTrafficLights("HIGH");

        // Lancer la négociation avec les voisins
        try { Thread.sleep(500); } catch (Exception e) {}
        launchContractNet();
    }

    private void launchContractNet() {
        System.out.println("[Intersection " + intersectionId
                + "] 📢 Envoi CFP aux voisins : " + neighborIntersections);

        for (String neighbor : neighborIntersections) {
            ACLMessage cfp = new ACLMessage(ACLMessage.CFP);
            cfp.addReceiver(new AID(neighbor, AID.ISLOCALNAME));
            cfp.setContent(MessageTypes.CFP
                    + ":from=" + intersectionId
                    + ":reason=CONGESTION"
                    + ":current_vehicles=" + trafficState.getVehicleCount());
            cfp.setConversationId("contract-net-" + intersectionId + "-" + System.currentTimeMillis());
            send(cfp);
            System.out.println("[Intersection " + intersectionId
                    + "] 📨 CFP envoyé à " + neighbor);
        }

        // Attendre les propositions
        try { Thread.sleep(2000); } catch (Exception e) {}

        // Évaluation simple : choisir le premier voisin
        if (!neighborIntersections.isEmpty()) {
            String best = neighborIntersections.get(0);
            String worst = neighborIntersections.size() > 1
                    ? neighborIntersections.get(1) : null;

            // Accepter le meilleur
            ACLMessage accept = new ACLMessage(ACLMessage.ACCEPT_PROPOSAL);
            accept.addReceiver(new AID(best, AID.ISLOCALNAME));
            accept.setContent(MessageTypes.ACCEPT_PROPOSAL
                    + ":winner=" + best
                    + ":redirect_from=" + intersectionId);
            send(accept);
            System.out.println("[Intersection " + intersectionId
                    + "] ✅ ACCEPT_PROPOSAL → " + best);

            // Rejeter les autres
            if (worst != null) {
                ACLMessage reject = new ACLMessage(ACLMessage.REJECT_PROPOSAL);
                reject.addReceiver(new AID(worst, AID.ISLOCALNAME));
                reject.setContent(MessageTypes.REJECT_PROPOSAL
                        + ":intersection=" + worst);
                send(reject);
                System.out.println("[Intersection " + intersectionId
                        + "] ❌ REJECT_PROPOSAL → " + worst);
            }

            // Informer le superviseur du résultat
            notifySupervisor("INFORM:flow_redirected_to=" + best
                    + ":from=" + intersectionId);
        }
    }

    private void adaptTrafficLights(String congestionLevel) {
        ACLMessage req = new ACLMessage(ACLMessage.REQUEST);
        req.addReceiver(new AID(associatedLight, AID.ISLOCALNAME));
        req.setContent(MessageTypes.REQUEST_SIGNAL_ADAPTATION
                + ":level=" + congestionLevel
                + ":intersection=" + intersectionId);
        send(req);
        System.out.println("[Intersection " + intersectionId
                + "] 🚦 Adaptation feux demandée → " + associatedLight);
    }

    private void notifySupervisor(String info) {
        ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
        msg.addReceiver(new AID("supervisor", AID.ISLOCALNAME));
        msg.setContent(MessageTypes.INFORM_CONGESTION
                + ":from=" + intersectionId
                + ":" + info);
        send(msg);
        System.out.println("[Intersection " + intersectionId
                + "] 📡 Superviseur notifié.");
    }

    @Override
    protected void takeDown() {
        System.out.println("[Intersection " + intersectionId + "] Arrêtée.");
    }
}