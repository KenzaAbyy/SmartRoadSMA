package agents.core;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class SupervisorAgent extends Agent {

    protected void setup() {
        System.out.println("SupervisorAgent démarré !");
        System.out.println("Nom : " + getLocalName());

        addBehaviour(new CyclicBehaviour() {
            public void action() {

                ACLMessage msg = receive();

                if (msg != null) {

                    String content = msg.getContent();
                    System.out.println("[Supervisor] Message reçu : " + content);

                    // 🔴 INCIDENT
                    if (content.startsWith("INFORM_INCIDENT")) {
                        System.out.println("🚨 Accident détecté !");
                        sendRedirection();
                    }

                    // 🟡 TRAFIC
                    else if (content.startsWith("INFORM_TRAFFIC_STATE")) {
                        System.out.println("📊 Analyse trafic...");
                    }

                    // 🔵 DEMANDE ROUTE
                    else if (content.startsWith("REQUEST_ROUTE")) {

                        System.out.println("🧠 Calcul d’un itinéraire optimal...");

                        ACLMessage reply = new ACLMessage(ACLMessage.INFORM);
                        reply.addReceiver(getAID("gateway"));
                        reply.setContent("PROPOSE_ROUTE: itinéraire optimal via A1");

                        send(reply);

                        System.out.println("➡️ Route envoyée au Gateway");
                    }

                } else {
                    block();
                }
            }
        });
    }

    private void sendRedirection() {

        ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
        msg.addReceiver(getAID("gateway"));
        msg.setContent("REQUEST_REDIRECTION");

        send(msg);

        System.out.println("➡️ Redirection demandée au Gateway");
    }
}