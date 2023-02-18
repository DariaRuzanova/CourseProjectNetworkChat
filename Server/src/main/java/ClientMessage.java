public class ClientMessage {
    String clientName;
    String message;

    public ClientMessage(String clientName, String message) {
        this.clientName = clientName;
        this.message = message;
    }

    public String getClientName() {
        return clientName;
    }

    public String getMessage() {
        return message;
    }
}
