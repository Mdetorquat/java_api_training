package fr.lernejo.navy_battle.navy_battle;

import java.io.IOException;
import java.net.URISyntaxException;

public class Launcher {

    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException {
        Serveur serveur;
        if (args.length < 1 || args.length > 2) {
            System.out.println("Veuillez spécifier le port d'écoute");
            System.exit(1);
        }
        try {
            serveur = new Serveur(args[0]);
            if (args.length == 2) {
                Client client = new Client(serveur, args[1]);
                client.clientConnect();
            }
        } catch (IOException | URISyntaxException | InterruptedException e) {
            System.out.println("Not an Integer");
            throw e;
        }
    }
}
