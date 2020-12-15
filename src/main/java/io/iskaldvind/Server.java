package io.iskaldvind;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

public class Server {

    private static final Scanner SCANNER = new Scanner(System.in);
    private static Boolean isClientConnected = false;

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(5000);
            System.out.println("(i) Server started");
            Socket socket = serverSocket.accept();
            isClientConnected = true;
            System.out.println("(i) Client is connected..");
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

            Thread tIn = new Thread(() -> inThread(dataInputStream));
            Thread tOut = new Thread(() -> outThread(dataOutputStream));
            tIn.start();
            tOut.start();
            tIn.join();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            isClientConnected = false;
            System.out.println("(i) Client is disconnected..\n(i) Press Enter quit");
        }
    }

    private static void inThread(DataInputStream dataInputStream){
        do {
            String text;
            try {
                text = dataInputStream.readUTF();
                System.out.println("Client: " + text);
            } catch (SocketException | EOFException e) {
                break;
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        } while (true);
    }

    private static void outThread(DataOutputStream dataOutputStream) {
        while (true) {
            String text = SCANNER.nextLine();
            if (!isClientConnected) {
                break;
            } else {
                try {
                    dataOutputStream.writeUTF(text);
                } catch (IOException e) {
                    e.printStackTrace();
                    break;
                }
            }
        }
    }
}
