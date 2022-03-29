package fr.lernejo.navy_battle;

import fr.lernejo.navy_battle.navy_battle.EndGame;
import fr.lernejo.navy_battle.navy_battle.Game;
import fr.lernejo.navy_battle.navy_battle.Serveur;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestEndGame {
    @Test
    void argument_not_exist() {
        EndGame a = new EndGame(null);
        try {
            a.Shoot(new int[]{1, 2});
            assertEquals(Game.StateOfFire.out, a.Shoot(new int[]{1, 2}), "bad shot is");
        } catch (Exception ignored) {
        }
    }

    @Test
    void end_game_test() throws IOException {
        Game g = new Game( new Serveur("3456"));
        g.serveur.strings[0] = "http://localhost:3456";
        EndGame a = new EndGame(g);
        a.Shoot(new int[]{1,1});
    }
}
