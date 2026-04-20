package main;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;

public class MainContainer {

    public static void main(String[] args) {
        try {
            Runtime runtime = Runtime.instance();

            Profile profile = new ProfileImpl(null, 1100, null);
            profile.setParameter(Profile.GUI, "true");

            AgentContainer container = runtime.createMainContainer(profile);

            System.out.println("========================================");
            System.out.println("   STI - Système de Transport Intelligent");
            System.out.println("   Lancement global des agents...");
            System.out.println("========================================");

            // 🔥 SNIFFER GLOBAL (URBAIN + EXTRA)
            AgentController sniffer = container.createNewAgent(
                    "sniffer",
                    "jade.tools.sniffer.Sniffer",
                    new Object[]{
                            "supervisor;gateway;highway;test;" +
                                    "intersection_A;intersection_B;intersection_C;" +
                                    "vehicle1;vehicle2;" +
                                    "light_intersection_A;light_intersection_B;light_intersection_C"
                    }
            );
            sniffer.start();

            Thread.sleep(2000);

            // ===============================
            // 🔴 TA PARTIE (EXTRA-URBAIN)
            // ===============================

            container.createNewAgent(
                    "supervisor",
                    "agents.core.SupervisorAgent",
                    null
            ).start();

            container.createNewAgent(
                    "highway",
                    "agents.extra.HighwayAgent",
                    null
            ).start();

            container.createNewAgent(
                    "gateway",
                    "agents.extra.GatewayAgent",
                    null
            ).start();

            container.createNewAgent(
                    "test",
                    "agents.TestAgent",
                    null
            ).start();

            // ===============================
            // 🟢 PARTIE MEMBRE 2 (URBAIN)
            // ===============================

            // Feux
            container.createNewAgent(
                    "light_intersection_A",
                    "agents.TrafficLightAgent",
                    null
            ).start();

            container.createNewAgent(
                    "light_intersection_B",
                    "agents.TrafficLightAgent",
                    null
            ).start();

            container.createNewAgent(
                    "light_intersection_C",
                    "agents.TrafficLightAgent",
                    null
            ).start();

            // Intersections
            container.createNewAgent(
                    "intersection_A",
                    "agents.IntersectionAgent",
                    null
            ).start();

            container.createNewAgent(
                    "intersection_B",
                    "agents.IntersectionAgent",
                    null
            ).start();

            container.createNewAgent(
                    "intersection_C",
                    "agents.IntersectionAgent",
                    null
            ).start();

            // Véhicules urbains
            container.createNewAgent(
                    "vehicle1",
                    "agents.VehicleAgent",
                    null
            ).start();

            container.createNewAgent(
                    "vehicle2",
                    "agents.VehicleAgent",
                    null
            ).start();

            System.out.println("========================================");
            System.out.println("   ✅ Tous les agents lancés !");
            System.out.println("========================================");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}