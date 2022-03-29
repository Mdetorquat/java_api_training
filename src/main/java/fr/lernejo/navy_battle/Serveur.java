package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import fr.lernejo.navy_battle.handlers.HandlerRequests;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Serveur {

    public final String serverID;
    final int port;
    public final String url;
    public final String[] strings;
    final HttpServer serveur;
    final HandlerRequests requests;
    public final Game game;

    final HttpHandler ping = exchange -> {
        String body = "OK";
        exchange.sendResponseHeaders(200, body.length());
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(body.getBytes());
        }
    };

    public void htmlForm(HttpExchange httpExchange, int error) throws IOException {
        String body = String.format("<h1>%s</h1>", error);
        httpExchange.sendResponseHeaders(error, body.length());
        try (OutputStream os = httpExchange.getResponseBody()) {
            os.write(body.getBytes());
        }
    }

    final HttpHandler start = new HttpHandler() {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            if (game.inGame[0] || !httpExchange.getRequestMethod().equals("POST")) {
                htmlForm(httpExchange, 404);
                return;
            }
            try {
                requests.HandlerStart(httpExchange, false);
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        }
    };

    final HttpHandler shoot = new HttpHandler() {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            if (!httpExchange.getRequestMethod().equals("GET")) {
                htmlForm(httpExchange, 404);
                return;
            }
            requests.HandlerFire(httpExchange, false);
        }
    };


    final HttpHandler defaut = httpExchange -> htmlForm(httpExchange, 404);

    Serveur(String port) throws IOException {
        this.port = Integer.parseInt(port);
        url = String.format("http://localhost:%s", port);
        Executor thread = Executors.newFixedThreadPool(1);
        serveur = HttpServer.create(new InetSocketAddress(this.port), 50);
        serveur.setExecutor(thread);
        serveur.createContext("/ping", this.ping);
        serveur.createContext("/api/game/start", this.start);
        serveur.createContext("/api/game/fire", this.shoot);
        serveur.createContext("/", this.defaut);
        this.serverID = UUID.randomUUID().toString();
        requests = new HandlerRequests(this);
        serveur.start();
        game = new Game(this);
        strings = new String[]{""};
    }

}
