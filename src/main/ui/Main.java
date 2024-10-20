package ui;

import java.io.FileNotFoundException;

// main class which runs the application
public class Main {
    public static void main(String[] args) throws Exception {
        try {
            new SudokuApp();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run application: file not found");
        }
    }

}
