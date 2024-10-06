package model;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestMatrix {
    private Matrix testMatrix;
    
    @BeforeEach
    void runBefore() {
        testMatrix = new Matrix();
    }

    @Test
    void testSetCellValue() {
        assertEquals(0, testMatrix.getCellValue(5, 2));
        testMatrix.setCellValue(7, 3, 5);
        assertEquals(5, testMatrix.getCellValue(7, 3));
    }

    @Test
    void testValidationMethod() {

        testMatrix.setCellValue(0, 0, 5);

        // Should not allow another 5 in the same row
        assertFalse(testMatrix.validation(0, 1, 5));

        // Should not allow another 5 in the same column
        assertFalse(testMatrix.validation(1, 0, 5));

        // Should not allow another 5 in the same subgrid
        assertFalse(testMatrix.validation(1, 1, 5));

        // Should allow 5 in a different row, column, and subgrid
        assertTrue(testMatrix.validation(3, 3, 5));

        // Place another number and test again
        testMatrix.setCellValue(0, 1, 6);

        // Should not allow 6 in the same row
        assertFalse(testMatrix.validation(0, 2, 6));

        // Should not allow 6 in the same column
        assertFalse(testMatrix.validation(2, 1, 6));

        // Should not allow 6 in the same subgrid
        assertFalse(testMatrix.validation(1, 2, 6));
    }

    @Test
    void testGeneratedMatrix() {
        testMatrix.generateMatrix(0, 0);
        List<List<Cell>> list = testMatrix.getGameboard();

        assertEquals(true, validateRows(list));
        assertEquals(true, validateColumn(list));
        assertEquals(true, validateSubgrid(list));

        List<List<Cell>> invalidMatrix = generateInvalidMatrix();

        assertEquals(false, validateRows(invalidMatrix));
        assertEquals(false, validateColumn(invalidMatrix));
        assertEquals(false, validateSubgrid(invalidMatrix));
    }
    
    private boolean validateRows(List<List<Cell>> board) {
        for (int i=0; i < board.size(); i++) {
            boolean[] check = new boolean[9];
            Arrays.fill(check, false);

            for (int j=0; j < board.get(i).size(); j++) {
                int cellValue = board.get(i).get(j).getValue();
                if (check[cellValue-1] == true) {
                    return false;
                }
                check[cellValue-1] = true;
            }
        }
        return true;
    }


    private boolean validateColumn(List<List<Cell>> board) {
        for (int i=0; i < board.size(); i++) {
            boolean[] check = new boolean[9];
            Arrays.fill(check, false);

            for (int j=0; j < board.get(i).size(); j++) {
                int cellValue = board.get(j).get(i).getValue();
                if (check[cellValue-1] == true) {
                    return false;
                }
                check[cellValue-1] = true;
            }
        }
        return true;
    }

    private boolean validateSubgrid(List<List<Cell>> board) {
        for (int row = 0; row < 9; row += 3) {
            for (int col = 0; col < 9; col += 3) {
                if (!validateSubgrid(board, row, col)) {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean validateSubgrid(List<List<Cell>> board, int row, int col) {
        boolean[] check = new boolean[9];
        Arrays.fill(check, false);
        for (int i = row; i < row+ 3; i++) {
            for (int j = col; j < col + 3; j++) {
                int value = board.get(i).get(j).getValue();
                if (check[value-1]) {
                    return false; // Invalid value or duplicate in subgrid
                }
                check[value-1] = true;
            }
        }
        return true;

    }

    private List<List<Cell>> generateInvalidMatrix() {
        List<List<Cell>> invalid = new ArrayList<List<Cell>>();
        Random rand = new Random();

        for (int i = 0; i < 9; i++) {
            invalid.add(new ArrayList<Cell>());
        }

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                invalid.get(i).add(new Cell(1+ rand.nextInt(9)));
            }
        }


        return invalid;
    }
}
