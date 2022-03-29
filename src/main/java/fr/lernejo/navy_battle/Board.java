package fr.lernejo.navy_battle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board {
    final int[] ships = new int[]{5, 4, 3, 3, 2};
    final Random rand;

    Board() {
        rand = new Random();
    }

    public boolean CorrectLocation(List<List<int[]>> ships, int x, int y, int pos, int sizeofship) {
        if (pos != 0) {
            if (y - sizeofship < 0)
                return false;
            for (List<int[]> ship : ships) {
                for (int[] loc : ship) {
                    for (int i = 0; i < sizeofship; i++) {
                        if (loc[0] == x && loc[1] == (y - i))
                            return false;
                    }
                }
            }
        }
        else {
            if (x - sizeofship < 0)
                return false;
            for (List<int[]> ship : ships) {
                for (int[] loc : ship) {
                    for (int i = 0; i < sizeofship; i++) {
                        if (loc[0] == (x - i) && loc[1] == y)
                            return false;
                    }
                }
            }
        }
        return true;
    }

    public void ShipPosition(List<List<int[]>> ships, int x, int y, int pos, int sizeofship) {
        List<int[]> listships = new ArrayList<>();
        if (pos == 0) {
            for (int i = 0; i < sizeofship; i++)
                listships.add(new int[]{x - i, y});
        } else {
            for (int i = 0; i < sizeofship; i++)
                listships.add(new int[]{x, y - i});
        }
        ships.add(listships);
    }

    public List<List<int[]>> BoardGeneretion() {
        List<List<int[]>> lists = new ArrayList<>();
        for (int ship : ships) {
            boolean iscorrect = false;
            while (!iscorrect) {
                int x = rand.nextInt(10);
                int y = rand.nextInt(10);
                int nextposition = rand.nextInt(2);
                iscorrect = CorrectLocation(lists, x, y, nextposition, ship);
                if (iscorrect) {
                    ShipPosition(lists, x, y, nextposition, ship);
                }
            }
        }
        return lists;
    }
}
