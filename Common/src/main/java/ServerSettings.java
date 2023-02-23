import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ServerSettings {

    private final Properties property = new Properties();


    public ServerSettings(String settingsFileName) {
        try {
            FileInputStream fis = new FileInputStream(settingsFileName);
            property.load(fis);
        } catch (IOException e) {
            System.err.println("Ошибка: такой файл отсутствует!");
        }
    }

    public int getPort() {
        return Integer.parseInt(property.getProperty("PORT"));
    }

    public String getHost() {
        return property.getProperty("HOST");
    }
}
