package ui;

import model.*;
import java.util.List;
import java.util.Scanner;

public class Main {
    @SuppressWarnings("methodlength")
    public static void main(String[] args) throws Exception {
        User userOne = new User();
        Scanner sc = new Scanner(System.in);
        int option = -1;
        while (option != 3) {
            System.out.println("Choose from following option: \n 1 - new game \n 2 - load games \n 3 - quit");
            option = sc.nextInt();
            sc.nextLine();
            if (option == 1) {
                System.out.println("Enter number of clues you want (25~40):");
                userOne.addGame(new Game(sc.nextInt()));
            } else if (option == 2) {
                List<Game> list = userOne.getGameList();
                if (list.size() > 0) {
                    System.out.println("Choose from following games \n");
                    for (int i = 0; i < list.size(); i++) {
                        System.out.println((i + 1) + ". Game " + (i + 1) + "\n");
                    }
                    Game currentGame = list.get(sc.nextInt() - 1);
                    printUserMatrix(currentGame.getMatrix().getGameboard());
                } else {
                    System.out.println("\n no games available \n");
                }
            }
        }
        sc.close();
    }

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
