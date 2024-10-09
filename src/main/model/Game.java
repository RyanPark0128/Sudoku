package model;

public class Game {
    private Matrix matrix;
    private int clues;
    private int timeElapsed;
    private int hintLeft;


    public Game(int clues) {
        // answerMatrix = new Matrix();
        // userMatrix = new Matrix(answerMatrix);
    }

    public void useHint() {
        //stub
    }

    public int getHintLeft() {
        return hintLeft;
    }

    public int getTime() {
        return timeElapsed;
    }

}
