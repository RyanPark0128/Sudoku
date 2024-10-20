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

            if (command.equals("6")) {
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
        System.out.println("\t1 -> Create new user");
        System.out.println("\t2 -> Load existing user");
        System.out.println("\t3 -> Start a new game");
        System.out.println("\t4 -> Load list of games to play");
        System.out.println("\t5 -> Save progress");
        System.out.println("\t6 -> quit");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("1")) {
            System.out.println("Enter your name: \n");
            input.nextLine();
            user = new User(input.nextLine());
        } else if (command.equals("2")) {
            loadUser();
            if (user == null) {
                System.out.println("No user to load");
            } else {
                System.out.println("User " + user.getName() + " loaded");
            }
        } else if (command.equals("3")) {
            if (user == null) {
                System.out.println("Create user first");
            } else {
                System.out.println("Enter # of clues: \n");
                Game newGame = new Game(input.nextInt());
                user.addGame(newGame);
                playGame(newGame);
            }
        } else if (command.equals("4")) {
            if (user == null) {
                System.out.println("Create user first");
            } else {
                List<Game> gl = user.getGameList();
                if (gl.size() > 0) {
                    for (int i = 0; i < gl.size(); i++) {
                        System.out.println(
                                (i + 1) + ") Game " + (i + 1) + "\n\t\t clues: " + gl.get(i).getNumOfClues()
                                        + "\n\t\t hintLeft: " + gl.get(i).getHintLeft() + "\n\t\t timeElapesd: "
                                        + gl.get(i).getTime() + "\n");
                    }
                    System.out.println("Which game do you want to load (" + (1) + " ~ " + (gl.size()) + ")" + ": ");
                    playGame(gl.get(input.nextInt() - 1));
                } else {
                    System.out.println("No game available to load");
                }
            }

        } else if (command.equals("5")) {
            saveUser();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // REQUIRES: Correctly instantiated Game object
    // MODIFIES: this
    // EFFECTS: processes user command
    public void playGame(Game g) {
        boolean keepGoing = true;
        String command = null;

        while (keepGoing) {
            printUserMatrix(g.getMatrix().getGameboard());
            System.out.println("1 -> use hint");
            System.out.println("2 -> submit answer");
            System.out.println("3 -> show answers");
            System.out.println("4 -> Back to main menu");
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("4")) {
                keepGoing = false;
            } else if (command.equals("1")) {
                if (g.getHintLeft() > 0) {
                    g.useHint();
                } else {
                    System.out.println("No more hint left");
                }
            } else if (command.equals("2")) {
                if (!g.getMatrix().checkAnswer()) {
                    System.out.println("Your answer to the matrix is wrong");
                } else {
                    System.out.println("Congrats! you solved the sudoku puzzle");
                }

            } else if (command.equals("3")) {
                printMatrix(g.getMatrix().getGameboard());
            }
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

    // REQUIRES: gameboard.size() == 9, gameboard[0].size() == 9
    // EFFECT: Prints the solution matrix of the Sudoku Game
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

    // REQUIRES: gameboard.size() == 9, gameboard[0].size() == 9
    // EFFECT: Prints the user solution matrix of the Sudoku Game
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