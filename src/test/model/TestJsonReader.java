package model;

import java.io.IOException;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;


public class TestJsonReader {
    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            User user = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyUser.json");
        try {
            User user = reader.read();
            assertEquals("Sooho", user.getName());
            assertEquals(0, user.getNumOfGames());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderCorrectLoad() {
        JsonReader reader = new JsonReader("./data/testReaderCorrectLoad.json");
        try {
            User user = reader.read();
            assertEquals("Sooho", user.getName());
            List<Game> games = user.getGameList();
            assertEquals(1, games.size());
            assertEquals(3, games.get(0).getHintLeft());
            assertEquals(21, games.get(0).getTime());
            assertEquals(30, games.get(0).getNumOfClues());

            List<List<Cell>> gameboard = games.get(0).getMatrix().getGameboard();

            assertEquals(3, gameboard.get(0).get(0).getValue());
            assertEquals(6, gameboard.get(0).get(1).getValue());
            assertEquals(4, gameboard.get(0).get(2).getValue());
            assertEquals(5, gameboard.get(0).get(3).getValue());
            assertEquals(8, gameboard.get(0).get(4).getValue());
            assertEquals(7, gameboard.get(0).get(5).getValue());
            assertEquals(1, gameboard.get(0).get(6).getValue());
            assertEquals(9, gameboard.get(0).get(7).getValue());
            assertEquals(2, gameboard.get(0).get(8).getValue());

            assertEquals(8, gameboard.get(8).get(0).getValue());
            assertEquals(2, gameboard.get(8).get(1).getValue());

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}