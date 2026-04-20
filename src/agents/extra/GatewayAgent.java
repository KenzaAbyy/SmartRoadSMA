package agents.extra;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class GatewayAgent extends Agent {

    protected void setup() {
        System.out.println("GatewayAgent démarré !");
        System.out.println("Nom : " + getLocalName());

        addBehaviour(new CyclicBehaviour() {
            public void action() {

                ACLMessage msg = receive();

                if (msg != null) {

                    String content = msg.getContent();
                    System.out.println("[Gateway] Message reçu : " + content);

                    // 🔥 cas 1 : demande d’itinéraire
                    if (content.startsWith("REQUEST_ROUTE")) {

                        ACLMessage forward = new ACLMessage(ACLMessage.REQUEST);
                        forward.addReceiver(getAID("supervisor"));
                        forward.setContent(content);

                        send(forward);

                        System.out.println("[Gateway] ➡️ Demande envoyée au Supervisor");
                    }

                    // 🔥 cas 2 : redirection (incident)
                    else if (content.equals("REQUEST_REDIRECTION")) {
                        System.out.println("🚗 Redirection du trafic en cours...");
                        proposeRoute();
                    }

                    // 🔥 cas 3 : réponse du Supervisor
                    else if (content.startsWith("PROPOSE_ROUTE")) {

                        ACLMessage reply = new ACLMessage(ACLMessage.INFORM);
                        reply.addReceiver(getAID("test"));
                        reply.setContent(content);

                        send(reply);

                        System.out.println("[Gateway] ➡️ Route envoyée au Vehicle");
                    }

                } else {
                    block();
                }
            }
        });
    }

    private void proposeRoute() {

        ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
        msg.addReceiver(getAID("test"));
        msg.setContent("PROPOSE_ROUTE: nouvelle route via B2");

        send(msg);

        System.out.println("➡️ Nouvelle route envoyée au véhicule");
    }
}