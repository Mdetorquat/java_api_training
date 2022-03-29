package fr.lernejo.navy_battle;

import fr.lernejo.navy_battle.navy_battle.Game;
import fr.lernejo.navy_battle.navy_battle.Serveur;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class TestGame {
    @Test
    void shot_out_of_board() throws IOException {
        Game g = new Game(null);
        Assertions.assertEquals(Game.StateOfFire.out, g.ShootAction("false"), "Le tir est en dehors des limites du jeu");
        Assertions.assertEquals(Game.StateOfFire.out, g.ShootAction("X99"), "Le tir est en dehors des limites du jeu");
        Assertions.assertEquals(Game.StateOfFire.out, g.ShootAction("Z9"), "Le tir est en dehors des limites du jeu");
        Assertions.assertEquals(Game.StateOfFire.out, g.ShootAction("A0"), "Le tir est en dehors des limites du jeu");
        Assertions.assertEquals(Game.StateOfFire.out, g.ShootAction("apple"), "Le tir est en dehors des limites du jeu");
    }

    @Test
    void fire() throws IOException {
        Serveur s = new Serveur("1235");
        Game g = new Game(s);
        g.Fire();
    }

    @Test
    void good_shot() throws IOException {
        Game game = new Game(null);
        int cpt = 0;
        while (cpt < 100) {
            int[] array = game.board.get(0).get(0);
            if (game.board.get(0).size() == 1)
                Assertions.assertEquals(Game.StateOfFire.sunk, game.ShootAction(String.format("%s%s", (char) (array[1] + 'A'), array[0] + 1)), "Shoot is a sunk");
            else
                Assertions.assertEquals(Game.StateOfFire.hit, game.ShootAction(String.format("%s%s", (char) (array[1] + 'A'), array[0] + 1)), "Shoot is a hit");
            if (game.board.size() == 0)
                break;
            cpt++;
        }
        for (int i = 1; i <= 10; i++) {
            for (int j = 0; j <= 9; j++) {
                Assertions.assertEquals(Game.StateOfFire.miss, game.ShootAction(String.format("%s%s", (char) (j + 'A'), i)), "Shoot is a miss");
            }
        }
    }
}
