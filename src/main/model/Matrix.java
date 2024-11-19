package model;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONObject;

import persistence.Writable;

// Represents a matrix for sudoku game.
public class Matrix implements Writable {
    private List<List<Cell>> gameboard; // Matrix that represents the game board, consisting of 2d array of Cell class
    private List<Integer> list; // Array of number 1 to 9 in random order for randomness when generating game

    /*
     * REQUIRES: 20 <= clues <= 45
     * MODIFIES: This
     * EFFECTS: Initialize 2d array with Cell. Cell will have value of 0 initially.
     * Also, list of number 1 to 9 in random order is generated.
     * makes a call to method generateMatrix which will populate the matrix with
     * appropriate value.
     * Also decide which cells are going to be shown to user using
     * generateUserMatrix method.
     */
    public Matrix(int clues) {
        list = new ArrayList<>();
        gameboard = new ArrayList<List<Cell>>();

        for (int i = 0; i < 9; i++) {
            gameboard.add(new ArrayList<Cell>());
            list.add(i + 1);
        }

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                gameboard.get(i).add(new Cell());
            }
        }

        generateMatrix(0, 0);
        generateUserMatrix(clues);
    }

    /*
     * REQUIRES: gameboard.size() == 9 && gameboard.get(0).size() == 9
     * MODIFIES: This
     * EFFECTS: Initialize 2d array with cells with given gameboard.
     */
    public Matrix(List<List<Cell>> gameboard) {
        this.gameboard = gameboard;
    }

    /*
     * MODIFIES: This
     * EFFECTS: Initialize 2d array with cells that has value of 0.
     */
    public Matrix() {
        list = new ArrayList<>();
        gameboard = new ArrayList<List<Cell>>();

        for (int i = 0; i < 9; i++) {
            gameboard.add(new ArrayList<Cell>());
            list.add(i + 1);
        }

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                gameboard.get(i).add(new Cell());
            }
        }
    }

    /*
     * REQUIRES: 0 <= row < 9, 0 <= col < 9, first call to the method must be row ==
     * col == 0;
     * MODIFIES: this
     * EFFECTS: Populates matrix recursively, make validation before assigning value
     */
    public boolean generateMatrix(int row, int col) {
        if (row == gameboard.size()) {
            return true;
        }

        int nextRow = (col == gameboard.size() - 1) ? row + 1 : row;
        int nextCol = (col == gameboard.size() - 1) ? 0 : col + 1;
        Collections.shuffle(list);

        for (int num : list) {
            if (validation(row, col, num)) {
                gameboard.get(row).get(col).setValue(num);
                if (generateMatrix(nextRow, nextCol)) {
                    return true;
                }
                gameboard.get(row).get(col).setValue(0);
            }
        }

        return false;
    }

    /*
     * REQUIRES: 20 <= cellsGiven <= 45
     * MODIFIES: this
     * EFFECTS: Randomly decide which cells will be shown to the user
     */
    public void generateUserMatrix(int cellsGiven) {
        int count = cellsGiven;
        Random rand = new Random();

        while (count > 0) {
            int randomRow = rand.nextInt(9);
            int randomCol = rand.nextInt(9);
            while (gameboard.get(randomRow).get(randomCol).isGiven()) {
                randomRow = rand.nextInt(9);
                randomCol = rand.nextInt(9);
            }
            gameboard.get(randomRow).get(randomCol).setIsGiven(true);
            count--;
        }
    }

    /*
     * REQUIRES: 0 <= row < 9, 0 <= col < 9
     * MODIFIES: This
     * EFFECTS: validates if the number chosen can be assigned to the given cell.
     * checks both column and row to see if there is duplicate number. Also checks
     * if the number is present in its subgrid.
     */
    public boolean validation(int row, int col, int value) {
        for (int i = 0; i < col; i++) {
            if (gameboard.get(row).get(i).getValue() == value) {
                return false;
            }
        }

        for (int i = 0; i < row; i++) {
            if (gameboard.get(i).get(col).getValue() == value) {
                return false;
            }
        }

        int subGridRowStart = (row / 3) * 3;
        int subGridColStart = (col / 3) * 3;

        for (int i = subGridRowStart; i < subGridRowStart + 3; i++) {
            for (int j = subGridColStart; j < subGridColStart + 3; j++) {
                if (gameboard.get(i).get(j).getValue() == value) {
                    return false;
                }
            }
        }

        return true;
    }

    public List<List<Cell>> getGameboard() {
        return gameboard;
    }

    /*
     * REQUIRES: 0 <= row < 9, 0 <= col < 9
     * EFFECTS: returns the value of a cell at given row and column
     */
    public int getCellValue(int row, int col) {
        return gameboard.get(row).get(col).getValue();
    }

    /*
     * REQUIRES: 0 <= row < 9, 0 <= col < 9, 1 <= value <= 9
     * MODIFIES: value of Cell
     * EFFECTS: sets the value of cell at given location to given value
     */
    public void setCellValue(int row, int col, int value) {
        gameboard.get(row).get(col).setValue(value);
    }

    /*
     * EFFECTS: Compares the user answer to the sudoku game and the actual answer of
     * the game.
     * returns true if it matches else false.
     */
    public boolean checkAnswer() {
        // stub
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (!(gameboard.get(i).get(j).isGiven()) && gameboard.get(i).get(j).getValue() != gameboard.get(i).get(j).getUserValue()) {
                    return false;
                }
            }
        }

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                gameboard.get(i).get(j).setIsGiven(true);
            }
        }
        return true;
    }

    /*
     * EFFECTS: creates Json object for Matrix
     */
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("gameboard", cellsToJson());
        return json;
    }

    // EFFECTS: returns 2D Json object of Cell class.
    private JSONArray cellsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (int i = 0; i < gameboard.size(); i++) {
            JSONArray jsonSubArray = new JSONArray();
            for (int j = 0; j < gameboard.get(i).size(); j++) {
                jsonSubArray.put(gameboard.get(i).get(j).toJson());
            }
            jsonArray.put(jsonSubArray);
        }

        return jsonArray;
    }

}
