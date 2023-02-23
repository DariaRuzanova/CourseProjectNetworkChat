import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class ServerSettingsTest {

    @org.junit.jupiter.api.Test
    void getPort() {
        String settingsFolder = String.valueOf(Paths.get(CommonUtils.getRouteFolder()).getParent());
        String settingsFileName = String.valueOf(Paths.get(settingsFolder, "Settings.txt"));
        ServerSettings settings = new ServerSettings(settingsFileName);
        int port = settings.getPort();
        assertEquals(10000, port);
    }
}