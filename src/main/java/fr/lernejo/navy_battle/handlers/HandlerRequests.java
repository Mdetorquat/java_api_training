package fr.lernejo.navy_battle.handlers;

import com.sun.net.httpserver.HttpExchange;
import fr.lernejo.navy_battle.Game;
import fr.lernejo.navy_battle.Serveur;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class HandlerRequests {

    final Serveur serveur;
    //public final CheckRequests check;

    public HandlerRequests(Serveur s) {
        serveur = s;
        //check = new CheckRequests();
    }


    public void HandlerFire(HttpExchange httpExchange, boolean bool) throws IOException {
        String pos = "";
        try {
            pos = httpExchange.getRequestURI().getQuery().split("pos=")[1];
            Game.StateOfFire f = serveur.game.ShootAction(pos);
            String format = String.format("{\"consequence\": \"%s\",\"shipLeft\": %s}", f.toString(), (serveur.game.board.size() > 0) && (serveur.game.inGame[0]));
            if (!bool) {
                httpExchange.getResponseHeaders().add("Content-Type", "application/json");
            }
            httpExchange.sendResponseHeaders(202, format.length());
            try (
                OutputStream os = httpExchange.getResponseBody()) {
                os.write(format.getBytes());
            }
            serveur.game.Fire();
        } catch (Exception e) { serveur.htmlForm(httpExchange, 400); }
    }

    String RequestBody(HttpExchange httpExchange) throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(httpExchange.getRequestBody(), StandardCharsets.UTF_8);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        int tmp;
        StringBuilder buf = new StringBuilder(512);
        while ((tmp = bufferedReader.read()) != -1) {
            buf.append((char) tmp);
        }
        bufferedReader.close();
        inputStreamReader.close();
        return buf.toString();
    }

    public void HandlerStart(HttpExchange httpExchange, boolean test) throws IOException, InterruptedException {
        try {
            String body = RequestBody(httpExchange);
            //String server_url = check.StartRequestValidation(body);
            //serveur.strings[0] = server_url;
        } catch (Exception e) {
            serveur.htmlForm(httpExchange, 400);
        }
        String bodyresponse = String.format("{\"id\": \"%s\",\"url\": \"%s\",\"message\": \"%s\"}", serveur.serverID, serveur.url, "OK");
        httpExchange.sendResponseHeaders(202, bodyresponse.length());
        if (!test)
            httpExchange.getResponseHeaders().add("Content-Type", "application/json");

        try (
            OutputStream os = httpExchange.getResponseBody()) {
            os.write(bodyresponse.getBytes());
        }
        serveur.game.inGame[0] = true; serveur.game.Fire();
    }
}
