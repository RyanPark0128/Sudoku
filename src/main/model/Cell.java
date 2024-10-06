package model;


// Represents each cell in the sudoku gameboard.
public class Cell {
    private boolean visible; // will make the cell show/hide the value it has
    private int value; // represents the vallue it has.
    
    /*
     * EFFECTS: creates the Cell object with value 0, and visiblity false.
     */
    public Cell() {
        value = 0;
        visible = false;
    }

    public Cell(int value) {
        this.value = value;
        visible = false;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean getVisible() {
        return visible;
    }

    public void setVisible(boolean value) {
        this.visible = value;
    }
}
