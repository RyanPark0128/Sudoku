package model;

import org.json.JSONObject;

import persistence.Writable;

// Represents each cell in the sudoku gameboard.
public class Cell implements Writable{
    private boolean isGiven; // will make the cell show/hide the value it has
    private int value; // represents the vallue it has.
    private int userValue; // values that is given by user.

    /*
     * EFFECTS: creates the Cell object with value 0, and visiblity false.
     */
    public Cell() {
        value = 0;
        isGiven = false;
    }

     /*
     * REQUIRES: 1 <= value <= 9
     * MODIFIES: this
     * EFFECTS: creates cell with given value
     */
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

    public int getUserValue() {
        return userValue;
    }

    public void setUserValue(int userValue) {
        this.userValue = userValue;
    }

    public boolean isGiven() {
        return isGiven;
    }

    public void setIsGiven(boolean value) {
        this.isGiven = value;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("isGiven", isGiven);
        json.put("value", value);
        json.put("userValue", userValue);
        return json;
    }
}
