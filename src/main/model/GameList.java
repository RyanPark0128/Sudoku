package model;

import java.util.List;

public class GameList {
    private List<Game> list;


    public GameList() {
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
