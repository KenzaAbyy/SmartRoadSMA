package ontology;

public class VehicleInfo {

    private String vehicleId;
    private String type;
    private Node currentPosition;
    private Node destination;
    private double speed;
    private String status;

    public VehicleInfo() {
    }

    public VehicleInfo(String vehicleId, String type, Node currentPosition, Node destination, double speed, String status) {
        this.vehicleId = vehicleId;
        this.type = type;
        this.currentPosition = currentPosition;
        this.destination = destination;
        this.speed = speed;
        this.status = status;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Node getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(Node currentPosition) {
        this.currentPosition = currentPosition;
    }

    public Node getDestination() {
        return destination;
    }

    public void setDestination(Node destination) {
        this.destination = destination;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "VehicleInfo{" +
                "vehicleId='" + vehicleId + '\'' +
                ", type='" + type + '\'' +
                ", currentPosition=" + currentPosition +
                ", destination=" + destination +
                ", speed=" + speed +
                ", status='" + status + '\'' +
                '}';
    }
}