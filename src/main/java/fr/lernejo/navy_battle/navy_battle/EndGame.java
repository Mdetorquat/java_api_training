package fr.lernejo.navy_battle.navy_battle;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class EndGame {

    final Game game;

    final List<int[]> shoots = new ArrayList<>();

    public EndGame(Game game) {
        this.game = game;
        for (int i = 0; i <= 9; i++) {
            for (int j = 0; j <= 9; j++) {
                shoots.add(new int[]{i, j});
            }
        }
    }

    public Game.StateOfFire End() {
        game.inGame[0] = false;
        return Game.StateOfFire.out;
    }

    public int[] CellToFire() {
        return shoots.remove(game.init.rand.nextInt(shoots.size()));
    }

    public Game.StateOfFire Shoot(int[] pos) {
        String format = String.format("%s%s", (char) (pos[1] + 'A'), pos[0] + 1);
        if (!game.inGame[0])
            return End();
        try {
            String url = String.format("%s/api/game/fire?format=%s", game.serveur.strings[0], format);
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest fire_request = HttpRequest.newBuilder()
                .uri(new URI(url)).setHeader("Accept", "application/json").setHeader("Content-Type", "application/json").GET()
                .build();
            HttpResponse<String> httpResponse = client.send(fire_request, HttpResponse.BodyHandlers.ofString());
            return Game.StateOfFire.sunk;
            //return game.serveur.requests.check.FireRequestValidation(httpResponse.body(), game);
        } catch (Exception exception) {
            return Game.StateOfFire.out;
        }
    }

}
