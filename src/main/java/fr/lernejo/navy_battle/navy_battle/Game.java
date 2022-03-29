package fr.lernejo.navy_battle.navy_battle;

import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

public class Game {

    // Etat des différentes actions de tir
    public enum StateOfFire {
        miss,
        out,
        sunk,
        hit
    }

    public final Serveur serveur;
    final EndGame shoot;
    final Pattern pattern = Pattern.compile("^[A-J](10|[1-9])$");
    final Board init;
    public final List<List<int[]>> board;
    public final boolean[] inGame;

    // Constructeur du jeu
    public Game(Serveur serveur) {
        this.serveur = serveur;
        shoot = new EndGame(this);
        init = new Board();
        board = init.BoardGeneretion();
        inGame = new boolean[]{false};
    }

    public void Fire() {
        int[] posShoot = shoot.CellToFire();
        posShoot[0]++;
        shoot.Shoot(posShoot);
    }

    // Fonction qui permet d'indiquer l'état du tir et, si nécessaire, retire le(s) bâteau(x) du jeu.
    public StateOfFire ShootAction(String case_position) throws IOException {
        if (!pattern.matcher(case_position).find())
            return StateOfFire.out;

        int x_pos = Integer.parseInt(case_position.split("[A-J]")[1]) - 1;
        int y_pos = (case_position.charAt(0) - 'A');
        for (List<int[]> shipPos : board) {
            for (int[] ships : shipPos) {
                if (ships[0] == x_pos && ships[1] == y_pos) {
                    shipPos.remove(ships);

                    // Si la taille du bateau vaut 0 alors on le retire du jeu car il est coulé
                    if (shipPos.size() == 0) {
                        board.remove(shipPos);
                        return StateOfFire.sunk;
                    }

                    // Sinon il est touché
                    return StateOfFire.hit;
                }
            }
        }
        // Dommage, c'est raté !
        return StateOfFire.miss;
    }
}
