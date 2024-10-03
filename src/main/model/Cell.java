package model;

public class Cell {
    private boolean visible;
    private int value;
    
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
}
