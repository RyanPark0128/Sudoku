package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class TestCell {
    private Cell testCell;

    @BeforeEach
    void runBefore() {
        testCell = new Cell();
    }

    @Test
    void testConstructor() {
        assertEquals(0, testCell.getValue());
        assertEquals(false, testCell.isGiven());
    }

    @Test
    void testSetValue() {
        assertEquals(0, testCell.getValue());
        testCell.setValue(9);
        assertEquals(9, testCell.getValue());
    }


    @Test
    void testSetVisible() {
        assertEquals(false, testCell.isGiven());
        testCell.setIsGiven(true);
        assertEquals(true, testCell.isGiven());
    }

    @Test
    void testUserValue() {
        testCell.setValue(5);
        testCell.setUserValue(7);

        assertNotEquals(testCell.getValue(), testCell.getUserValue());

        testCell.setUserValue(5);

        assertEquals(testCell.getValue(), testCell.getUserValue());

    }
}

