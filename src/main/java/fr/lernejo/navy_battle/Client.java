package fr.lernejo.navy_battle;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;

public class Client {

    final Serveur serveur;
    final String url;

    // Constructeur
    Client(Serveur s, String url) throws URISyntaxException {
        serveur = s;
        this.url = url;
    }


    public boolean clientConnect() throws URISyntaxException, IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder().uri(new URI(url + "/api/game/start")).setHeader("Accept", "application/json")
            .setHeader("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(String.format("{\"id\": \"%s\",\"url\": \"%s\",\"message\": \"%s\"}", serveur.serverID, serveur.url, "Trying to connect")))
            .build();
        client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        serveur.strings[0] = url;
        serveur.game.inGame[0] = true;
        return true;
    }
}
