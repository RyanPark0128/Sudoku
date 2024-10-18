package persistence;

import model.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.json.*;

// Represents a reader that reads user from JSON data stored in file
public class JsonReader {
    private String source;
    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads user from file and returns it;
    // throws IOException if an error occurs reading data from file
    public User read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseUser(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses user from JSON object and returns it
    private User parseUser(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        User user = new User(name);
        addGames(user, jsonObject);
        return user;
    }

    // MODIFIES: User
    // EFFECTS: parses Games from JSON object and adds them to user
    private void addGames(User user, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("list");
        for (Object json : jsonArray) {
            JSONObject nextGame = (JSONObject) json;
            addGame(user, nextGame);
        }
    }

    // MODIFIES: User
    // EFFECTS: parses Game from JSON object and adds it to user
    private void addGame(User user, JSONObject jsonObject) {
        int clues = jsonObject.getInt("clues");
        int timeElapsed = jsonObject.getInt("timeElapsed");
        int hintLeft = jsonObject.getInt("timeElapsed");
        JSONObject jsonMatrix = jsonObject.getJSONObject("matrix");
        Matrix matrix = addMatrix(jsonMatrix);

        user.addGame(new Game(clues, timeElapsed, hintLeft, matrix));
    }

    private Matrix addMatrix(JSONObject jsonMatrix) {
        JSONArray array = jsonMatrix.getJSONArray("gameboard");
        List<List<Cell>> gameboard = new ArrayList<List<Cell>>();
        for (int i = 0; i < 9; i++) {
            gameboard.add(new ArrayList<Cell>());
        }


        for (int i=0; i< 9; i++) {
            JSONArray subArray = array.getJSONArray(i);
            for (int j=0; j<9; j++) {
                int userValue = subArray.getJSONObject(j).getInt("userValue");
                int value = subArray.getJSONObject(j).getInt("value");
                boolean isGiven = subArray.getJSONObject(j).getBoolean("isGiven");
                gameboard.get(i).add(new Cell(isGiven, value, userValue));
            }
        }

        return new Matrix(gameboard);
    }
}
