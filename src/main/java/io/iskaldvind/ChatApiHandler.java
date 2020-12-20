package io.iskaldvind;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class ChatApiHandler {

    interface Callback {

        void onAuth(boolean isSuccess, String serverError);

        void onNewMessage(String message);

        void onStop();
    }

    ExecutorService executor = Executors.newSingleThreadExecutor();

    Callback callback;

    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;

    ChatApiHandler(Callback callback) {
        this.callback = callback;
        try {
            Socket socket = new Socket("localhost", 8081);
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            new Thread(() -> {
                try {
                    while (true) {
                        String newMessage = dataInputStream.readUTF();
                        if (newMessage.startsWith("/auth")) {
                            if (newMessage.equals("/auth ok")) {
                                System.out.println("response success");
                                callback.onAuth(true, null);
                            } else {
                                System.out.println("response fails");
                                callback.onAuth(false, newMessage);
                            }
                        } else if (newMessage.startsWith("/nm ") && newMessage.length() > 5) {
                            callback.onNewMessage(newMessage.substring(4));
                        } else if (newMessage.startsWith("/err ")) {
                            callback.onAuth(false, newMessage.substring(5));
                        } else if (newMessage.startsWith("/stop ")) {
                            callback.onAuth(false, newMessage.substring(6));
                            callback.onStop();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    synchronized void auth(String login, String password) {
        new Thread(() -> {
            try {
                String command = "/auth " + login + " " + password;
                System.out.println(command);
                dataOutputStream.writeUTF(command);
            } catch (IOException e) {
                System.out.println("response exception");
                callback.onAuth(false, null);
            }
        }).start();
    }

    synchronized void sendMessage(String text) {
        new Thread(() -> {
            try {
                dataOutputStream.writeUTF(text);
            } catch (IOException e) {
            }
        }).start();
    }
}


