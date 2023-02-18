import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public class TestMessageProvider implements MessageProvider {
    private final LinkedBlockingQueue<String> clientMessages;
    private final List<String> serverMessages;

    public TestMessageProvider() {
        clientMessages = new LinkedBlockingQueue<>();
        serverMessages = new ArrayList<>();
    }

    @Override
    public String get() throws InterruptedException {
        return clientMessages.take();
    }

    @Override
    public void send(String message) {
        synchronized (serverMessages) {
            serverMessages.add(message);
        }
    }

    public void putClientMessage(String message) throws InterruptedException {
        clientMessages.put(message);
    }

    public List<String> getServerMessages() {
        List<String> result;
        synchronized (serverMessages) {
            result = new ArrayList<>(serverMessages);
        }
        return result;
    }
}
