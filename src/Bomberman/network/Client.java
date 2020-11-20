package Bomberman.network;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    private static final String SERVER_IP = "127.0.0.1";
    private static final int SERVER_PORT = 2521;

    public static PrintWriter out;
    public static Socket socket;

    public static void runClient() {
        try {
            socket = new Socket(SERVER_IP, SERVER_PORT);

            ServerConnection serverConn = new ServerConnection(socket);
            out = new PrintWriter(socket.getOutputStream(), true);

            new Thread(serverConn).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
