import java.io.*;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;

public class Client {
    private final String EXIT = "/exit";

    private Socket socket;
    private BufferedReader in;
    private BufferedWriter out;

    ServerSettings serverSettings = new ServerSettings();
    int port = serverSettings.getPort();
    String host = serverSettings.getHost();
    private final String name;
    ConsoleLogger logger;
    private final MessageProvider messageProvider;
    private final AtomicBoolean exit = new AtomicBoolean(false);

    public Client(String name, ConsoleLogger logger, MessageProvider messageProvider) {
        this.name = name;
        this.logger = logger;
        this.messageProvider = messageProvider;
    }

    public boolean connect() {
        socket = null;
        try {
            socket = new Socket(host, port);
            logger.log("Клиент подключен к сокету.");
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            out.write(name);
            out.newLine();
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return socket != null;
    }

    public void disconnect() {
        if (socket != null) {
            try {
                in.close();
                out.close();
                socket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            socket = null;
        }
    }

    public void read() {
        Thread thread = new Thread(() -> {
            while (!exit.get()) {
                try {
                    String msg = in.readLine();
                    if (msg != null) {
                        messageProvider.send(msg);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        thread.start();
    }

    public void write() {
        Thread thread = new Thread(() -> {
            while (!exit.get()) {
                try {
                    String msg = messageProvider.get();
                    System.out.println("Пользователь " + name + ": " + msg);
                    logger.log("Пользователь " + name + ": " + msg);
                    out.write(msg);
                    out.newLine();
                    exit.set(EXIT.equals(msg));
                    out.flush();
                } catch (IOException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        thread.start();
    }
}
