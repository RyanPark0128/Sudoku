package model;

import java.io.IOException;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;


public class TestJsonReader {
    //  @Test
    // void testReaderNonExistentFile() {
    //     JsonReader reader = new JsonReader("./data/noSuchFile.json");
    //     try {
    //         User user = reader.read();
    //         fail("IOException expected");
    //     } catch (IOException e) {
    //         // pass
    //     }
    // }

    // @Test
    // void testReaderEmptyWorkRoom() {
    //     JsonReader reader = new JsonReader("./data/testReaderEmptyWorkRoom.json");
    //     try {
    //         User user = reader.read();
    //         assertEquals("Ryan", user.getName());
    //         assertEquals(0, user.getNumOfGames());
    //     } catch (IOException e) {
    //         fail("Couldn't read from file");
    //     }
    // }

    // @Test
    // void testReaderGeneralWorkRoom() {
    //     JsonReader reader = new JsonReader("./data/testReaderGeneralWorkRoom.json");
    //     try {
    //         User user = reader.read();
    //         assertEquals("Ryan", user.getName());
    //         List<Game> games = user.getGameList();

    //     } catch (IOException e) {
    //         fail("Couldn't read from file");
    //     }
    // }
}
