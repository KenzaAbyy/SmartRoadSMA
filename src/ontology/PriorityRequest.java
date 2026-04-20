package ontology;

public class PriorityRequest {

    private String requestId;
    private String vehicleId;
    private String priorityType;
    private String targetZone;
    private boolean active;

    public PriorityRequest() {
    }

    public PriorityRequest(String requestId, String vehicleId, String priorityType, String targetZone, boolean active) {
        this.requestId = requestId;
        this.vehicleId = vehicleId;
        this.priorityType = priorityType;
        this.targetZone = targetZone;
        this.active = active;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getPriorityType() {
        return priorityType;
    }

    public void setPriorityType(String priorityType) {
        this.priorityType = priorityType;
    }

    public String getTargetZone() {
        return targetZone;
    }

    public void setTargetZone(String targetZone) {
        this.targetZone = targetZone;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "PriorityRequest{" +
                "requestId='" + requestId + '\'' +
                ", vehicleId='" + vehicleId + '\'' +
                ", priorityType='" + priorityType + '\'' +
                ", targetZone='" + targetZone + '\'' +
                ", active=" + active +
                '}';
    }
}