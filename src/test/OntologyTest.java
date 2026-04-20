package test;

import ontology.Incident;
import ontology.Node;
import ontology.PriorityRequest;
import ontology.Road;
import ontology.RoutePath;
import ontology.TrafficState;
import ontology.VehicleInfo;

import java.util.Arrays;

public class OntologyTest {

    public static void main(String[] args) {

        Node node1 = new Node("N1", "Carrefour Centre", "INTERSECTION");
        Node node2 = new Node("N2", "Entrée Autoroute", "GATEWAY");
        Node node3 = new Node("N3", "Sortie Ville", "HIGHWAY_EXIT");

        Road road1 = new Road("R1", "Boulevard Principal", node1, node2, 4.2);

        TrafficState trafficState = new TrafficState("ZONE_1", "HIGH", 22.5, 30, true);

        Incident incident = new Incident("INC_01", "ACCIDENT", "Autoroute A1", "CRITICAL", false);

        RoutePath routePath = new RoutePath("PATH_01", Arrays.asList(node1, node2, node3), 12.5, 18.0);

        VehicleInfo vehicleInfo = new VehicleInfo("V01", "AMBULANCE", node1, node3, 45.0, "MOVING");

        PriorityRequest priorityRequest = new PriorityRequest("PR_01", "V01", "EMERGENCY", "ZONE_1", true);

        System.out.println(node1);
        System.out.println(road1);
        System.out.println(trafficState);
        System.out.println(incident);
        System.out.println(routePath);
        System.out.println(vehicleInfo);
        System.out.println(priorityRequest);
    }
}