import java.util.Scanner;

public class MainClient {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String logFileName = "d:\\Daria\\Java\\networkChat\\networkChat\\logFileClient.txt";
        ConsoleLogger logger = new ConsoleLogger(logFileName, true);

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
}
