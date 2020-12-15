package io.iskaldvind;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

public class Client {

    private static final Scanner SCANNER = new Scanner(System.in);
    private static Boolean isConnected = false;

    public static void main(String[] args) {
        String errorMessage = "(i) Server is disconnected..\n(i) Press Enter quit";
        try {
            Socket socket = new Socket("localhost", 5000);
            isConnected = true;
            System.out.println("(i) Connected to Server..");
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

            Thread tIn = new Thread(() -> inThread(dataInputStream));
            Thread tOut = new Thread(() -> outThread(dataOutputStream));

            tIn.start();
            tOut.start();
            tIn.join();

        } catch (ConnectException e) {
            errorMessage = "(i) Server is down!";
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            isConnected = false;
            System.out.println(errorMessage);
        }
    }

    private static void inThread(DataInputStream dataInputStream) {
        do {
            String text;
            try {
                text = dataInputStream.readUTF();
                System.out.println("Server: " + text);
            } catch (SocketException | EOFException e) {
                break;
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        } while (true);
    }

    private static void outThread(DataOutputStream dataOutputStream) {
        String text;
        while (true) {
            text = SCANNER.nextLine();
            if (!isConnected) {
                break;
            } else {
                try {
                    dataOutputStream.writeUTF(text);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
