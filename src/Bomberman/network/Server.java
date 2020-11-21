package Bomberman.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server extends Thread{
    public static final ArrayList<ClientHandler> clients = new ArrayList<>();
    private static final ExecutorService pool = Executors.newFixedThreadPool(4);
    private static final int PORT = 2521;

    @Override
    public void run() {
        try {
            ServerSocket listener = new ServerSocket(PORT);
            while (true) {
                System.out.println("Waiting for client connection ...");
                Socket client = listener.accept();
                System.out.println("Connected to a client");
                ClientHandler clientThread = new ClientHandler(client, clients);
                clients.add(clientThread);
                pool.execute(clientThread);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
