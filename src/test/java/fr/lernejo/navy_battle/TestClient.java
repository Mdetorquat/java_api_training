package fr.lernejo.navy_battle;

import fr.lernejo.navy_battle.navy_battle.Client;
import fr.lernejo.navy_battle.navy_battle.Serveur;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;

public class TestClient {
    @Test
    void accept() throws IOException, URISyntaxException, InterruptedException {
        Client client = new Client(new Serveur("9876"), "http://localhost:9876");
        client.clientConnect();
    }
}
