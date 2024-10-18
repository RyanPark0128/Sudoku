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
    void testWriterEmptyWorkroom() {
        // try {
        //     User user = new User("Ryan");
        //     JsonWriter writer = new JsonWriter("./data/testWriterEmptyWorkroom.json");
        //     writer.open();
        //     writer.write(user);
        //     writer.close();

        //     JsonReader reader = new JsonReader("./data/testWriterEmptyWorkroom.json");
        //     user = reader.read();
        //     assertEquals("Ryan", user.getName());
        //     assertEquals(0, user.getNumOfGames());
        // } catch (IOException e) {
        //     fail("Exception should not have been thrown");
        // }
    }

    @Test
    void testWriterGeneralWorkroom() {
        // try {
        //     User user = new User("Ryan");
        //     user.generateNewGame(30);
        //     user.generateNewGame(40);
        //     JsonWriter writer = new JsonWriter("./data/testWriterGeneralWorkroom.json");
        //     writer.open();
        //     writer.write(user);
        //     writer.close();

        //     JsonReader reader = new JsonReader("./data/testWriterGeneralWorkroom.json");
        //     user = reader.read();
        //     assertEquals("Ryan", user.getName());
        //     List<Game> games = user.getGameList();
        //     assertEquals(2, games.size());
        //     assertEquals(30, games.get(0).getNumOfClues());
        //     assertEquals(40, games.get(1).getNumOfClues());

        // } catch (IOException e) {
        //     fail("Exception should not have been thrown");
        // }
    }
}
