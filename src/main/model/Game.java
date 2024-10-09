package model;

public class Game {
    private Matrix matrix;
    private int clues;
    private int timeElapsed;
    private int hintLeft;
    private boolean inProgress;


    public Game(int clues) {
        // answerMatrix = new Matrix();
        // userMatrix = new Matrix(answerMatrix);
    }

    /*
     * MODIFIES: this
     * EFFECTS: checks if there is hint left, if there is use hint.
     */
    public void useHint() {
        //stub
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
        //stub

        return 0;
    }

    public boolean isPlaying() {
        // stub;
        return true;
    }

    public void changeGameStatus() {
        // stub;
    }

}
