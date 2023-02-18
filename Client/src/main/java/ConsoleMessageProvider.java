import java.util.Scanner;

public class ConsoleMessageProvider implements MessageProvider {
    Scanner sc;

    public ConsoleMessageProvider(Scanner sc) {
         this.sc = sc;
    }

    @Override
    public String get() {
        return sc.nextLine();
    }

    @Override
    public void send(String message) {
        System.out.println(message);
    }
}
