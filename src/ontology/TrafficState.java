package ontology;

public class TrafficState {

    private String zoneId;
    private String trafficLevel;
    private double averageSpeed;
    private int vehicleCount;
    private boolean congested;

    public TrafficState() {
    }

    public TrafficState(String zoneId, String trafficLevel, double averageSpeed, int vehicleCount, boolean congested) {
        this.zoneId = zoneId;
        this.trafficLevel = trafficLevel;
        this.averageSpeed = averageSpeed;
        this.vehicleCount = vehicleCount;
        this.congested = congested;
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public String getTrafficLevel() {
        return trafficLevel;
    }

    public void setTrafficLevel(String trafficLevel) {
        this.trafficLevel = trafficLevel;
    }

    public double getAverageSpeed() {
        return averageSpeed;
    }

    public void setAverageSpeed(double averageSpeed) {
        this.averageSpeed = averageSpeed;
    }

    public int getVehicleCount() {
        return vehicleCount;
    }

    public void setVehicleCount(int vehicleCount) {
        this.vehicleCount = vehicleCount;
    }

    public boolean isCongested() {
        return congested;
    }

    public void setCongested(boolean congested) {
        this.congested = congested;
    }

    @Override
    public String toString() {
        return "TrafficState{" +
                "zoneId='" + zoneId + '\'' +
                ", trafficLevel='" + trafficLevel + '\'' +
                ", averageSpeed=" + averageSpeed +
                ", vehicleCount=" + vehicleCount +
                ", congested=" + congested +
                '}';
    }
}