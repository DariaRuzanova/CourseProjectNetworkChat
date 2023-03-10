import org.junit.jupiter.api.Test;

import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ClientServerTest {
    @Test
    void test() throws InterruptedException {
        String pathFolder = String.valueOf(Paths.get(CommonUtils.getRouteFolder()).getParent());
        String settingsFileName = String.valueOf(Paths.get(pathFolder, "Settings.txt"));

        String logFileName = String.valueOf(Paths.get(pathFolder, "logFileServer.txt"));
        Server server = new Server(settingsFileName, logFileName);
        server.connectServer();
        server.sendAllMessages();

        String clientName1 = "client1";

        String logFileName1 = String.valueOf(Paths.get(pathFolder, "logFileClient1.txt"));
        ConsoleLogger logger1 = new ConsoleLogger(logFileName1, false);
        TestMessageProvider messageProvider1 = new TestMessageProvider();
        Client client1 = new Client(clientName1, settingsFileName, logger1, messageProvider1);
        client1.connect();
        client1.write();
        client1.read();

        String clientName2 = "client2";
        String logFileName2 = String.valueOf(Paths.get(pathFolder, "logFileClient2.txt"));
        ConsoleLogger logger2 = new ConsoleLogger(logFileName2, false);
        TestMessageProvider messageProvider2 = new TestMessageProvider();
        Client client2 = new Client(clientName2, settingsFileName, logger2, messageProvider2);
        client2.connect();
        client2.write();
        client2.read();

        messageProvider1.putClientMessage("Hello1 from client1");
        messageProvider1.putClientMessage("Hello2 from client1");

        messageProvider2.putClientMessage("Hello1 from client2");
        messageProvider2.putClientMessage("Hello2 from client2");

        Thread.sleep(1000);

        List<String> serverMessages1 = messageProvider1.getServerMessages();
        List<String> serverMessages2 = messageProvider2.getServerMessages();

        assertNotNull(serverMessages1);
        assertEquals(2, serverMessages1.size());
        assertEquals("client2: Hello1 from client2", serverMessages1.get(0));
        assertEquals("client2: Hello2 from client2", serverMessages1.get(1));

        assertNotNull(serverMessages2);
        assertEquals(2, serverMessages1.size());
        assertEquals("client1: Hello1 from client1", serverMessages2.get(0));
        assertEquals("client1: Hello2 from client1", serverMessages2.get(1));
    }
}
