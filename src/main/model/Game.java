package model;

public class Game {
    private Matrix answerMatrix;
    private Matrix userMatrix;
    private String difficulty;
    private int timeElapsed;
    private int hintLeft;


    public Game() {
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
