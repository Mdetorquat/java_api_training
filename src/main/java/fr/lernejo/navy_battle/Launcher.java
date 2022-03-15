package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpServer;
import fr.lernejo.navy_battle.handlers.CallHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Launcher {

    public static void main(String[] args) throws IOException {
        if (args.length < 1 || args.length > 2) {
            throw new IllegalArgumentException("Il ne peut y avoir que 1 ou 2 arguments !");
        }
        else
        {
            int port = Integer.parseInt(args[0]);
            if (args.length == 1) {
                serverStart(port);
            }

            if (args.length == 2) {
                //
            }

        }
    }

    public static HttpServer serverStart(int port) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        server.createContext("/ping", new CallHandler());
        return server;
    }
}
