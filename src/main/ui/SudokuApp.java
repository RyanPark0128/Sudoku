package ui;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

// represents the sudoku application
public class SudokuApp {
    private static final String JSON_STORE = "./data/sudoku.json";
    private Scanner input;
    private User workRoom;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;


    // EFFECTS: constructs user and runs application
    public SudokuApp() throws FileNotFoundException {
        input = new Scanner(System.in);
        workRoom = new User("Alex's workroom");
        // jsonWriter = new JsonWriter(JSON_STORE);
        // jsonReader = new JsonReader(JSON_STORE);
        runSudoku();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runSudoku() {
        boolean keepGoing = true;
        String command = null;
        input = new Scanner(System.in);

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("3")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nGoodbye!");
    }

    // EFFECTS: displays menu of options to user
    public void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\t1 -> New user");
        System.out.println("\t2 -> Load user");
        System.out.println("\t3 -> quit");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("1")) {
            // addThingy();
        } else if (command.equals("2")) {
            // printThingies();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // EFFECTS: saves the user to file
    private void saveUser() {

    }

    // MODIFIES: this
    // EFFECTS: loads user from file
    private void loadUser() {

    }



    //REQUIRES: gameboard.size() == 9, gameboard[0].size() == 9
    //EFFECT: Prints the solution matrix of the Sudoku Game
    public static void printMatrix(List<List<Cell>> gameboard) {
        System.out.println();
        System.out.println("- - - - - - - - - - - - -");
        for (int i = 0; i < gameboard.size(); i++) {
            System.out.print("| ");
            for (int j = 0; j < gameboard.get(i).size(); j++) {
                System.out.print(gameboard.get(i).get(j).getValue() + " ");
                if (j % 3 == 2) {
                    System.out.print("| ");
                }
            }
            System.out.println();
            if (i % 3 == 2) {
                System.out.println("- - - - - - - - - - - - -");
            }
        }
        System.out.println();
    }


    //REQUIRES: gameboard.size() == 9, gameboard[0].size() == 9
    //EFFECT: Prints the user solution matrix of the Sudoku Game
    public static void printUserMatrix(List<List<Cell>> gameboard) {
        // stub
        System.out.println();
        System.out.println("- - - - - - - - - - - - -");
        for (int i = 0; i < gameboard.size(); i++) {
            System.out.print("| ");
            for (int j = 0; j < gameboard.get(i).size(); j++) {
                if (gameboard.get(i).get(j).isGiven()) {
                    System.out.print(gameboard.get(i).get(j).getValue() + " ");
                } else {
                    System.out.print("- ");
                }
                if (j % 3 == 2) {
                    System.out.print("| ");
                }
            }
            System.out.println();
            if (i % 3 == 2) {
                System.out.println("- - - - - - - - - - - - -");
            }
        }
        System.out.println();
    }
}