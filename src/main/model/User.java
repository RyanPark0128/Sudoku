package model;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import persistence.Writable;

import java.util.ArrayList;

// Represent user that will be playing the game.
public class User implements Writable {
    private List<Game> list; // list of games that the user has
    private String name;
    private EventLog eventLog;

    /*
     * EFFECTS: creates new user that will be playing Sudoku games
     */
    public User(String name) {
        list = new ArrayList<Game>();
        this.name = name;
        eventLog = EventLog.getInstance();
        eventLog.logEvent(new Event("User: " + name + " logged in"));
    }

    public List<Game> getGameList() {
        eventLog.logEvent(new Event("Loaded Game list"));
        return list;
    }

    /*
     * REQUIRES: 0 <= index < list.size()
     * EFFECTS: returns the game at the index given
     */
    public Game getGame(int index) {
        return list.get(index);
    }

    /*
     * MODIFIES: this
     * EFFECTS: adds a game to the list
     */
    public void addGame(Game game) {
        list.add(game);
        eventLog.logEvent(new Event("game has been added to the game list"));
    }

    /*
     * REQUIRES: 25 <= clues <= 40
     * MODIFIES: this
     * EFFECTS: generate a game and adds to the list
     */
    public void generateNewGame(int clues) {
        list.add(new Game(clues));
        eventLog.logEvent(new Event("game has been added to the game list"));
    }

    /*
     * EFFECTS: returns number of games user has.
     */
    public int getNumOfGames() {
        return list.size();
    }

    // EFFECTS: return name of user
    public String getName() {
        return name;
    }

    // EFFECTS: returns Json object of user
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("list", gamesToJson());
        return json;
    }

    // EFFECTS: returns Games in this user as a JSON array
    private JSONArray gamesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Game g : list) {
            jsonArray.put(g.toJson());
        }

        return jsonArray;
    }
}
