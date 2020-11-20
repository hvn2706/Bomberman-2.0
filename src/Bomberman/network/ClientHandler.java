package Bomberman.network;

import Bomberman.Game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable {
    private final BufferedReader in;
    private final PrintWriter out;
    private final ArrayList<ClientHandler> clients;
    private final int indexName;
    public static int index = 0;

    // constructor
    public ClientHandler(Socket clientSocket, ArrayList<ClientHandler> clients) throws IOException {
        this.clients = clients;
        index++;
        this.indexName = index;
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out = new PrintWriter(clientSocket.getOutputStream(), true);
    }

    private void outToAll(String msg) {
        if (indexName == 1) {
            if (clients.size() > 1) {
                clients.get(1).out.println(msg);
            }
        } else {
            clients.get(0).out.println(msg);
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                String request = in.readLine();
                if (request == null) {
                    continue;
                }
                outToAll("Client#" + indexName + ": " + request);
            }
        } catch (IOException io) {
            io.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            out.close();
        }
    }
}
