import static org.junit.jupiter.api.Assertions.*;

class ServerSettingsTest {

    @org.junit.jupiter.api.Test
    void getPort() {
        ServerSettings settings = new ServerSettings();
        int port = settings.getPort();
        assertEquals(10000, port);
    }
}