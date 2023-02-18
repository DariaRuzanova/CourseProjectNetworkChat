public interface MessageProvider {
    String get() throws InterruptedException;
    void send(String message);
}
