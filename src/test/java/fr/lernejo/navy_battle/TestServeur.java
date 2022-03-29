package fr.lernejo.navy_battle;

import fr.lernejo.navy_battle.navy_battle.Serveur;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class TestServeur {
    static Serveur ServerTestStart(String port) throws Exception {
        try {
            return new Serveur(port);
        } catch (IOException error) {
            Assertions.assertEquals(0, 1, "Nombre d'exceptions");
            throw new Exception("Le lancement du test Serveur a échoué");
        }
    }

    static void StopServer(Serveur server) {
        if (server == null)
            return;
        server.serveur.stop(0);
    }

    @Test
    void ping_test_serveur() {
        Serveur serveur = null;
        try {
            serveur = ServerTestStart("5678");
        } catch (Exception e) {
            Assertions.assertEquals(0, 1, "Number of exception on server");
        }
        try {
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:5678/ping"))
                .setHeader("Accept", "application/json")
                .setHeader("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString("Ping"))
                .build();
            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            Assertions.assertEquals(200, httpResponse.statusCode(), "Status code 200 was expected for a POST on /ping");
            Assertions.assertEquals("OK", httpResponse.body(), "OK body was expected for a POST  on /ping");
        } catch (URISyntaxException | InterruptedException | IOException e) {
            Assertions.assertEquals(0, 1, "Number of exception for POST on /ping");
        }
        StopServer(serveur);
    }

    @Test
    void drop_test_serveur() {
        Serveur serveur = null;
        try {
            serveur = ServerTestStart("9121");
        } catch (Exception e) {
            Assertions.assertEquals(0, 1, "Number of exception");
        }
        try {
            HttpClient cli = HttpClient.newHttpClient();
            HttpRequest requetePost = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:9121/false"))
                .setHeader("Accept", "application/json")
                .setHeader("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString("Ping"))
                .build();
            HttpResponse<String> response = cli.send(requetePost, HttpResponse.BodyHandlers.ofString());
            Assertions.assertEquals(404, response.statusCode(), "404 was expected for a POST on /wtf");
        } catch (URISyntaxException | InterruptedException | IOException e) {
            Assertions.assertEquals(0, 1, "Number of exception");
        }

        try {
            HttpClient cli = HttpClient.newHttpClient();
            HttpRequest requetePost = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:9121/api/game/fire"))
                .setHeader("Accept", "application/json")
                .setHeader("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString("Ping"))
                .build();
            HttpResponse<String> response = cli.send(requetePost, HttpResponse.BodyHandlers.ofString());
            Assertions.assertEquals(404, response.statusCode(), "Status code 404 was expected for a POST on /yolo");
        } catch (URISyntaxException | InterruptedException | IOException e) {
            Assertions.assertEquals(0, 1, "Number of exception");
        }

        try {
            HttpClient cli = HttpClient.newHttpClient();
            HttpRequest requeteget = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:9121/api/game/fire"))
                .setHeader("Accept", "application/json")
                .setHeader("Content-Type", "application/json")
                .GET()
                .build();
            HttpResponse<String> response = cli.send(requeteget, HttpResponse.BodyHandlers.ofString());
            Assertions.assertEquals(400, response.statusCode(), "Status code 404 was expected for a POST on /yolo");
        } catch (URISyntaxException | InterruptedException | IOException e) {
            Assertions.assertEquals(0, 1, "Number of exception");
        }

        try {
            HttpClient cli = HttpClient.newHttpClient();
            HttpRequest requetePost = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:9121/api/game/start"))
                .setHeader("Accept", "application/json")
                .setHeader("Content-Type", "application/json")
                .GET()
                .build();
            HttpResponse<String> response = cli.send(requetePost, HttpResponse.BodyHandlers.ofString());
            Assertions.assertEquals(404, response.statusCode(), "Status code 404 was expected for get on /api/game/start");
        } catch (URISyntaxException | InterruptedException | IOException e) {
            Assertions.assertEquals(0, 1, "Number of exception");
        }
        assert serveur != null;
        serveur.game.inGame[0] = true;
        try {
            HttpClient cli = HttpClient.newHttpClient();
            HttpRequest requetePost = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:9121/api/game/start"))
                .setHeader("Accept", "application/json")
                .setHeader("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString("Ping"))
                .build();
            HttpResponse<String> response = cli.send(requetePost, HttpResponse.BodyHandlers.ofString());
            Assertions.assertEquals(404, response.statusCode(), "Status code 404 was expected for get on /api/game/start");
        } catch (URISyntaxException | InterruptedException | IOException e) {
            Assertions.assertEquals(0, 1, "Number of exception");
        }
        StopServer(serveur);
    }
}
