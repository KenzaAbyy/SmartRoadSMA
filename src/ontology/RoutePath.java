package ontology;

import java.util.List;

public class RoutePath {

    private String pathId;
    private List<Node> nodes;
    private double totalDistance;
    private double estimatedTime;

    public RoutePath() {
    }

    public RoutePath(String pathId, List<Node> nodes, double totalDistance, double estimatedTime) {
        this.pathId = pathId;
        this.nodes = nodes;
        this.totalDistance = totalDistance;
        this.estimatedTime = estimatedTime;
    }

    public String getPathId() {
        return pathId;
    }

    public void setPathId(String pathId) {
        this.pathId = pathId;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }

    public double getTotalDistance() {
        return totalDistance;
    }

    public void setTotalDistance(double totalDistance) {
        this.totalDistance = totalDistance;
    }

    public double getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(double estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    @Override
    public String toString() {
        return "RoutePath{" +
                "pathId='" + pathId + '\'' +
                ", nodes=" + nodes +
                ", totalDistance=" + totalDistance +
                ", estimatedTime=" + estimatedTime +
                '}';
    }
}