package agents.extra;

import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;

public class HighwayAgent extends Agent {

    private boolean dense = false; // alterner l'état

    protected void setup() {
        System.out.println("HighwayAgent démarré !");
        System.out.println("Nom : " + getLocalName());

        // 🔁 envoi toutes les 4 secondes (subscribe simulé)
        addBehaviour(new TickerBehaviour(this, 4000) {
            protected void onTick() {

                ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
                msg.addReceiver(getAID("supervisor"));

                // alterner trafic
                dense = !dense;
                String state = dense ? "dense" : "normal";

                // 1 fois sur 3 → simuler un incident
                if (Math.random() < 0.33) {
                    msg.setContent("INFORM_INCIDENT: accident sur A1");
                    System.out.println("[Highway] 🚨 Incident envoyé !");
                } else {
                    msg.setContent("INFORM_TRAFFIC_STATE: trafic " + state);
                    System.out.println("[Highway] 📡 Etat trafic envoyé : " + state);
                }

                send(msg);
            }
        });
    }
}