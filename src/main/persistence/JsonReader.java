package persistence;

import model.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// Represents a reader that reads user from JSON data stored in file
public class JsonReader {

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
    }

    // EFFECTS: reads user from file and returns it;
    // throws IOException if an error occurs reading data from file
    public User read() throws IOException {

    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
  
    }

    // EFFECTS: parses user from JSON object and returns it
    private User parseUser(JSONObject jsonObject) {

    }

    // MODIFIES: User
    // EFFECTS: parses Games from JSON object and adds them to user
    private void addGames(User user, JSONObject jsonObject) {

    }

    // MODIFIES: User
    // EFFECTS: parses Game from JSON object and adds it to user
    private void addGame(User wr, JSONObject jsonObject) {

    }
}
