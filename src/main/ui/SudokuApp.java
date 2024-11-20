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
    @SuppressWarnings("methodlength")
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

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button1.setAlignmentX(Component.CENTER_ALIGNMENT);
        button2.setAlignmentX(Component.CENTER_ALIGNMENT);
        button3.setAlignmentX(Component.CENTER_ALIGNMENT);
        button4.setAlignmentX(Component.CENTER_ALIGNMENT);
        button5.setAlignmentX(Component.CENTER_ALIGNMENT);

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

        button.addActionListener(e -> {
            frame.dispose();
            newUserHandler();
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
                newGameHandler();
            }
        });

        button3.addActionListener(e -> {
            if (user == null) {
                JOptionPane.showMessageDialog(new JFrame(), "Create a new user first");
            } else if (user.getNumOfGames() == 0) {
                JOptionPane.showMessageDialog(new JFrame(), "There is no game. Create new game");
            } else {
                frame.dispose();
                loadGameHandler();
            }
        });

        button4.addActionListener(e -> {
            saveUser();
        });

        button5.addActionListener(e -> {
            frame.dispose();
        });

        frame.add(panel);
        frame.setSize(400, 500);
        frame.setVisible(true);
    }

    /*
     * EFFECTS: Creates new user with given name
     */
    @SuppressWarnings("methodlength")
    public void newUserHandler() {
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
    }

    /*
     * MODIFIES: user
     * EFFECTS: Creates new game with chosen option of clues
     */
    @SuppressWarnings("methodlength")
    public void newGameHandler() {
        Object[] options = { "Easy (60 clues)", "Medium (50 clues)", "Hard (40 clues)" };
        int choice = JOptionPane.showOptionDialog(
                null,
                "Choose an option:",
                "",
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

    /*
     * EFFECTS: Shows list of games that the user currently has , and opens them
     * upon clicking them.
     */
    @SuppressWarnings("methodlength")
    public void loadGameHandler() {
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
            Game currentGame = list.get(i);
            JButton gameButton = new JButton(
                    "Game " + (i + 1) + "  clues:" + currentGame.getNumOfClues() + ", time:"
                            + currentGame.getTime() + ", hint:" + currentGame.getHintLeft());
            gameButton.setMaximumSize(buttonSize);
            gameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            gameButton.addActionListener(f -> {
                loadGame(currentGame);
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

    /*
     * EFFECTS: Opens the given game in a gameboard so that user can solve them.
     */
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

    /*
     * EFFECTS: Creates the gameboard panel
     */
    @SuppressWarnings("methodlength")
    private JPanel createSudokuPanel(List<List<Cell>> matrix) {
        int grid = 9;
        int subgrid = 3;
        JPanel sudokuPanel = new JPanel();
        sudokuPanel.setLayout(new GridLayout(subgrid, subgrid));

        Border thickBorder = BorderFactory.createLineBorder(Color.BLACK, 2);
        Border thinBorder = BorderFactory.createLineBorder(Color.GRAY, 1);

        JTextField[][] cells = new JTextField[grid][grid];

        for (int row = 0; row < subgrid; row++) {
            for (int col = 0; col < subgrid; col++) {
                JPanel subgridPanel = new JPanel(new GridLayout(subgrid, subgrid));
                subgridPanel.setBorder(thickBorder);

                for (int i = 0; i < subgrid; i++) {
                    for (int j = 0; j < subgrid; j++) {
                        int cellRow = row * subgrid + i;
                        int cellCol = col * subgrid + j;

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

    /*
     * EFFECTS: Creates the sidebar panel where it shows information about the game
     */
    @SuppressWarnings("methodlength")
    private JPanel createSidebarPanel(Game g, JFrame f) {
        JPanel sidebarPanel = new JPanel();
        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
        sidebarPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

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
            g.getMatrix().generateUserMatrix(1);
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

    /*
     * MODIFIES: timer for each game
     * EFFECTS: Starts the time when the game start
     */
    private void startTimer(Game g) {
        timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int time = g.getTime();
                g.setTime(++time);
                timeLabel.setText(formatTime(time / 60, time % 60));
            }
        });
        timer.start();
    }

    /*
     * EFFECTS: Helper function that returns string of formatted time
     */
    public String formatTime(int min, int sec) {
        return String.format("Time Elapsed: %02d:%02d", min, sec);
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