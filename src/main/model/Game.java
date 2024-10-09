package model;

public class Game {
    private Matrix matrix;
    private int clues;
    private int timeElapsed;
    private int hintLeft;
    private boolean inProgress;

    public Game(int clues) {
        this.matrix = new Matrix(clues);
        this.clues = clues;
        this.timeElapsed = 0;
        this.hintLeft = 3;
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

}
