import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CommonUtils {
    public static String getRouteFolder() {
        return System.getProperty("user.dir");
    }

    public static String getRouteFileName(String fileName) {
        return String.valueOf(Paths.get(CommonUtils.getRouteFolder(), fileName));
    }
}
