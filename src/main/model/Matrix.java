package model;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

// Represents a matrix for sudoku game.
public class Matrix {
    private List<List<Cell>> gameboard; // Matrix that represents the game board, consisting of 2d array of Cell class
    private List<Integer> list; // Array of number 1 to 9 in random order for randomness when generating game

    /*
     * REQUIRES: 20 <= clues <= 45
     * EFFECTS: Initialize 2d array with Cell. Cell will have value of 0 initially.
     * Also, list of number 1 to 9 in random order is generated.
     * makes a call to method generateMatrix which will populate the matrix with appropriate value.
     * Also decide which cells are going to be shown to user using generateUserMatrix method.
     */
    public Matrix(int clues) {
        list = new ArrayList<>();
        gameboard = new ArrayList<List<Cell>>();

        for (int i = 0; i < 9; i++) {
            gameboard.add(new ArrayList<Cell>());
            list.add(i+1);
        }

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                gameboard.get(i).add(new Cell());
            }
        }

        generateMatrix(0, 0);
        generateUserMatrix(clues);
    }

    public Matrix() {
        list = new ArrayList<>();
        gameboard = new ArrayList<List<Cell>>();

        for (int i = 0; i < 9; i++) {
            gameboard.add(new ArrayList<Cell>());
            list.add(i+1);
        }

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                gameboard.get(i).add(new Cell());
            }
        }
    }


    /*
     * REQUIRES: 0 <= row <= 9, 0 <= col <= 9, first call to the method must be row == col == 0;
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
     * EFFECTS: Populates matrix recursively, make validation before assigning value
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
     * REQUIRES: 0 <= row <= 9, 0 <= col <= 9
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


    public int getCellValue(int row, int col) {
        return gameboard.get(row).get(col).getValue();
    }

    public void setCellValue(int row, int col, int value) {
        gameboard.get(row).get(col).setValue(value);
    }

     /*
     * EFFECTS: print every element of the matrix
     */
    // public void printMatrix() {
    //     System.out.println();
    //     System.out.println("- - - - - - - - - - - - -");
    //     for (int i = 0; i < gameboard.size(); i++) {
    //         System.out.print("| ");
    //         for (int j = 0; j < gameboard.get(i).size(); j++) {
    //             System.out.print(gameboard.get(i).get(j).getValue() + " ");
    //             if (j % 3 == 2) {
    //                 System.out.print("| ");
    //             }
    //         }
    //         System.out.println();
    //         if (i % 3 == 2) {
    //             System.out.println("- - - - - - - - - - - - -");
    //         }
    //     }
    //     System.out.println();
    // }


    /*
     * EFFECTS: print gameboard with hidden numbers, so that user can solve it.
     */
    // public void printUserMatrix() {
    //     // stub
    //     System.out.println();
    //     System.out.println("- - - - - - - - - - - - -");
    //     for (int i = 0; i < gameboard.size(); i++) {
    //         System.out.print("| ");
    //         for (int j = 0; j < gameboard.get(i).size(); j++) {
    //             if (gameboard.get(i).get(j).isGiven()) {
    //                 System.out.print(gameboard.get(i).get(j).getValue() + " ");
    //             } else {
    //                 System.out.print("- ");           
    //             }
    //             if (j % 3 == 2) {
    //                 System.out.print("| ");
    //             }
    //         }
    //         System.out.println();
    //         if (i % 3 == 2) {
    //             System.out.println("- - - - - - - - - - - - -");
    //         }
    //     }
    //     System.out.println();
    // }


    /*
     * EFFECTS: Compares the user answer to the sudoku game and the actual answer of the game.
     * returns true if it matches else false.
     */
    public boolean checkAnswer() {
        //stub
        for (int i=0; i<9; i++) {
            for (int j=0; j<9; j++) {
                if (gameboard.get(i).get(j).getValue() != gameboard.get(i).get(j).getUserValue()) {
                    return false;
                }
            }
        }

        return true;
    }


}
