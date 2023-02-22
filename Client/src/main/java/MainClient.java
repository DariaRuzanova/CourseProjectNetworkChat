import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class MainClient {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

//        String logFileName = "d:\\Daria\\Java\\networkChat\\networkChat\\logFileClient.txt";
        String logFileName = loggerClientFileName();
        ConsoleLogger logger = new ConsoleLogger(logFileName, false);

        ConsoleMessageProvider messageProvider = new ConsoleMessageProvider(sc);

        System.out.println("Введите имя для авторизации");
        String name = sc.nextLine();

        Client client = new Client(name, logger, messageProvider);
        client.connect();

        System.out.println("Пользователь " + name + " авторизовался в чате");
        logger.log("Пользователь " + name + " авторизовался в чате");
        client.write();
        client.read();
    }

    private static String loggerClientFileName() {
        return String.valueOf(Paths.get(Path.of(new File("").getAbsolutePath()).toString(), "logFileClient.txt"));
    }
}
