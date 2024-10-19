package ui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

// represents the sudoku application
public class SudokuApp {
    private static final String JSON_STORE = "./data/sudoku.json";
    private Scanner input;
    private User user;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;


    // EFFECTS: constructs user and runs application
    public SudokuApp() throws FileNotFoundException {
        input = new Scanner(System.in);
        user = null;
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
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

            if (command.equals("4")) {
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
        System.out.println("\t3 -> list of games");
        System.out.println("\t4 -> quit");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("1")) {
            System.out.println("Enter your name: \n");
            user = new User(input.nextLine());
            displayMenu2();
            processCommand2(input.next());

        } else if (command.equals("2")) {
            loadUser();
            if (user != null) {

            }
            displayMenu2();
            processCommand2(input.next());
            
        } else if (command.equals("3")) {
            List<Game> gl = user.getGameList();

            for (int i=0; i< gl.size(); i++) {
                printUserMatrix(gl.get(i).getMatrix().getGameboard());
                System.out.println();
                printMatrix(gl.get(i).getMatrix().getGameboard());
                System.out.println();
            }
            
        } else {
            System.out.println("Selection not valid...");
        }
    }

    public void displayMenu2() {
        System.out.println("\nSelect from:");
        System.out.println("\t1 -> New game");
        System.out.println("\t2 -> Load game");
        System.out.println("\t3 -> quit");
    }


    private void processCommand2(String command) {
        if (command.equals("1")) {
            System.out.println("Enter # of clues: \n");
            user.addGame(new Game(input.nextInt()));

        } else if (command.equals("2")) {

        } else {
            System.out.println("Selection not valid...");
        }
    }

    // EFFECTS: saves the user to file
    private void saveUser() {
        try {
            jsonWriter.open();
            jsonWriter.write(user);
            jsonWriter.close();
            System.out.println("Saved " + user.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads user from file
    private void loadUser() {
        try {
            user = jsonReader.read();
            System.out.println("Loaded " + user.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
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