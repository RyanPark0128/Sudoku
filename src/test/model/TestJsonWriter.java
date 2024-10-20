package model;

import org.junit.jupiter.api.Test;

import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestJsonWriter {

    @Test
    void testWriterInvalidFile() {
        try {
            User user = new User("Ryan");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyUser() {
        try {
            User user = new User("Hyungyu");
            JsonWriter writer = new JsonWriter("./data/testWriterUser.json");
            writer.open();
            writer.write(user);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterUser.json");
            user = reader.read();
            assertEquals("Hyungyu", user.getName());
            assertEquals(0, user.getNumOfGames());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            User user = new User("Ryan");
            user.generateNewGame(30);
            user.generateNewGame(40);
            JsonWriter writer = new JsonWriter("./data/testWriterCorrectSave.json");
            writer.open();
            writer.write(user);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterCorrectSave.json");
            User userLoaded = reader.read();

            assertTrue(compareUsers(user, userLoaded));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterWith3Games() {
        try {
            User user = new User("Donghwan");
            user.generateNewGame(30);
            user.generateNewGame(40);
            user.generateNewGame(35);
            JsonWriter writer = new JsonWriter("./data/testWriter3Games.json");
            writer.open();
            writer.write(user);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriter3Games.json");
            User userLoaded = reader.read();

            assertTrue(compareUsers(user, userLoaded));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    public boolean compareUsers(User u1, User u2) {
        assertEquals(u1.getName(), u2.getName());
        assertEquals(u1.getNumOfGames(), u2.getNumOfGames());
        for (int i = 0; i < u1.getNumOfGames(); i++) {
            Game game1 = u1.getGameList().get(i);
            Game game2 = u2.getGameList().get(i);

            if (game1.getHintLeft() != game2.getHintLeft() || game1.getTime() != game2.getTime()
                    || game1.getNumOfClues() != game2.getNumOfClues()) {
                return false;
            }

            List<List<Cell>> m1 = game1.getMatrix().getGameboard();
            List<List<Cell>> m2 = game2.getMatrix().getGameboard();

            for (int j = 0; j < 9; j++) {
                for (int k = 0; k < 9; k++) {
                    if (m1.get(j).get(k).getValue() != m2.get(j).get(k).getValue()
                            || m1.get(j).get(k).getUserValue() != m2.get(j).get(k).getUserValue()
                            || m1.get(j).get(k).isGiven() != m2.get(j).get(k).isGiven()) {
                        return false;
                    }
                }
            }

        }

        return true;
    }
}
