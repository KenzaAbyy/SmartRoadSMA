package agents;

import constants.MessageTypes;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class TrafficLightAgent extends Agent {

    private String lightId;
    private String currentColor = "RED";
    private int normalCycleDuration = 30;
    private boolean emergencyMode = false;

    @Override
    protected void setup() {
        lightId = getLocalName();
        System.out.println("[TrafficLight " + lightId + "] Démarré - état : " + currentColor);

        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
                ACLMessage msg = myAgent.receive(mt);

                if (msg != null) {
                    String content = msg.getContent();

                    if (content != null && content.contains(MessageTypes.REQUEST_SIGNAL_ADAPTATION)) {
                        adaptCycle(content);

                        ACLMessage reply = msg.createReply();
                        reply.setPerformative(ACLMessage.INFORM);
                        reply.setContent(MessageTypes.INFORM_TRAFFIC_STATE
                                + ":color=" + currentColor
                                + ":duration=" + normalCycleDuration
                                + ":lightId=" + lightId);
                        send(reply);
                        System.out.println("[TrafficLight " + lightId
                                + "] ✅ Feu adapté → " + currentColor
                                + " (" + normalCycleDuration + "s)");
                    }

                    if (content != null && content.contains(MessageTypes.REQUEST_PRIORITY)) {
                        enableEmergencyPriority();
                    }

                } else {
                    block();
                }
            }
        });
    }

    private void adaptCycle(String content) {
        if (content.contains("HIGH")) {
            currentColor = "GREEN";
            normalCycleDuration = 60;
            System.out.println("[TrafficLight " + lightId
                    + "] 🟢 Congestion HIGH → cycle étendu à " + normalCycleDuration + "s");
        } else if (content.contains("LOW")) {
            currentColor = "RED";
            normalCycleDuration = 30;
            System.out.println("[TrafficLight " + lightId
                    + "] 🔴 Trafic normal → cycle standard " + normalCycleDuration + "s");
        } else {
            currentColor = currentColor.equals("RED") ? "GREEN" : "RED";
        }
    }

    private void enableEmergencyPriority() {
        emergencyMode = true;
        currentColor = "GREEN";
        normalCycleDuration = 10;
        System.out.println("[TrafficLight " + lightId + "] 🚨 MODE URGENCE activé !");
    }

    @Override
    protected void takeDown() {
        System.out.println("[TrafficLight " + lightId + "] Arrêté.");
    }
}