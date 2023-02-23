public class MainServer {
    public static void main(String[] args) {
        String settingsFileName = CommonUtils.getRouteFileName("Settings.txt");
        String logFileName = CommonUtils.getRouteFileName("logFileServer.txt");

        Server server = new Server(settingsFileName, logFileName);
        server.connectServer();
        server.sendAllMessages();
    }
}
