package fr.lernejo.navy_battle;

import fr.lernejo.navy_battle.navy_battle.Launcher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;

public class TestLauncher {
    @Test
    void good_argument() {
        new Launcher();
        try {
            String[] strings = new String[]{"1122"};
            Launcher.main(strings);

            strings = new String[]{"1123", "http://localhost:1122"};

            Launcher.main(strings);
        } catch (InterruptedException | IOException | URISyntaxException e) {
            e.printStackTrace();
            Assertions.assertEquals(0, 1, "Exception");
        }
        Assertions.assertEquals(0, 0, "Exception");
    }

    @Test
    void bad_argument() {
        try {
            String[] strings = new String[]{"##"};
            Launcher.main(strings);
            Assertions.assertEquals(1, 0, "Exception");
        } catch (IllegalArgumentException e) {
            Assertions.assertEquals(1, 1, "IllegalArgumentException");
        } catch (IOException | InterruptedException | URISyntaxException e) {
            Assertions.assertEquals(1, 0, "IllegalArgumentException");
        }
        ;

        try {
            String[] arg = new String[]{"2222", "Never gonna give you up"};
            Launcher.main(arg);
            Assertions.assertEquals(1, 0, "Exception");
        } catch (URISyntaxException e) {
            Assertions.assertEquals(1, 1, "URISyntaxException");
        } catch (IOException | InterruptedException e) {
            Assertions.assertEquals(1, 0, "URISyntaxException");
        }
    }
}
