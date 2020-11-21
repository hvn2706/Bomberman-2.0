package Bomberman.network;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private static String SERVER_IP;
    private static int SERVER_PORT;

    public static PrintWriter out;
    public static Socket socket;
    public static boolean serverRunning = true;

    private static void readPort() {
        try {
            File file = new File("resources/server.txt");
            Scanner sc = new Scanner(file);
            SERVER_IP = sc.nextLine();
            SERVER_PORT = sc.nextInt();
            sc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void runClient() {
        try {
            readPort();
            socket = new Socket(SERVER_IP, SERVER_PORT);

            ServerConnection serverConn = new ServerConnection(socket);
            out = new PrintWriter(socket.getOutputStream(), true);

            new Thread(serverConn).start();
        } catch (IOException e) {
            serverRunning = false;
            e.printStackTrace();
        }
    }
}
