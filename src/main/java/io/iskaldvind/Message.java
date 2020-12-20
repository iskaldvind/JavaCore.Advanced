package io.iskaldvind;

public class Message {
    Client author;
    Client target;
    String text;

    Message(Client author, Client target, String text) {
        this.author = author;
        this.target = target;
        this.text = text;
    }
}
