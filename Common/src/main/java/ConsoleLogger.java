import java.io.FileOutputStream;
import java.io.IOException;

public class ConsoleLogger implements Logger {
    private int countMsg = 0;
    private final String logFileName;


    public ConsoleLogger(String logFileName, boolean append) {

        this.logFileName = logFileName;
        try (FileOutputStream fos = new FileOutputStream(logFileName, append)) {
            fos.write("".getBytes());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void log(String msg) {
        countMsg++;
        String str = "№ " + countMsg + " " + msg + "\n";

        try (FileOutputStream fos = new FileOutputStream(logFileName, true)) {
            fos.write(str.getBytes());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
