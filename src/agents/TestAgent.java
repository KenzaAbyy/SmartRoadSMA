package agents;

import jade.core.Agent;
import jade.core.behaviours.WakerBehaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class TestAgent extends Agent {

    protected void setup() {
        System.out.println("[Vehicle] 🚗 Démarré : " + getLocalName());

        // 🔥 envoie une demande d’itinéraire après 2 sec
        addBehaviour(new WakerBehaviour(this, 2000) {
            protected void onWake() {

                ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
                msg.addReceiver(getAID("gateway"));

                msg.setContent("REQUEST_ROUTE: destination B");

                send(msg);

                System.out.println("[Vehicle] 🚗 Demande d’itinéraire envoyée !");
            }
        });

        // 🔥 reçoit la réponse
        addBehaviour(new CyclicBehaviour() {
            public void action() {

                ACLMessage msg = receive();

                if (msg != null) {
                    System.out.println("[Vehicle] 🚗 Message reçu : " + msg.getContent());
                } else {
                    block();
                }
            }
        });
    }
}