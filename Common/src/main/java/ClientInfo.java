import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.net.Socket;

public class ClientInfo {
    String name;
    Socket socket;
    private final BufferedReader in;
    private final BufferedWriter out;


    public ClientInfo(String name, Socket socket, BufferedReader in, BufferedWriter out) {
        this.name = name;
        this.socket = socket;
        this.in = in;
        this.out = out;
    }

    public String getName() {
        return name;
    }

    public Socket getSocket() {
        return socket;
    }

    public BufferedReader getIn() {
        return in;
    }

    public BufferedWriter getOut() {
        return out;
    }
}
