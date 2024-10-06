package model;


// Represents each cell in the sudoku gameboard.
public class Cell {
    private boolean isGiven; // will make the cell show/hide the value it has
    private int value; // represents the vallue it has.
    
    /*
     * EFFECTS: creates the Cell object with value 0, and visiblity false.
     */
    public Cell() {
        value = 0;
        isGiven = false;
    }

    public Cell(int value) {
        this.value = value;
        isGiven = false;
    }

    public int getValue() {
        return value;
    }
    

    public void setValue(int value) {
        this.value = value;
    }

    public boolean isGiven() {
        return isGiven;
    }

    public void setIsGiven(boolean value) {
        this.isGiven = value;
    }
}
