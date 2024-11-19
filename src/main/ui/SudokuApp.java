package ui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentEvent;
import javax.swing.text.AbstractDocument;

import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

// represents the sudoku application
public class SudokuApp {
    private static final String JSON_STORE = "./data/sudoku.json";
    private User user;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private JLabel timeLabel;
    private Timer timer;

    // EFFECTS: constructs user and runs application
    public SudokuApp() throws FileNotFoundException {
        jsonReader = new JsonReader(JSON_STORE);
        jsonWriter = new JsonWriter(JSON_STORE);
        runSudoku();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runSudoku() {
        JFrame frame = new JFrame();
        JButton button = new JButton("New User");
        JButton button1 = new JButton("Load User");
        JButton button2 = new JButton("New Game");
        JButton button3 = new JButton("Load Game");
        JButton button4 = new JButton("Save");
        JButton button5 = new JButton("Quit");
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Welcome to Sudoku game");

        // Set the layout manager to arrange components vertically
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button1.setAlignmentX(Component.CENTER_ALIGNMENT);
        button2.setAlignmentX(Component.CENTER_ALIGNMENT);
        button3.setAlignmentX(Component.CENTER_ALIGNMENT);
        button4.setAlignmentX(Component.CENTER_ALIGNMENT);
        button5.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add components to the panel
        panel.add(Box.createVerticalStrut(30));
        panel.add(label);
        panel.add(Box.createVerticalStrut(30));
        if (user != null) {
            JLabel label2 = new JLabel("Welcome " + user.getName());
            panel.add(label2);
            panel.add(Box.createVerticalStrut(30));
            label2.setAlignmentX(Component.CENTER_ALIGNMENT);
        }
        panel.add(button);
        panel.add(Box.createVerticalStrut(10));
        panel.add(button1);
        panel.add(Box.createVerticalStrut(10));
        panel.add(button2);
        panel.add(Box.createVerticalStrut(10));
        panel.add(button3);
        panel.add(Box.createVerticalStrut(10));
        panel.add(button4);
        panel.add(Box.createVerticalStrut(10));
        panel.add(button5);

        // event handler
        button.addActionListener(e -> {
            frame.dispose();
            JFrame infoFrame = new JFrame();
            JPanel panel1 = new JPanel();
            JTextField tf = new JTextField();
            JButton bt1 = new JButton("Submit");

            panel1.setLayout(new BoxLayout(panel1, BoxLayout.X_AXIS));
            tf.setSize(150, 20);

            String ph = "Enter your name";
            tf.setText(ph);
            tf.setForeground(Color.GRAY);

            tf.addFocusListener(new FocusListener() {
                @Override
                public void focusGained(FocusEvent e) {
                    if (tf.getText().equals(ph)) {
                        tf.setText("");
                        tf.setForeground(Color.BLACK);
                    }
                }

                @Override
                public void focusLost(FocusEvent e) {
                    if (tf.getText().isEmpty()) {
                        tf.setForeground(Color.GRAY);
                        tf.setText(ph);
                    }
                }
            });

            bt1.addActionListener(f -> {
                this.user = new User(tf.getText());
                infoFrame.dispose();
                runSudoku();
            });

            panel1.add(tf);
            panel1.add(bt1);
            infoFrame.add(panel1);
            infoFrame.setSize(200, 60);
            infoFrame.setVisible(true);

            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    bt1.requestFocusInWindow();
                }
            });
        });

        button1.addActionListener(e -> {
            frame.dispose();
            loadUser();
            runSudoku();
        });

        button2.addActionListener(e -> {
            if (user == null) {
                JOptionPane.showMessageDialog(new JFrame(), "Create a new user first");
            } else {
                frame.dispose();
                Object[] options = { "Easy (60 clues)", "Medium (50 clues)", "Hard (40 clues)" };
                int choice = JOptionPane.showOptionDialog(
                        null,
                        "Choose an option:",
                        "Custom Option Dialog",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.INFORMATION_MESSAGE,
                        null,
                        options,
                        options[0]);

                if (choice >= 0) {
                    JOptionPane.showMessageDialog(null, "You selected: " + options[choice]);
                }
                int clue = 0;

                if (choice == 0) {
                    clue = 40;
                } else if (choice == 1) {
                    clue = 50;
                } else if (choice == 2) {
                    clue = 60;
                }

                Game g = new Game(clue);
                user.addGame(g);
                loadGame(g);
            }

        });

        button3.addActionListener(e -> {
            if (user == null) {
                JOptionPane.showMessageDialog(new JFrame(), "Create a new user first");
            } else if (user.getNumOfGames() == 0) {
                JOptionPane.showMessageDialog(new JFrame(), "There is no game");
            } else {
                frame.dispose();
                JFrame gamelist = new JFrame();
                JPanel messagePanel = new JPanel();
                messagePanel.setLayout(new BoxLayout(messagePanel, BoxLayout.Y_AXIS));

                JLabel messageLabel = new JLabel("Choose an option:");
                messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

                List<Game> list = user.getGameList();
                Dimension buttonSize = new Dimension(400, 30);

                messagePanel.add(Box.createVerticalStrut(10));
                messagePanel.add(messageLabel);
                messagePanel.add(Box.createVerticalStrut(15));
                gamelist.add(messagePanel);

                for (int i = 0; i < list.size(); i++) {
                    Game cGame = list.get(i);
                    JButton gameButton = new JButton(
                            "Game " + (i + 1) + "  clues:" + cGame.getNumOfClues() + ", time:"
                                    + cGame.getTime() + ", hint:" + cGame.getHintLeft());
                    gameButton.setMaximumSize(buttonSize);
                    gameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
                    gameButton.addActionListener(f -> {
                        loadGame(cGame);
                        gamelist.dispose();
                    });
                    messagePanel.add(gameButton);
                    messagePanel.add(Box.createVerticalStrut(10));
                }

                JDialog dialog = new JDialog(gamelist, "Custom Option Dialog", true);
                dialog.setContentPane(messagePanel);
                dialog.pack();
                dialog.setLocationRelativeTo(gamelist);
                dialog.setVisible(true);
            }
        });

        button4.addActionListener(e -> {
            saveUser();
        });

        button5.addActionListener(e -> {
            frame.dispose();
        });

        // Add the panel to the frame
        frame.add(panel);

        // Set frame properties
        frame.setSize(400, 500);
        // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public void loadGame(Game g) {
        JFrame frame = new JFrame("Sudoku Game");
        JPanel sudokuPanel = createSudokuPanel(g.getMatrix().getGameboard());
        JPanel sidebarPanel = createSidebarPanel(g, frame);
        frame.setSize(900, 900);
        frame.add(sudokuPanel, BorderLayout.CENTER);
        frame.add(sidebarPanel, BorderLayout.EAST);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        startTimer(g);
    }

    private JPanel createSudokuPanel(List<List<Cell>> matrix) {
        int GRID_SIZE = 9;
        int SUBGRID_SIZE = 3;
        JPanel sudokuPanel = new JPanel();
        sudokuPanel.setLayout(new GridLayout(SUBGRID_SIZE, SUBGRID_SIZE));

        // Create borders for the subgrids
        Border thickBorder = BorderFactory.createLineBorder(Color.BLACK, 2);
        Border thinBorder = BorderFactory.createLineBorder(Color.GRAY, 1);

        // Initialize the cells array
        JTextField[][] cells = new JTextField[GRID_SIZE][GRID_SIZE];

        // Create the subgrids and cells
        for (int row = 0; row < SUBGRID_SIZE; row++) {
            for (int col = 0; col < SUBGRID_SIZE; col++) {
                // Create a panel for each 3x3 subgrid
                JPanel subgridPanel = new JPanel(new GridLayout(SUBGRID_SIZE, SUBGRID_SIZE));
                subgridPanel.setBorder(thickBorder);

                // Add cells to the subgrid
                for (int i = 0; i < SUBGRID_SIZE; i++) {
                    for (int j = 0; j < SUBGRID_SIZE; j++) {
                        int cellRow = row * SUBGRID_SIZE + i;
                        int cellCol = col * SUBGRID_SIZE + j;

                        JTextField cell = new JTextField();
                        cell.setHorizontalAlignment(JTextField.CENTER);
                        cell.setFont(new Font("SansSerif", Font.BOLD, 20));
                        cell.setBorder(thinBorder);

                        if (matrix.get(cellRow).get(cellCol).isGiven()) {
                            cell.setText(String.valueOf(matrix.get(cellRow).get(cellCol).getValue()));
                            cell.setEditable(false);
                            cell.setForeground(Color.GRAY);
                        } else {
                            cell.getDocument().addDocumentListener(new DocumentListener() {
                                @Override
                                public void insertUpdate(DocumentEvent e) {
                                    changedUpdate(e);
                                }

                                @Override
                                public void removeUpdate(DocumentEvent e) {
                                    changedUpdate(e);
                                }

                                @Override
                                public void changedUpdate(DocumentEvent e) {
                                    if (cell.getText().matches("^[1-9]$")) {
                                        matrix.get(cellRow).get(cellCol).setUserValue(Integer.parseInt(cell.getText()));
                                    }
                                }
                            });
                        }
                        // Set the custom DocumentFilter for Sudoku cells
                        ((AbstractDocument) cell.getDocument()).setDocumentFilter(new SudokuCellDocumentFilter());

                        cells[cellRow][cellCol] = cell;
                        subgridPanel.add(cell);
                    }
                }
                sudokuPanel.add(subgridPanel);
            }
        }

        return sudokuPanel;
    }

    private JPanel createSidebarPanel(Game g, JFrame f) {
        JPanel sidebarPanel = new JPanel();
        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
        sidebarPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Labels
        JLabel hintsLabel = new JLabel("Hints Left: " + g.getHintLeft());
        hintsLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));

        JLabel difficultyLabel = new JLabel("Difficulty: ");
        difficultyLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));

        timeLabel = new JLabel(formatTime(g.getTime() / 60, g.getTime() % 60));
        timeLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));

        JButton hintButton = new JButton("Use Hint");
        JButton solutionButton = new JButton("Submit answer");
        JButton quitButton = new JButton("Exit");

        if (g.getHintLeft() < 1) {
            hintButton.setEnabled(false);
        }

        hintButton.addActionListener(e -> {
            g.getMatrix().generateUserMatrix(5);
            g.useHint();
            f.dispose();
            timer.stop();
            loadGame(g);
        });

        solutionButton.addActionListener(e -> {
            if (g.getMatrix().checkAnswer()) {
                JOptionPane.showMessageDialog(new JFrame(), "Your answer is correct!");
                f.dispose();
                timer.stop();
                loadGame(g);
            } else {
                JOptionPane.showMessageDialog(new JFrame(), "Your answer is incorrect, try again");
            }
        });

        quitButton.addActionListener(e -> {
            timer.stop();
            f.dispose();
            runSudoku();
        });

        // Add labels to sidebar
        sidebarPanel.add(hintsLabel);
        sidebarPanel.add(Box.createVerticalStrut(20));
        sidebarPanel.add(difficultyLabel);
        sidebarPanel.add(Box.createVerticalStrut(20));
        sidebarPanel.add(timeLabel);
        sidebarPanel.add(Box.createVerticalStrut(50));
        sidebarPanel.add(hintButton);
        sidebarPanel.add(Box.createVerticalStrut(10));
        sidebarPanel.add(solutionButton);
        sidebarPanel.add(Box.createVerticalStrut(10));
        sidebarPanel.add(quitButton);
        sidebarPanel.add(Box.createVerticalStrut(10));
        sidebarPanel.add(Box.createVerticalGlue());

        return sidebarPanel;
    }

    private void startTimer(Game g) {
        // Update the timeLabel every second
        timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int time = g.getTime();
                g.setTime(++time);
                timeLabel.setText(formatTime(time / 60, time % 60));
            }
        });
        timer.start();
    }

    public String formatTime(int min, int sec) {
        return String.format("Time Elapsed: %02d:%02d", min, sec);
    }

    // EFFECTS: displays menu of options to user
    public void displayMenu() {
        // System.out.println("\nSelect from:");
        // System.out.println("\t1 -> Create new user");
        // System.out.println("\t2 -> Load existing user");
        // System.out.println("\t3 -> Start a new game");
        // System.out.println("\t4 -> Load list of games to play");
        // System.out.println("\t5 -> Save progress");
        // System.out.println("\t6 -> quit");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    @SuppressWarnings("methodlength")
    private void processCommand(String command) {
        // if (command.equals("1")) {
        // System.out.println("Enter your name: \n");
        // input.nextLine();
        // user = new User(input.nextLine());
        // } else if (command.equals("2")) {
        // loadUser();
        // if (user == null) {
        // System.out.println("No user to load");
        // } else {
        // System.out.println("User " + user.getName() + " loaded");
        // }
        // } else if (command.equals("3")) {
        // if (user == null) {
        // System.out.println("Create user first");
        // } else {
        // System.out.println("Enter # of clues: \n");
        // Game newGame = new Game(input.nextInt());
        // user.addGame(newGame);
        // playGame(newGame);
        // }
        // } else if (command.equals("4")) {
        // if (user == null) {
        // System.out.println("Create user first");
        // } else {
        // List<Game> gl = user.getGameList();
        // if (gl.size() > 0) {
        // for (int i = 0; i < gl.size(); i++) {
        // System.out.println(
        // (i + 1) + ") Game " + (i + 1) + "\n\t\t clues: " + gl.get(i).getNumOfClues()
        // + "\n\t\t hintLeft: " + gl.get(i).getHintLeft() + "\n\t\t timeElapesd: "
        // + gl.get(i).getTime() + "\n");
        // }
        // System.out.println("Which game do you want to load (" + (1) + " ~ " +
        // (gl.size()) + ")" + ": ");
        // playGame(gl.get(input.nextInt() - 1));
        // } else {
        // System.out.println("No game available to load");
        // }
        // }

        // } else if (command.equals("5")) {
        // saveUser();
        // } else {
        // System.out.println("Selection not valid...");
        // }
    }

    // REQUIRES: Correctly instantiated Game object
    // MODIFIES: this
    // EFFECTS: processes user command
    @SuppressWarnings("methodlength")
    public void playGame(Game g) {
        // boolean keepGoing = true;
        // String command = null;

        // while (keepGoing) {
        // printUserMatrix(g.getMatrix().getGameboard());
        // System.out.println("1 -> use hint");
        // System.out.println("2 -> submit answer");
        // System.out.println("3 -> show answers");
        // System.out.println("4 -> Back to main menu");
        // command = input.next();
        // command = command.toLowerCase();

        // if (command.equals("4")) {
        // keepGoing = false;
        // } else if (command.equals("1")) {
        // if (g.getHintLeft() > 0) {
        // g.useHint();
        // } else {
        // System.out.println("No more hint left");
        // }
        // } else if (command.equals("2")) {
        // if (!g.getMatrix().checkAnswer()) {
        // System.out.println("Your answer to the matrix is wrong");
        // } else {
        // System.out.println("Congrats! you solved the sudoku puzzle");
        // }

        // } else if (command.equals("3")) {
        // printMatrix(g.getMatrix().getGameboard());
        // }
        // }
    }

    // EFFECTS: saves the user to file
    private void saveUser() {
        try {
            jsonWriter.open();
            jsonWriter.write(user);
            jsonWriter.close();
            JOptionPane.showMessageDialog(new JFrame(), "Successfuly saved user " + user.getName());
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads user from file
    private void loadUser() {
        try {
            user = jsonReader.read();
            JOptionPane.showMessageDialog(new JFrame(), "Successfuly loaded user " + user.getName());

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