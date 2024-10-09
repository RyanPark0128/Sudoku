package model;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.ArrayList;

public class TestUser {
    private User testUser;

    @BeforeEach
    void runBefore() {
        testUser = new User();
    }


    @Test
    void testConstructor() {
        assertEquals(0, testUser.getNumOfGames());
    }

    @Test
    void testAddGetGame() {
        Game testGame = new Game(25);
        testUser.addGame(testGame);
        assertEquals(1, testUser.getNumOfGames());
        assertEquals(testGame, testUser.getGame(0));
    }

    @Test
    void testGetList() {
        Game testGame = new Game(25);
        Game testGameTwo = new Game(30);

        testUser.addGame(testGame);
        testUser.addGame(testGameTwo);

        List<Game> gameList = testUser.getGameList();

        List<Game> compare = new ArrayList<Game>();
        compare.add(testGame);
        compare.add(testGameTwo);

        for (int i=0; i<2; i++) {
            assertEquals(compare.get(i), gameList.get(i));
        }
    }

    

    
}
