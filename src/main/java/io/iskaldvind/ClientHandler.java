package io.iskaldvind;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler {

    AuthService authService;
    Server server;
    Socket socket;
    DataOutputStream dataOutputStream;
    DataInputStream dataInputStream;
    Client client;

    ClientHandler(AuthService authService, Server server, Socket socket) {
        this.authService = authService;
        this.server = server;
        this.socket = socket;
        try {
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataInputStream = new DataInputStream(socket.getInputStream());
            if (!auth(dataInputStream, dataOutputStream)) {
                server.onClientDisconnected(this);
                closeConnection();
                return;
            }
            server.onNewClient(this);
            messageListener(dataInputStream, dataOutputStream);
        } catch (IOException e) {
            server.onClientDisconnected(this);
            closeConnection();
            e.printStackTrace();
        }
    }

    private void closeConnection() {
        try {
            dataInputStream.close();
            dataOutputStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    synchronized void sendMessage(Client author, String text) {
        try {
            dataOutputStream.writeUTF(author.name + ": " + text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Boolean auth (DataInputStream dataInputStream, DataOutputStream dataOutputStream  ) throws IOException {
        dataOutputStream.writeUTF("Please authenticate (/auth login password)");
        int tryCount = 0;
        int maxTryCount = 5;
        while (true) {
            tryCount++;
            String newMessage = dataInputStream.readUTF();
            String[] messageData = newMessage.split("\\s");
            if (messageData.length == 3 && messageData[0].equals("/auth")) {
                String login = messageData[1];
                String password = messageData[2];
                client = authService.auth(login, password);
                if (client != null) {
                    break;
                } else {
                    dataOutputStream.writeUTF("Wrong login or password");
                }
            } else {
                dataOutputStream.writeUTF("Authentication failed");
            }
            if (tryCount == maxTryCount) {
                System.out.println("Authentication attempts limit exceeded");
                return false;
            }
        }
        return true;
    }

    private void messageListener(DataInputStream dataInputStream, DataOutputStream dataOutputStream) throws IOException {
        while (true) {
            String newMessage = dataInputStream.readUTF();
            if (newMessage.equals("/exit")) {
                server.onClientDisconnected(this);
                closeConnection();
                return;
            }
            String[] messageData = newMessage.split("\\s");
            if (messageData[0].equals("/w")) {
                if (messageData.length > 1) {
                    String name = messageData[1];
                    String text;
                    if (messageData.length == 2) {
                        text = "";
                    } else {
                        text = newMessage.substring("/w".length() + name.length() + 2);
                    }
                    Client target = authService.getByName(name);
                    if (target == null) {
                        dataOutputStream.writeUTF("Wrong target");
                    } else {
                        server.onNewMessage(client, target, text);
                    }
                } else {
                    dataOutputStream.writeUTF("Wrong target");
                }
            } else {
                server.onNewMessage(client, null, newMessage);
            }
        }
    }
}
