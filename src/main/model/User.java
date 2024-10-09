package model;

import java.util.List;
import java.util.ArrayList;

public class User {
    private List<Game> list; // list of games that the user has

    /*
     * EFFECTS: creates new user that will be playing Sudoku games
     */
    public User() {
        // stub
        list = new ArrayList<Game>();
    }

    public List<Game> getGameList() {
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
    }

     /*
     * EFFECTS: returns number of games user has.
     */
    public int getNumOfGames() {
        return list.size();
    }


    
}
