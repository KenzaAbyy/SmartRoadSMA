package constants;

public class MessageTypes {

    public static final String REQUEST_ROUTE = "REQUEST_ROUTE";
    public static final String FORWARD_ROUTE_REQUEST = "FORWARD_ROUTE_REQUEST";
    public static final String PROPOSE_ROUTE = "PROPOSE_ROUTE";
    public static final String PROPOSE_ALTERNATIVE_ROUTE = "PROPOSE_ALTERNATIVE_ROUTE";

    public static final String REQUEST_TRAFFIC_STATE = "REQUEST_TRAFFIC_STATE";
    public static final String INFORM_TRAFFIC_STATE = "INFORM_TRAFFIC_STATE";
    public static final String INFORM_CONGESTION = "INFORM_CONGESTION";
    public static final String INFORM_INCIDENT = "INFORM_INCIDENT";
    public static final String INFORM_ROUTE_STATUS = "INFORM_ROUTE_STATUS";

    public static final String REQUEST_SIGNAL_ADAPTATION = "REQUEST_SIGNAL_ADAPTATION";
    public static final String REQUEST_REDIRECTION = "REQUEST_REDIRECTION";
    public static final String REQUEST_PRIORITY = "REQUEST_PRIORITY";

    public static final String SUBSCRIBE_TRAFFIC_UPDATES = "SUBSCRIBE_TRAFFIC_UPDATES";
    public static final String PUBLISH_NETWORK_STATUS = "PUBLISH_NETWORK_STATUS";

    public static final String CFP = "CFP";
    public static final String PROPOSE = "PROPOSE";
    public static final String ACCEPT_PROPOSAL = "ACCEPT_PROPOSAL";
    public static final String REJECT_PROPOSAL = "REJECT_PROPOSAL";

    private MessageTypes() {
        // Prevent instantiation
    }
}