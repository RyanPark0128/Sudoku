package ui;

import model.*;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public class Main {
    @SuppressWarnings("methodlength")
    public static void main(String[] args) throws Exception {

        try {
            new SudokuApp();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run application: file not found");
        }
        // User userOne = new User();
        // Scanner sc = new Scanner(System.in);
        // int option = -1;
        // while (option != 3) {
        // System.out.println("Choose from following option: \n 1 - new game \n 2 - load
        // games \n 3 - quit");
        // option = sc.nextInt();
        // sc.nextLine();
        // if (option == 1) {
        // System.out.println("Enter number of clues you want (25~40):");
        // userOne.addGame(new Game(sc.nextInt()));
        // } else if (option == 2) {
        // List<Game> list = userOne.getGameList();
        // if (list.size() > 0) {
        // System.out.println("Choose from following games \n");
        // for (int i = 0; i < list.size(); i++) {
        // System.out.println((i + 1) + ". Game " + (i + 1) + "\n");
        // }
        // Game currentGame = list.get(sc.nextInt() - 1);
        // printUserMatrix(currentGame.getMatrix().getGameboard());
        // } else {
        // System.out.println("\n no games available \n");
        // }
        // }
        // }
        // sc.close();
    }

}
