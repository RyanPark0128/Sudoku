package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class TestGame {
    private Game testGame;

    @BeforeEach
    void runBefore() {
        testGame = new Game(25);
    }

    @Test
    void testConstructor() {
        assertEquals(25, testGame.getNumOfClues());
        assertEquals(false, testGame.isPlaying());
        assertEquals(3, testGame.getHintLeft());
        assertEquals(0, testGame.getTime());

        Matrix testMatrix = testGame.getMatrix();
        List<List<Cell>> board = testMatrix.getGameboard();

        assertEquals(9, board.size());
        assertEquals(9, board.get(0).size());
    }

    @Test
    void checkHintUsage() {
        testGame.useHint();
        assertEquals(2, testGame.getHintLeft());

        testGame.useHint();
        testGame.useHint();

        assertEquals(0, testGame.getHintLeft());
    }

    @Test
    void checkUserPlaying() {
        testGame.changeGameStatus();
        assertEquals(true, testGame.isPlaying());
        testGame.changeGameStatus();
        assertEquals(false, testGame.isPlaying());
    }

    @Test
    void testTime() {
        testGame.setTime(10);
        assertEquals(10, testGame.getTime());
    }
}
