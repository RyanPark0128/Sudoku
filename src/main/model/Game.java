package model;

import org.json.JSONObject;

import persistence.Writable;

// Represents each game in user's database.
public class Game implements Writable {
    private Matrix matrix; // matrix of the game.
    private int clues; // number of clues that will be open in the gameboard
    private int timeElapsed; // time passed since the game started
    private int hintLeft; // number of hints left for this game
    private boolean inProgress; // whether the game is paused or in progress

    /*
     * REQUIRES: 20 <= clues <= 45
     * MODIFIES: this
     * EFFECTS: creates game object with given clues.
     */
    public Game(int clues) {
        this.matrix = new Matrix(clues);
        this.clues = clues;
        this.timeElapsed = 0;
        this.hintLeft = 3;
        this.inProgress = false;
    }

    /*
     * REQUIRES: 20 <= clues <= 45
     * MODIFIES: this
     * EFFECTS: creates game object with given value when loading saved file.
     */
    public Game(int clues, int timeElapsed, int hintLeft, Matrix gameboard) {
        this.matrix = gameboard;
        this.clues = clues;
        this.hintLeft = hintLeft;
        this.timeElapsed = timeElapsed;
        this.inProgress = false;
    }

    /*
     * MODIFIES: this
     * EFFECTS: checks if there is hint left, if there is use hint.
     */
    public void useHint() {
        hintLeft = hintLeft - 1;
    }

    public int getHintLeft() {
        return hintLeft;
    }

    public int getTime() {
        return timeElapsed;
    }

    public Matrix getMatrix() {
        return matrix;
    }

    public int getNumOfClues() {
        return clues;
    }

    public boolean isPlaying() {
        return inProgress;
    }

    public void changeGameStatus() {
        this.inProgress = !inProgress;
    }

    // EFFECTS: returns Json Object for Game
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("matrix", matrix.toJson());
        json.put("clues", clues);
        json.put("hintLeft", hintLeft);
        json.put("timeElapsed", timeElapsed);
        json.put("inProgress", inProgress);
        return json;
    }
}
