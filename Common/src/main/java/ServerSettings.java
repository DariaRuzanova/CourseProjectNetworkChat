import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class ServerSettings {
    private final Properties property = new Properties();

    public ServerSettings() {
        try {

            // String txtSetting = "D:\\Daria\\Java\\networkChat\\networkChat\\Settings.txt";
            String txtSetting = getSettingsFileName();
            FileInputStream fis = new FileInputStream(txtSetting);
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

    public String getSettingsFileName() {

        return String.valueOf(Paths.get(Path.of(new File("").getAbsolutePath()).toString(), "Settings.txt"));
    }
}
