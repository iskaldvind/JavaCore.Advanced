package io.iskaldvind;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;

public class ClientChatWindow extends JFrame {

    final JTextArea messagesList;
    final JTextField messageField;
    final JScrollPane scrollPane;

    interface Callback {

        void sendMessage(String text);
    }

    ClientChatWindow(Callback callback) {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(500, 500, 600, 500);
        setLayout(new GridLayout(2, 1));
        // Список сообщений
        messagesList = new JTextArea();
        messagesList.setEditable(false);
        scrollPane = new JScrollPane(messagesList);
        add(scrollPane);
        DefaultCaret caret = (DefaultCaret)messagesList.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        // Панель новго сообщения
        JPanel sendMassagePanel = new JPanel();
        sendMassagePanel.setLayout(new BoxLayout(sendMassagePanel, BoxLayout.X_AXIS));
        sendMassagePanel.setSize(600, 50);
        messageField = new JTextField();
        messageField.addActionListener( (e) -> {
            onSend(messageField, callback);
        });
        JButton sendButton = new JButton("Отправить");
        sendMassagePanel.add(messageField);
        sendMassagePanel.add(sendButton);
        add(sendMassagePanel);
        sendButton.addActionListener((e) -> {
            onSend(messageField, callback);
        });
    }

    private void onSend(JTextField textField, Callback callback) {
        String text = textField.getText();
        textField.setText("");
        callback.sendMessage(text);
        textField.requestFocusInWindow();
    }

    public void onNewMessage(String message) {
        messagesList.setText(messagesList.getText() + "\n" + message);
    }
}
