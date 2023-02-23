import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.LinkedBlockingQueue;

public class Server {
    private final int port;
    private final ConsoleLogger logger;
    private static Socket clientSocket;
    private static BufferedReader in;
    private static BufferedWriter out;
    ArrayList<ClientInfo> clients = new ArrayList<>();
    LinkedBlockingQueue<ClientMessage> clientMessages = new LinkedBlockingQueue<>();

    public Server(String settingsFileName, String logFileName) {
        ServerSettings serverSettings = new ServerSettings(settingsFileName);
        port = serverSettings.getPort();
        logger = new ConsoleLogger(logFileName, false);
    }

    public void connectServer() {
        Thread thread = new Thread(() -> {
            try {
                ServerSocket serverSocket = new ServerSocket(port);

                String msg1 = "Сервер запущен";
                logger.log(msg1);
                System.out.println(msg1);

                while (true) {
                    clientSocket = serverSocket.accept();
                    System.out.println("Подключен новый пользователь");
                    try {
                        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                        out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                        String name = in.readLine();
                        String msg2 = "Подключен новый пользователь: " + name;
                        logger.log(msg2);

                        ClientInfo clientInfo = new ClientInfo(name, clientSocket, in, out);
                        clients.add(clientInfo);
                        getMessages(clientInfo);

                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });
        thread.start();

    }

    public void sendAllMessages() {
        Thread thread = new Thread(() -> {

            try {
                while (true) {
                    ClientMessage clientMessage = clientMessages.take();
                    for (ClientInfo clientinfo : clients) {
                        if (!Objects.equals(clientinfo.getName(), clientMessage.getClientName())) {
                            String msg = String.format("%s: %s", clientMessage.getClientName(), clientMessage.getMessage());
                            clientinfo.getOut().write(msg);
                            clientinfo.getOut().newLine();
                            clientinfo.getOut().flush();
                        }
                    }
                }
            } catch (InterruptedException | IOException e) {
                throw new RuntimeException(e);
            }
        });
        thread.start();
    }

    private void getMessages(ClientInfo clientInfo) {
        Thread thread = new Thread(() -> {

            try {
                boolean exit = false;
                while (!exit) {
                    String msg = clientInfo.getIn().readLine();
                    if (!Objects.equals(msg, CommonConstants.EXIT_MESSAGE)) {
                        logger.log("Получено сообщение от пользователя " + clientInfo.getName() + ": " + msg);
                        System.out.println("Получено сообщение от пользователя " + clientInfo.getName() + ": " + msg);
                    } else {
                        exit = true;
                        msg = clientInfo.getName() + " вышел из чата";
                        logger.log(msg);
                        System.out.println(msg);
                        close(clientInfo);
                    }
                    ClientMessage clientMessage = new ClientMessage(clientInfo.getName(), msg);
                    clientMessages.add(clientMessage);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        thread.start();
    }

    private void close(ClientInfo clientInfo) throws IOException {
        clients.remove(clientInfo);
        clientInfo.getSocket().close();
        clientInfo.getIn().close();
        clientInfo.getOut().close();
    }
}
