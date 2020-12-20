package io.iskaldvind;

import javax.swing.*;

public class ClientSignInWindow extends JFrame {

    private final JLabel error;
    private final JTextField loginField;
    private final JTextField passwordField;
    private boolean isButtonEnabled = true;

    interface Callback {
        void onLoginClick(String login, String password);
    }

    ClientSignInWindow(Callback callback) {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(500, 500, 300, 150);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        JLabel header = new JLabel("Пожалуйста авторизуйтесь!");
        add(header);
        loginField = new JTextField();
        loginField.setName("Логин");
        passwordField = new JTextField();
        passwordField.setName("Пароль");
        loginField.addActionListener((e) -> {
            onClick(callback);
        });
        passwordField.addActionListener((e) -> {
            onClick(callback);
        });
        JButton signInButton = new JButton("Войти");
        signInButton.addActionListener((e) -> {
            onClick(callback);
        });
        error = new JLabel(" ");
        add(loginField);
        add(passwordField);
        add(error);
        add(signInButton);
    }

    private void onClick(Callback callback) {
        if (isButtonEnabled) callback.onLoginClick(loginField.getText(), passwordField.getText());
    }

    void showError(String errorText) {
        error.setText(errorText);
    }

    void disableButton() {
        isButtonEnabled = false;
    }
}
