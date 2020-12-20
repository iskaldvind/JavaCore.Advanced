package io.iskaldvind;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class Server {

    List<Message> messages = new ArrayList<>();
    List<ClientHandler> clientHandlers = new ArrayList<>();

    Server() {
        try {
            ServerSocket serverSocket = new ServerSocket(8081);
            AuthService authService = new AuthService();
            System.out.println("Server starts");
            while (true) {
                try {
                    Socket socket = serverSocket.accept();
                    new Thread(() -> new ClientHandler(authService, this, socket)).start();
                } catch (SocketException e) {
                    System.out.println("Client lost connection");
                }
            }
        } catch (IOException e) {
            System.out.println("Server shut down with error");
            e.printStackTrace();
        }
    }

    synchronized void onNewMessage(Client author, Client target, String message) {
        messages.add(new Message(author, target, message));
        if (target == null) {
            for (ClientHandler clientHandler: clientHandlers) {
                if (!clientHandler.client.login.equals(author.login)) clientHandler.sendMessage(author, message);
            }
        } else {
            for (ClientHandler clientHandler: clientHandlers) {
                if (clientHandler.client.login.equals(target.login)) {
                    clientHandler.sendMessage(author, message);
                }
            }
        }
    }

    synchronized void onClientDisconnected(ClientHandler clientHandler) {
        clientHandlers.remove(clientHandler);
        onNewMessage(clientHandler.client, null, "Left chat");
    }

    synchronized void onNewClient(ClientHandler clientHandler) {
        clientHandlers.add(clientHandler);
        onNewMessage(clientHandler.client, null, "Joined chat");
        for (Message message: messages) {
            if ((message.target == null) || message.target.login.equals(clientHandler.client.login)) {
                clientHandler.sendMessage(message.author, message.text);
            }
        }
    }

    public static void main(String[] args) {
        new Server();
    }
}
