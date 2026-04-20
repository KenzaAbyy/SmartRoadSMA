package ontology;

public class Road {

    private String id;
    private String name;
    private Node startNode;
    private Node endNode;
    private double distance;

    public Road() {
    }

    public Road(String id, String name, Node startNode, Node endNode, double distance) {
        this.id = id;
        this.name = name;
        this.startNode = startNode;
        this.endNode = endNode;
        this.distance = distance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Node getStartNode() {
        return startNode;
    }

    public void setStartNode(Node startNode) {
        this.startNode = startNode;
    }

    public Node getEndNode() {
        return endNode;
    }

    public void setEndNode(Node endNode) {
        this.endNode = endNode;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Road{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", startNode=" + startNode +
                ", endNode=" + endNode +
                ", distance=" + distance +
                '}';
    }
}