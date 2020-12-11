package io.iskaldvind;

import javax.swing.*;
import java.awt.*;

public class Chat extends JFrame {

    public Chat() {
        setTitle("Chat");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(300, 300, 400, 400);
        setLayout(new BorderLayout());

        JPanel chatLogFrame = new JPanel();
        chatLogFrame.setLayout(new BorderLayout());
        JTextArea chatLog = new JTextArea();
        chatLog.setBorder(BorderFactory.createCompoundBorder(
                chatLog.getBorder(),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        chatLog.setBackground(new Color(240, 240, 240));
        chatLog.setEditable(false);
        JScrollPane chatLogPane = new JScrollPane(chatLog);
        chatLogFrame.add(chatLogPane);

        add(chatLogFrame, BorderLayout.CENTER);

        JPanel chatControlsFrame = new JPanel();
        chatControlsFrame.setLayout(new BorderLayout());
        JTextField chatTextField = new JTextField();
        chatTextField.setBorder(BorderFactory.createCompoundBorder(
                chatTextField.getBorder(),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        chatTextField.addActionListener( e -> {
            send(chatTextField, chatLog);
        });
        chatControlsFrame.add(chatTextField, BorderLayout.CENTER);

        JButton sendButton = new JButton("Send >");
        sendButton.setFont(new Font("Dialog", Font.BOLD, 10));
        sendButton.addActionListener( e -> {
            send(chatTextField, chatLog);
        });
        chatControlsFrame.add(sendButton, BorderLayout.LINE_END);

        add(chatControlsFrame, BorderLayout.PAGE_END);

        setVisible(true);
    }

    private void send(JTextField textField, JTextArea textArea) {
        String text = textField.getText();
        String log = textArea.getText();
        if (text.length() > 0) {
            String newLog = log.length() > 0 ? log + "\n\n" + text : text;
            textArea.setText(newLog);
            textField.setText("");
        }
    }
}
