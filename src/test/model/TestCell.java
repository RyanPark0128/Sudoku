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
        assertEquals(false, testCell.getVisible());
    }

    @Test
    void testSetValue() {
        assertEquals(0, testCell.getValue());
        testCell.setValue(9);
        assertEquals(9, testCell.getValue());
    }

    @Test
    void testSetVisible() {
        assertEquals(false, testCell.getVisible());
        testCell.setVisible(true);
        assertEquals(true, testCell.getVisible());
    }
}

