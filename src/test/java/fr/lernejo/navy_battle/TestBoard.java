package fr.lernejo.navy_battle;

import fr.lernejo.navy_battle.navy_battle.Board;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestBoard {
    @Test
    void good_position() {
        Board test = new Board();
        List<List<int[]>> ships = new ArrayList<>();
        List<int[]> arrayList = new ArrayList<>();
        arrayList.add(new int[]{0, 0});
        ships.add(arrayList);
        assertTrue(test.CorrectLocation(ships, 5, 5, 0, 2), "Le bateau est dans les limites du jeu");
        assertTrue(test.CorrectLocation(ships, 5, 5, 1, 2), "Le bateau est dans les limites du jeu");
        assertTrue(test.CorrectLocation(ships, 0, 3, 1, 2), "Le bateau est dans les limites du jeu");
        assertTrue(test.CorrectLocation(ships, 3, 0, 0, 2), "Le bateau est dans les limites du jeu");
        assertFalse(test.CorrectLocation(ships, 2, 0, 1, 2), "Le bateau n'est pas dans les limites du jeu");
        assertFalse(test.CorrectLocation(ships, 0, 2, 0, 2), "Le bateau n'est pas dans les limites du jeu");
        assertFalse(test.CorrectLocation(ships, 0, 1, 1, 2), "Le bateau n'est pas dans les limites du jeu");
        assertFalse(test.CorrectLocation(ships, 1, 0, 0, 2), "Le bateau n'est pas dans les limites du jeu");
        assertFalse(test.CorrectLocation(ships, 0, 0, 1, 1), "Le bateau n'est pas dans les limites du jeu");
        assertFalse(test.CorrectLocation(ships, 0, 0, 0, 1), "Le bateau n'est pas dans les limites du jeu");
    }

    @Test
    void position() {
        Board test = new Board();
        List<List<int[]>> ships = new ArrayList<>();
        test.ShipPosition(ships, 2, 0, 1, 2);
        test.ShipPosition(ships, 0, 2, 0, 2);
    }


    @Test
    void generate_game_board() {
        Board test = new Board();
        for (int i = 0; i < 25; i++) {
            int k = 0;
            List<List<int[]>> ships = test.BoardGeneretion();
            for (List<int[]> ship_loc : ships) {
                for (int[] ignored : ship_loc) {
                    k++;
                }
            }
            Assertions.assertEquals(17, k, "Number of filled cell");
        }
    }
}
