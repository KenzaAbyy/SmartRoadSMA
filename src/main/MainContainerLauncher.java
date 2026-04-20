package main;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;

public class MainContainerLauncher {

    public static void main(String[] args) {
        try {
            Runtime runtime = Runtime.instance();

            Profile profile = new ProfileImpl();
            profile.setParameter(Profile.GUI, "true");
            profile.setParameter(Profile.MAIN, "true");

            AgentContainer mainContainer = runtime.createMainContainer(profile);

            System.out.println("========================================");
            System.out.println("   STI - Système de Transport Intelligent");
            System.out.println("   Lancement des agents urbains...");
            System.out.println("========================================");

            // ── Sniffer automatique ──
            AgentController sniffer = mainContainer.createNewAgent(
                    "sniffer",
                    "jade.tools.sniffer.Sniffer",
                    new Object[]{"intersection_A;intersection_B;intersection_C;" +
                            "vehicle1;vehicle2;" +
                            "light_intersection_A;light_intersection_B;light_intersection_C"}
            );
            sniffer.start();

            // Attendre que le Sniffer soit prêt
            Thread.sleep(2000);

            // ── Feux de circulation ──
            mainContainer.createNewAgent(
                    "light_intersection_A",
                    "agents.TrafficLightAgent",
                    null
            ).start();

            mainContainer.createNewAgent(
                    "light_intersection_B",
                    "agents.TrafficLightAgent",
                    null
            ).start();

            mainContainer.createNewAgent(
                    "light_intersection_C",
                    "agents.TrafficLightAgent",
                    null
            ).start();

            // ── Intersections ──
            mainContainer.createNewAgent(
                    "intersection_A",
                    "agents.IntersectionAgent",
                    null
            ).start();

            mainContainer.createNewAgent(
                    "intersection_B",
                    "agents.IntersectionAgent",
                    null
            ).start();

            mainContainer.createNewAgent(
                    "intersection_C",
                    "agents.IntersectionAgent",
                    null
            ).start();

            // ── Véhicules ──
            mainContainer.createNewAgent(
                    "vehicle1",
                    "agents.VehicleAgent",
                    null
            ).start();

            mainContainer.createNewAgent(
                    "vehicle2",
                    "agents.VehicleAgent",
                    null
            ).start();

            System.out.println("========================================");
            System.out.println("   ✅ Tous les agents urbains démarrés !");
            System.out.println("========================================");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}