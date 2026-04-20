package ontology;

public class Incident {

    private String incidentId;
    private String type;
    private String location;
    private String severity;
    private boolean resolved;

    public Incident() {
    }

    public Incident(String incidentId, String type, String location, String severity, boolean resolved) {
        this.incidentId = incidentId;
        this.type = type;
        this.location = location;
        this.severity = severity;
        this.resolved = resolved;
    }

    public String getIncidentId() {
        return incidentId;
    }

    public void setIncidentId(String incidentId) {
        this.incidentId = incidentId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public boolean isResolved() {
        return resolved;
    }

    public void setResolved(boolean resolved) {
        this.resolved = resolved;
    }

    @Override
    public String toString() {
        return "Incident{" +
                "incidentId='" + incidentId + '\'' +
                ", type='" + type + '\'' +
                ", location='" + location + '\'' +
                ", severity='" + severity + '\'' +
                ", resolved=" + resolved +
                '}';
    }
}