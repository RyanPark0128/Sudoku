package model;

import java.util.List;

public class User {
    private List<Game> list;


    public User() {
        // stub
    }


    public List<Game> getGameList() {
        return list;
    }

    public Game getGame(int index) {
        return list.get(index);
    }

    public void addGame(Game game) {
        list.add(game);
    }
    
}
