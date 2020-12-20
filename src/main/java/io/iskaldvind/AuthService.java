package io.iskaldvind;

import java.util.ArrayList;

public class AuthService {

    ArrayList<Client> clients = new ArrayList<>();

    AuthService() {
        clients.add(new Client("Pavel", "pavel1", "1234"));
        clients.add(new Client("Oleg", "oleg1", "1234"));
        clients.add(new Client("Nick", "nick1", "4321"));
    }

    synchronized Client auth(String login, String password) {
        for (Client client: clients) {
            if (client.login.equals(login) && client.password.equals(password)) return client;
        }
        return null;
    }

    synchronized Client getByName(String name) {
        for (Client client: clients) {
            if (client.name.equals(name)) return client;
        }
        return null;
    }
}
