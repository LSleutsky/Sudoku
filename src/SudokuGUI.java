/**
 * PROJECT: Sudoku Puzzle
 *
 * CLASS: SudokuGUI
 *
 * DESCRIPTION: This class creates a 9x9 Sudoku Grid, a File Menu, and a Tools menu. The grid will populate random cells
 *              with random numbers from 1 to 9 that are read in from an external. Random cells will be left empty in
 *              order to take user input - the user input must also be numbers from 1 to 9. A user can click submit to
 *              submit the completed puzzle, and have the puzzle checked for passing the rules, and winning the game.
 *              There will be an option to launch a clock timer to see how long it takes to complete the puzzle, as well
 *              as simple instruction detailing the rules of the Sudoku game. A user will also be able to save their
 *              puzzle and then open it again later for additional attempts. There will also be a "Solve" action, which
 *              will let the user automatically solve the puzzle correctly.
 *
 * DATE: November 2015
 *
 * AUTHOR: Lush Sleutsky
 *
 * IDE: IntelliJ IDEA 15.0.1
 */

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.*;
import java.util.*;

public class SudokuGUI extends JFrame {

    // set unchangeable number of rows to 9
    private static final int ROWS = 9;
    // set unchangeable number of columns to 9
    private static final int COLUMNS = 9;
    // holds the array that contains the completed puzzle
    public static int[][] array;
    // array to hold a sudoku puzzle for a user to complete
    public static int[][] sudoku;
    // put JTextField boxes into multi-dimensional array to create a 9x9 grid to hold Sudoku number values
    private static JTextField[][] inputBoxes = new JTextField[9][9];
    // declare font size
    private static int fontSize = 30;

    /**
     * SudokuGUI constructor creates a 9x9 grid (to house a Sudoku puzzle), and creates a file menu, puzzle menu, and a
     * tools menu. The grid will be separated into 9 sets of 3x3 colored blocks.
     * The file menu will have options to submit a puzzle, save a puzzle, open a puzzle, solve a puzzle, and exit fully.
     * The tools menu will have the options to launch a set of simple game instructions, as well as a clock timer.
     */
    public SudokuGUI() {
        // create main frame
        JFrame frame = new JFrame("Sudoku Puzzle");
        // create a new font to set font type, font weight, and font size
        Font font = new Font("Helvetica", Font.BOLD, fontSize);
        // set a color that will be used to set the font color of uneditable puzzle squares
        String cellColor = "#1e30ff";
        // set the frame size
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // ********************************************* MENU BAR *************************************************** \\
        // create new menu bar
        JMenuBar menuBar = new JMenuBar();;
        // create a "File" dropdown
        JMenu fileMenu = new JMenu("File");
        // add "File" menu to menu bar
        menuBar.add(fileMenu);
        // create a "Puzzle" dropdown
        JMenu puzzleMenu = new JMenu("Puzzle");
        // add "Puzzle" menu to menu bar
        menuBar.add(puzzleMenu);
        // create a "Tools" dropdown
        JMenu toolsMenu = new JMenu("Tools");
        // add "Tools" menu to menu bar
        menuBar.add(toolsMenu);

        // create menu items for the "File" menu drop down
        JMenuItem saveAction = new JMenuItem("Save");
        JMenuItem openAction = new JMenuItem("Open");
        JMenuItem exitAction = new JMenuItem("Exit");

        // create menu items for the "Puzzle" menu drop down
        JMenuItem solveAction = new JMenuItem("Solve");
        JMenuItem submitAction = new JMenuItem("Submit");

        // create menu items for the "Tools" menu drop down
        JMenuItem openInstructions = new JMenuItem("Instructions");
        JMenuItem openTimer = new JMenuItem("Launch Timer");

        // add menu items to the "File" menu
        fileMenu.add(saveAction);
        fileMenu.add(openAction);
        fileMenu.add(exitAction);

        // ****************************************** Puzzle Sub Menu *********************************************** \\
        // make a new sub-menu for selecting a new puzzle
        JMenu puzzleOptionsMenu = new JMenu("New Puzzle");;
        // add sub menu to main "Puzzle" menu
        puzzleMenu.add(puzzleOptionsMenu);

        // easy sub-menu
        JRadioButtonMenuItem easySubMenu = new JRadioButtonMenuItem("Easy");
        puzzleOptionsMenu.add(easySubMenu);

        // medium sub-menu
        JRadioButtonMenuItem mediumSubMenu = new JRadioButtonMenuItem("Medium");
        puzzleOptionsMenu.add(mediumSubMenu);

        // hard sub-menu
        JRadioButtonMenuItem hardSubMenu = new JRadioButtonMenuItem("Hard");
        puzzleOptionsMenu.add(hardSubMenu);

        // expert sub-menu
        JRadioButtonMenuItem expertSubMenu = new JRadioButtonMenuItem("Expert");
        puzzleOptionsMenu.add(expertSubMenu);

        // master sub-menu
        JRadioButtonMenuItem masterSubMenu = new JRadioButtonMenuItem("Master");
        puzzleOptionsMenu.add(masterSubMenu);

        // add radio buttons to group to allow each button to be selected independently
        ButtonGroup directionGroup = new ButtonGroup();

        // deselect the currently selected radio button in the group and select a new one
        directionGroup.add(easySubMenu);
        directionGroup.add(mediumSubMenu);
        directionGroup.add(hardSubMenu);
        directionGroup.add(expertSubMenu);
        directionGroup.add(masterSubMenu);
        // **************************************** End Puzzle Sub Menu ********************************************* \\

        // add menu items to the "Puzzle" menu
        puzzleMenu.add(solveAction);
        puzzleMenu.add(submitAction);

        // add menu items to the "Tools" menu
        toolsMenu.add(openInstructions);
        toolsMenu.add(openTimer);

        // add menu bar to the frame
        frame.setJMenuBar(menuBar);
        // make menu bar visible
        frame.setVisible(true);
        // ******************************************* END MENU BAR ************************************************* \\

        // *********************************************** GRID ***************************************************** \\
        // creates new panel that holds the Sudoku puzzle
        JPanel puzzleBoard = new JPanel();
        // set the layout for the entire frame, puzzle grid to 9x9
        puzzleBoard.setLayout(new GridLayout (ROWS, COLUMNS));
        // outer loop to create rows
        for (int rows = 0; rows < ROWS; rows++) {
            // inner loop to create columns
            for (int columns = 0; columns < COLUMNS; columns++) {
                // make squares in the grid user editable text fields
                inputBoxes[rows][columns] = new JTextField();
                // set text in input boxes to number values from array that contains the numbers
                inputBoxes[rows][columns].setText("" + sudoku[rows][columns]);
                // center the numbers in each corresponding text box
                inputBoxes[rows][columns].setHorizontalAlignment(JTextField.CENTER);
                // set font for puzzle cells
                inputBoxes[rows][columns].setFont(font);
                // instead of having a cell with a "0" in it, make that cell empty, for a more natural Sudoku look
                if (sudoku[rows][columns] == 0) {
                    // replace cells with a 0 in them with an empty string
                    inputBoxes[rows][columns].setText("");
                }
                if (inputBoxes[rows][columns].getText().length() == 0) {
                    inputBoxes[rows][columns].setEditable(true);
                } else {
                    inputBoxes[rows][columns].setEditable(false);
                    // set the uneditable cells font color
                    inputBoxes[rows][columns].setForeground(Color.decode(cellColor));
                }

                // add created rows and columns to the main panel that holds the puzzle grid
                puzzleBoard.add(inputBoxes[rows][columns]);
                // add main puzzle board to main frame
                frame.getContentPane().add(puzzleBoard);

                // ************************************ 3x3 Grid Block Colors *************************************** \\
                // hold light red HTML color in variable
                String lightRed = "#ff8484";
                // hold light blue HTML color in variable
                String lightBlue = "#c1d1ff";
                // set each 3 x 3 block to a different color for separation
                if (rows < 3 && columns < 3) { // top left
                    // set to HTML color light red
                    inputBoxes[rows][columns].setBackground(Color.decode(lightRed));
                } else if (rows < 3 && columns < 6) { // top middle
                    // set to HTML color light blue
                    inputBoxes[rows][columns].setBackground(Color.decode(lightBlue));
                } else if (rows < 3 && columns < 9) { // top right
                    // set to HTML color light red
                    inputBoxes[rows][columns].setBackground(Color.decode(lightRed));
                } else if (rows < 6 && columns < 3) { // middle left
                    // set to HTML color light blue
                    inputBoxes[rows][columns].setBackground(Color.decode(lightBlue));
                } else if (rows < 6 && columns < 6) { // middle
                    // set to HTML color light red
                    inputBoxes[rows][columns].setBackground(Color.decode(lightRed));
                } else if (rows < 6 && columns < 9) { // middle right
                    // set to HTML color light blue
                    inputBoxes[rows][columns].setBackground(Color.decode(lightBlue));
                } else if (rows < 9 && columns < 3) { // bottom left
                    // set to HTML color light red
                    inputBoxes[rows][columns].setBackground(Color.decode(lightRed));
                } else if (rows < 9 && columns < 6) { // bottom middle
                    // set to HTML color light blue
                    inputBoxes[rows][columns].setBackground(Color.decode(lightBlue));
                } else if (rows < 9 && columns < 9) { // bottom right
                    // set to HTML color light red
                    inputBoxes[rows][columns].setBackground(Color.decode(lightRed));
                }
                // ********************************** End 3x3 Grid Block Colors ************************************* \\
            } // end columns loop
        } // end rows loop
        // ********************************************* END GRID *************************************************** \\

        // ***************************************** ACTION LISTENERS *********************************************** \\
        // focus each cell in rows
        for (int rows = 0; rows < ROWS; rows++) {
            // focus each cell in columns
            for (int columns = 0; columns < COLUMNS; columns++) {
                // focus action listener
                inputBoxes[rows][columns].addFocusListener(new FocusListener() {
                    @Override
                    public void focusLost(final FocusEvent pE) {
                        // action code for when cell loses focus, if desired
                    }

                    @Override
                    public void focusGained(final FocusEvent pE) {
                        // when a cell has focus, select the contents of that cell
                        inputBoxes[0][0].selectAll(); inputBoxes[0][1].selectAll(); inputBoxes[0][2].selectAll(); inputBoxes[0][3].selectAll(); inputBoxes[0][4].selectAll(); inputBoxes[0][5].selectAll(); inputBoxes[0][6].selectAll(); inputBoxes[0][7].selectAll(); inputBoxes[0][8].selectAll();
                        inputBoxes[1][0].selectAll(); inputBoxes[1][1].selectAll(); inputBoxes[1][2].selectAll(); inputBoxes[1][3].selectAll(); inputBoxes[1][4].selectAll(); inputBoxes[1][5].selectAll(); inputBoxes[1][6].selectAll(); inputBoxes[1][7].selectAll(); inputBoxes[1][8].selectAll();
                        inputBoxes[2][0].selectAll(); inputBoxes[2][1].selectAll(); inputBoxes[2][2].selectAll(); inputBoxes[2][3].selectAll(); inputBoxes[2][4].selectAll(); inputBoxes[2][5].selectAll(); inputBoxes[2][6].selectAll(); inputBoxes[2][7].selectAll(); inputBoxes[2][8].selectAll();
                        inputBoxes[3][0].selectAll(); inputBoxes[3][1].selectAll(); inputBoxes[3][2].selectAll(); inputBoxes[3][3].selectAll(); inputBoxes[3][4].selectAll(); inputBoxes[3][5].selectAll(); inputBoxes[3][6].selectAll(); inputBoxes[3][7].selectAll(); inputBoxes[3][8].selectAll();
                        inputBoxes[4][0].selectAll(); inputBoxes[4][1].selectAll(); inputBoxes[4][2].selectAll(); inputBoxes[4][3].selectAll(); inputBoxes[4][4].selectAll(); inputBoxes[4][5].selectAll(); inputBoxes[4][6].selectAll(); inputBoxes[4][7].selectAll(); inputBoxes[4][8].selectAll();
                        inputBoxes[5][0].selectAll(); inputBoxes[5][1].selectAll(); inputBoxes[5][2].selectAll(); inputBoxes[5][3].selectAll(); inputBoxes[5][4].selectAll(); inputBoxes[5][5].selectAll(); inputBoxes[5][6].selectAll(); inputBoxes[5][7].selectAll(); inputBoxes[5][8].selectAll();
                        inputBoxes[6][0].selectAll(); inputBoxes[6][1].selectAll(); inputBoxes[6][2].selectAll(); inputBoxes[6][3].selectAll(); inputBoxes[6][4].selectAll(); inputBoxes[6][5].selectAll(); inputBoxes[6][6].selectAll(); inputBoxes[6][7].selectAll(); inputBoxes[6][8].selectAll();
                        inputBoxes[7][0].selectAll(); inputBoxes[7][1].selectAll(); inputBoxes[7][2].selectAll(); inputBoxes[7][3].selectAll(); inputBoxes[7][4].selectAll(); inputBoxes[7][5].selectAll(); inputBoxes[7][6].selectAll(); inputBoxes[7][7].selectAll(); inputBoxes[7][8].selectAll();
                        inputBoxes[8][0].selectAll(); inputBoxes[8][1].selectAll(); inputBoxes[8][2].selectAll(); inputBoxes[8][3].selectAll(); inputBoxes[8][4].selectAll(); inputBoxes[8][5].selectAll(); inputBoxes[8][6].selectAll(); inputBoxes[8][7].selectAll(); inputBoxes[8][8].selectAll();
                    }

                }); // end of focus listener
            } // end columns focus
        } // end rows focus

        // ******************************************** "FILE" MENU ************************************************* \\
        // "Save" action
        saveAction.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                try {
                    // save current puzzle
                    savePuzzle();
                } catch (IOException ex) {
                    // if save process encounters error, print out error
                    System.out.println(ex.toString());
                }
            }
        }); // end save action listener

        //"Open" action
        openAction.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                try {
                    // enter and write into rows
                    for (int rows = 0; rows < ROWS; rows++) {
                        // enter and write into columns
                        for (int columns = 0; columns < COLUMNS; columns++) {
                            // make each square editable, before grid is populated with new numbers
                            inputBoxes[rows][columns].setEditable(true);
                        } // end columns
                    } // end rows
                    // open a previously saved puzzle and populate grid with its contents
                    openPuzzle();
                    addBlankLines();
                    System.out.println("CURRENT PUZZLE:");
                    printToConsole(array);
                } catch (FileNotFoundException ex) {
                    // if saved file is not found, or error opening file, print out error
                    System.out.println(ex.toString());
                }
            }
        }); // end open action

        // "Exit" action
        exitAction.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                // exit the window, user exits voluntarily
                System.exit(0);
            }
        }); // end exit action
        // ****************************************** END "FILE" MENU *********************************************** \\

        // ******************************************* "PUZZLE" MENU ************************************************ \\
        // ******************************************* Difficulties ************************************************* \\
        // easy sub-menu button press
        easySubMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                // add separation in console
                addBlankLines();
                // generate a new random puzzle
                array = Generator.generateSudoku();
                System.out.println("\nNew EASY puzzle generated:");
                // apply easy difficulty to the generated puzzle, removing 48 squares
                sudoku = easyDifficulty();
                // add the rows
                for (int rows = 0; rows < ROWS; rows++) {
                    // add the columns
                    for (int columns = 0; columns < COLUMNS; columns++) {
                        // set input box squares to the numbers found in puzzle array, making 48 of them empty
                        inputBoxes[rows][columns].setText("" + sudoku[rows][columns]);
                        // after making a random square empty, it will contain a "0" designating it as empty
                        if (inputBoxes[rows][columns].getText().contains("0")) {
                            // change that square to being empty, for a better look
                            inputBoxes[rows][columns].setText("");
                            // and make those cells editable
                            inputBoxes[rows][columns].setEditable(true);
                            inputBoxes[rows][columns].setForeground(Color.BLACK);
                            // if cells contain a value on load
                        } else if (inputBoxes[rows][columns].getText() != "") {
                            // make those cells uneditable
                            inputBoxes[rows][columns].setEditable(false);
                            // set the uneditable cells font color
                            inputBoxes[rows][columns].setForeground(Color.decode(cellColor));
                        }
                    } // end adding columns
                } // end adding rows
                // print puzzle solution to console
                printToConsole(array);
            }
        }); // end "easy" sub-menu action

        // medium sub-menu button press
        mediumSubMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                // add separation in console
                addBlankLines();
                // generate a new random puzzle
                array = Generator.generateSudoku();
                System.out.println("\nNew MEDIUM puzzle generated:");
                // apply medium difficulty to the generated puzzle, removing 51 squares
                sudoku = mediumDifficulty();
                // add the rows
                for (int rows = 0; rows < ROWS; rows++) {
                    // add the columns
                    for (int columns = 0; columns < COLUMNS; columns++) {
                        // set input box squares to the numbers found in puzzle array, making 51 of them empty
                        inputBoxes[rows][columns].setText("" + sudoku[rows][columns]);
                        // after making a random square empty, it will contain a "0" designating it as empty
                        if (inputBoxes[rows][columns].getText().contains("0")) {
                            // change that square to being empty, for a better look
                            inputBoxes[rows][columns].setText("");
                            // and make those cells editable
                            inputBoxes[rows][columns].setEditable(true);
                            inputBoxes[rows][columns].setForeground(Color.BLACK);
                            // if cells contain a value on load
                        } else if (inputBoxes[rows][columns].getText() != "") {
                            // make those cells uneditable
                            inputBoxes[rows][columns].setEditable(false);
                            // set the uneditable cells font color
                            inputBoxes[rows][columns].setForeground(Color.decode(cellColor));
                        }
                    } // end adding columns
                } // end adding rows
                // print puzzle solution to console
                printToConsole(array);
            }
        }); // end "medium" sub-menu action

        // hard sub-menu button press
        hardSubMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                // add separation in console
                addBlankLines();
                // generate a new random puzzle
                array = Generator.generateSudoku();
                System.out.println("\nNew HARD puzzle generated:");
                // apply hard difficulty to the generated puzzle, removing 54 squares
                sudoku = hardDifficulty();
                // add the rows
                for (int rows = 0; rows < ROWS; rows++) {
                    // add the columns
                    for (int columns = 0; columns < COLUMNS; columns++) {
                        // set input box squares to the numbers found in puzzle array, making 54 of them empty
                        inputBoxes[rows][columns].setText("" + sudoku[rows][columns]);
                        // after making a random square empty, it will contain a "0" designating it as empty
                        if (inputBoxes[rows][columns].getText().contains("0")) {
                            // change that square to being empty, for a better look
                            inputBoxes[rows][columns].setText("");
                            // and make those cells editable
                            inputBoxes[rows][columns].setEditable(true);
                            inputBoxes[rows][columns].setForeground(Color.BLACK);
                            // if cells contain a value on load
                        } else if (inputBoxes[rows][columns].getText() != "") {
                            // make those cells uneditable
                            inputBoxes[rows][columns].setEditable(false);
                            // set the uneditable cells font color
                            inputBoxes[rows][columns].setForeground(Color.decode(cellColor));
                        }
                    } // end adding columns
                } // end adding rows
                // print puzzle solution to console
                printToConsole(array);
            }
        }); // end "hard" sub-menu action

        // expert sub-menu button press
        expertSubMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                // add separation in console
                addBlankLines();
                // generate a new random puzzle
                array = Generator.generateSudoku();
                System.out.println("\nNew EXPERT puzzle generated:");
                // apply expert difficulty to the generated puzzle, removing 54 squares
                sudoku = expertDifficulty();
                // add the rows
                for (int rows = 0; rows < ROWS; rows++) {
                    // add the columns
                    for (int columns = 0; columns < COLUMNS; columns++) {
                        // set input box squares to the numbers found in puzzle array, making 54 of them empty
                        inputBoxes[rows][columns].setText("" + sudoku[rows][columns]);
                        // after making a random square empty, it will contain a "0" designating it as empty
                        if (inputBoxes[rows][columns].getText().contains("0")) {
                            // change that square to being empty, for a better look
                            inputBoxes[rows][columns].setText("");
                            // and make those cells editable
                            inputBoxes[rows][columns].setEditable(true);
                            inputBoxes[rows][columns].setForeground(Color.BLACK);
                            // if cells contain a value on load
                        } else if (inputBoxes[rows][columns].getText() != "") {
                            // make those cells uneditable
                            inputBoxes[rows][columns].setEditable(false);
                            // set the uneditable cells font color
                            inputBoxes[rows][columns].setForeground(Color.decode(cellColor));
                        }
                    } // end adding columns
                } // end adding rows
                // print puzzle solution to console
                printToConsole(array);
            }
        }); // end "expert" sub-menu action

        // master sub-menu button press
        masterSubMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                // add separation in console
                addBlankLines();
                // generate a new random puzzle
                array = Generator.generateSudoku();
                System.out.println("\nNew MASTER puzzle generated:");
                // apply master difficulty to the generated puzzle, removing 54 squares
                sudoku = masterDifficulty();
                // add the rows
                for (int rows = 0; rows < ROWS; rows++) {
                    // add the columns
                    for (int columns = 0; columns < COLUMNS; columns++) {
                        // set input box squares to the numbers found in puzzle array, making 54 of them empty
                        inputBoxes[rows][columns].setText("" + sudoku[rows][columns]);
                        // after making a random square empty, it will contain a "0" designating it as empty
                        if (inputBoxes[rows][columns].getText().contains("0")) {
                            // change that square to being empty, for a better look
                            inputBoxes[rows][columns].setText("");
                            // and make those cells editable
                            inputBoxes[rows][columns].setEditable(true);
                            inputBoxes[rows][columns].setForeground(Color.BLACK);
                            // if cells contain a value on load
                        } else if (inputBoxes[rows][columns].getText() != "") {
                            // make those cells uneditable
                            inputBoxes[rows][columns].setEditable(false);
                            // set the uneditable cells font color
                            inputBoxes[rows][columns].setForeground(Color.decode(cellColor));
                        }
                    } // end adding columns
                } // end adding rows
                // print puzzle solution to console
                printToConsole(array);
            }
        }); // end "master" sub-menu action
        // ******************************************* End Difficulties ********************************************* \\

        // "Solve" action
        solveAction.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                SudokuSolver solver = new SudokuSolver(array);
                solver.getSolution();

                // write into rows
                for (int rows = 0; rows < ROWS; rows++) {
                    // write into columns
                    for (int columns = 0; columns < COLUMNS; columns++) {
                        // populate grid squares with the correct solution from SudokuSolver
                        inputBoxes[rows][columns].setText("" + array[rows][columns]);
                        // if a cell is empty and will be filed with a value when the puzzle is solved
                        if (inputBoxes[rows][columns].getText() == "") {
                            /* make sure that filled value has a black font color, so it is different than the cells
                             that are pre-filled with values (blue font-color cells) */
                            inputBoxes[rows][columns].setForeground(Color.BLACK);
                        }
                    } // end writing columns
                } // end writing rows
            }
        }); // end solve action

        // "Submit" action
        submitAction.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                // pass the submit() method into an array that holds the user submission
                int[][] userSubmission = submitPuzzle();
                // write the user submission array to an external file
                writeToFile(userSubmission);

                // if user submission is tested for the correct solution and passes
                if (testSudoku()) {
                    // display a congratulatory message to the user the puzzle was solved correctly
                    JOptionPane.showMessageDialog(null, "CONGRATULATIONS!!"
                            + "\nYou have correctly solved the Sudoku puzzle!");
                } else {
                    // otherwise display a message stating the user did not correctly solve the puzzle
                    JOptionPane.showMessageDialog(null, "Sorry, but you did not correctly solve the puzzle"
                            + "\nKeep on trying, you'll get there...");
                }
            }
        }); // end submit action
        // ***************************************** END "PUZZLE" MENU ********************************************** \\

        // ******************************************** "TOOLS" MENU ************************************************ \\
        // "Launch Instructions" action
        openInstructions.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                // launch a dialog box with a set of simple game instructions and rules
                JOptionPane.showMessageDialog(null, "Follow this set of simple instructions to solve the Sudoku puzzle:"
                        + "\n1) Fill each empty square with a number from 1 to 9."
                        + "\n2) Each 3x3 colored block must sum to 45, and not repeat numbers"
                        + "\n3) Each full row must sum to 45, and not repeat numbers"
                        + "\n4) Each full column must sum to 45, and not repeat numbers"
                        + "\n------------------------------------------------------"
                        + "\n- Solve puzzle from the \"Puzzle\" menu if you're in trouble."
                        + "\n- Submit the puzzle when you believe you've completed it."
                        + "\n- Start a new puzzle with easy, medium, hard, or expert difficulties."
                        + "\n- Launch a timer form the \"Tools\" menu to time yourself."
                        + "\n- Save your puzzle for later, and open it when you're ready.");
            }
        }); // end launch instructions action

        // "Launch Clock Timer" action
        openTimer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                // make new instance of SudokuTimer
                SudokuTimer runTimer = new SudokuTimer();
                // launch the clock timer
                runTimer.launchTimer();
            }
        }); // end launch clock timer action
        // ****************************************** END "TOOLS" MENU ********************************************** \\
        // *************************************** END ACTION LISTENERS ********************************************* \\

        // add Sudoku puzzle board to the main frame
        frame.getContentPane().add(puzzleBoard);
        // make the frame visible
        frame.setVisible(true);
    } // end SudokuGUI constructor

    /**
     * submitPuzzle is used for user submission of a completed Sudoku puzzle for checking.
     * @return will return submitted puzzle (array) for checking.
     */
    public static int[][] submitPuzzle() {
        // array that will contain the submitted puzzle
        int[][] result = new int[ROWS][COLUMNS];
        // write rows to array
        for (int rows = 0; rows < ROWS; rows++) {
            // write columns to array
            for (int columns = 0; columns < COLUMNS; columns++) {
                // write content from grid text fields to an array that will parse strings to integers
                try {
                    // add each row and column to an external file that will be used for testing
                    result[rows][columns] = Integer.parseInt(inputBoxes[rows][columns].getText());
                    // write that array to an external output file
                    writeToFile(result);
                } catch (Exception ex) {
                    // if there is an error with submitting, print the error
                    System.out.println(ex.toString());
                    // an array index will contain "-1" in the output file, if square is without an integer
                    result[rows][columns] = -1;
                }
            } // end columns
        } // end rows
        return result;
    } // end submit

    /**
     * testSudoku returns the SudokuChecker.sudokuTest method which checks the Sudoku puzzle for correctness.
     * @return will return the method that checks the Sudoku puzzle for failing or passing.
     */
    public boolean testSudoku() {
        /* returns the checkPuzzle method from SudokuChecks, which checks to make sure each individual row, column, and
         3x3 block does not contain duplicate numbers, and that they all sum up to 45, respectively */
        return SudokuChecks.checkPuzzle(submitPuzzle());
    } // end testSudoku

    /**
     * readFromFile reads in an external file and uses it to populate a Sudoku grid to make a puzzle game to play.
     * @return will return the puzzle that is to be generated for the user to attempt to solve.
     */
    public static int[][] readFromFile() {
        // array to hold the puzzle that is read in
        int[][] puzzleArray = new int[ROWS][COLUMNS];
        // row counter for current row
        int rows = 0;
        try {
            // use buffered reader object to read in external input file
            BufferedReader inputFile = new BufferedReader(new FileReader("generated.txt"));
            // variable to hold each line in the external file
            String lineInFile;
            // loop as long as the input file has a line to read
            while ((lineInFile = inputFile.readLine())!= null) {
                String[] numValues = lineInFile.split(" ");
                // column parsing
                for (int columns = 0; columns < COLUMNS; columns++) {
                    // parse puzzle numbers from external file into integers and store the parsed values in an array
                    puzzleArray[rows][columns] = Integer.parseInt(numValues[columns]);
                } // end column parsing
                rows++;
            }
            // close file
            inputFile.close();
        } catch (IOException ex) {
            // if there is an error in reading the file, print error
            System.out.println(ex.toString());
        }
        // add separation in console
        addBlankLines();
        System.out.println("CURRENT PUZZLE:");
        // print the puzzle solution to console
        printToConsole(puzzleArray);
        // return parsed array that is read in
        return puzzleArray;
    } // end readFromFile

    /**
     * writeToFile writes the completed puzzle to an external file.
     * @param array this is the completed puzzle that will be written to an external file.
     */
    public static void writeToFile(int[][] array) {
        try {
            // create the output file that will contain a user submitted puzzle
            PrintWriter addToFile = new PrintWriter(new FileWriter("output.txt"));
            // write rows
            for (int rows = 0; rows < ROWS; rows++) {
                // hold the string of numbers that is written to the file
                String writeNumbers = "";
                // write columns
                for (int columns = 0; columns < COLUMNS; columns++) {
                    // write numbers to the file, with a "space" as a separator
                    writeNumbers += array[rows][columns] + " ";
                    // each empty square in the grid is referenced by a -1 when written to this output.txt file
                    // if there is a -1 being used to identify an empty cell (square) in the output file
                    if (array[rows][columns] == -1) {
                        // change -1 to a 0 to designate the empty cell (square), so the output file looks better
                        array[rows][columns] = 0;
                    }
                } // end write columns
                // add the string of numbers to the output file
                addToFile.println(writeNumbers);
            } // end write rows
            // close writing to file
            addToFile.close();
        } catch (IOException ex) {
            // if error occurs during the writing to file process, print error
            System.out.println(ex.toString());
        }
    } // end writeToFile

    /**
     * defaultDifficulty generates which squares in the Sudoku puzzle will be empty, defining the difficulty of the
     * current puzzle. The initial default difficulty for the start of any new puzzle will be easy, unless otherwise
     * specified by a user selection of a different difficulty from the "Puzzle" menu.
     * @return this will return the randomly generated squares to be removed and become empty squares.
     */
    public static int[][] defaultDifficulty() {
        // the Sudoku puzzle input box squares
        int[][] sudokuSquares = new int[ROWS][COLUMNS];
        // rows
        for (int rows = 0; rows < ROWS; rows++) {
            // columns
            for (int columns = 0; columns < COLUMNS; columns++) {
                // add contents of the array that holds the Sudoku puzzle to sudokuSquares
                sudokuSquares[rows][columns] = array[rows][columns];
            } // end columns
        } // end rows
        // amount of squares that will be empty, determining the difficulty of the current puzzle
        int numSquaresRemoved = 48;
        // while the number of squares removed is more than 0 squares
        while (numSquaresRemoved > 0) {
            int x = (int)(Math.random() * 9);
            int y = (int)(Math.random() * 9);
            // if an empty square does not have a "0" contained in it
            if (sudokuSquares[x][y] != 0) {
                // put a "0" in that empty square
                sudokuSquares[x][y] = 0;
                // iterate down
                numSquaresRemoved--;
            }
        }
        // return the array that now has the random empty squares
        return sudokuSquares;
    } // end squareGenerator

    /**
     * easyDifficulty will remove 48 values from the puzzle board, designating a difficulty of "easy."
     * @return will return the randomly generated squares to be removed and become empty squares.
     */
    public static int[][] easyDifficulty() {
        // the Sudoku puzzle input box squares
        int[][] sudokuSquares = new int[ROWS][COLUMNS];
        // rows
        for (int rows = 0; rows < ROWS; rows++) {
            // columns
            for (int columns = 0; columns < COLUMNS; columns++) {
                // add contents of the array that holds the Sudoku puzzle to sudokuSquares
                sudokuSquares[rows][columns] = array[rows][columns];
            } // end columns
        } // end rows
        // amount of squares that will be empty, determining the difficulty of the current puzzle
        int numSquaresRemoved = 48;
        // while the number of squares removed is more than 0 squares
        while (numSquaresRemoved > 0) {
            int x = (int)(Math.random() * 9);
            int y = (int)(Math.random() * 9);
            // if an empty square does not have a "0" contained in it
            if (sudokuSquares[x][y] != 0) {
                // put a "0" in that empty square
                sudokuSquares[x][y] = 0;
                // iterate down
                numSquaresRemoved--;
            }
        }
        // return the array that now has the random empty squares
        return sudokuSquares;
    } // end easyDifficulty

    /**
     * mediumDifficulty will remove 51 values from the puzzle board, designating a difficulty of "medium."
     * @return will return the randomly generated squares to be removed and become empty squares.
     */
    public static int[][] mediumDifficulty() {
        // the Sudoku puzzle input box squares
        int[][] sudokuSquares = new int[ROWS][COLUMNS];
        // rows
        for (int rows = 0; rows < ROWS; rows++) {
            // columns
            for (int columns = 0; columns < COLUMNS; columns++) {
                // add contents of the array that holds the Sudoku puzzle to sudokuSquares
                sudokuSquares[rows][columns] = array[rows][columns];
            } // end columns
        } // end rows
        // amount of squares that will be empty, determining the difficulty of the current puzzle
        int numSquaresRemoved = 51;
        // while the number of squares removed is more than 0 squares
        while (numSquaresRemoved > 0) {
            int x = (int)(Math.random() * 9);
            int y = (int)(Math.random() * 9);
            // if an empty square does not have a "0" contained in it
            if (sudokuSquares[x][y] != 0) {
                // put a "0" in that empty square
                sudokuSquares[x][y] = 0;
                // iterate down
                numSquaresRemoved--;
            }
        }
        // return the array that now has the random empty squares
        return sudokuSquares;
    } // end mediumDifficulty

    /**
     * mediumDifficulty will remove 54 values from the puzzle board, designating a difficulty of "hard."
     * @return will return the randomly generated squares to be removed and become empty squares.
     */
    public static int[][] hardDifficulty() {
        // the Sudoku puzzle input box squares
        int[][] sudokuSquares = new int[ROWS][COLUMNS];
        // rows
        for (int rows = 0; rows < ROWS; rows++) {
            // columns
            for (int columns = 0; columns < COLUMNS; columns++) {
                // add contents of the array that holds the Sudoku puzzle to sudokuSquares
                sudokuSquares[rows][columns] = array[rows][columns];
            } // end columns
        } // end rows
        // amount of squares that will be empty, determining the difficulty of the current puzzle
        int numSquaresRemoved = 54;
        // while the number of squares removed is more than 0 squares
        while (numSquaresRemoved > 0) {
            int x = (int)(Math.random() * 9);
            int y = (int)(Math.random() * 9);
            // if an empty square does not have a "0" contained in it
            if (sudokuSquares[x][y] != 0) {
                // put a "0" in that empty square
                sudokuSquares[x][y] = 0;
                // iterate down
                numSquaresRemoved--;
            }
        }
        // return the array that now has the random empty squares
        return sudokuSquares;
    } // end hardDifficulty

    /**
     * expertDifficulty will remove 57 values from the puzzle board, designating a difficulty of "expert."
     * @return will return the randomly generated squares to be removed and become empty squares.
     */
    public static int[][] expertDifficulty() {
        // the Sudoku puzzle input box squares
        int[][] sudokuSquares = new int[ROWS][COLUMNS];
        // rows
        for (int rows = 0; rows < ROWS; rows++) {
            // columns
            for (int columns = 0; columns < COLUMNS; columns++) {
                // add contents of the array that holds the Sudoku puzzle to sudokuSquares
                sudokuSquares[rows][columns] = array[rows][columns];
            } // end columns
        } // end rows
        // amount of squares that will be empty, determining the difficulty of the current puzzle
        int numSquaresRemoved = 57;
        // while the number of squares removed is more than 0 squares
        while (numSquaresRemoved > 0) {
            int x = (int)(Math.random() * 9);
            int y = (int)(Math.random() * 9);
            // if an empty square does not have a "0" contained in it
            if (sudokuSquares[x][y] != 0) {
                // put a "0" in that empty square
                sudokuSquares[x][y] = 0;
                // iterate down
                numSquaresRemoved--;
            }
        }
        // return the array that now has the random empty squares
        return sudokuSquares;
    } // end expertDifficulty

    /**
     * masterDifficulty will remove 65 values from the puzzle board, designating a difficulty of "master."
     * @return will return the randomly generated squares to be removed and become empty squares.
     */
    public static int[][] masterDifficulty() {
        // the Sudoku puzzle input box squares
        int[][] sudokuSquares = new int[ROWS][COLUMNS];
        // rows
        for (int rows = 0; rows < ROWS; rows++) {
            // columns
            for (int columns = 0; columns < COLUMNS; columns++) {
                // add contents of the array that holds the Sudoku puzzle to sudokuSquares
                sudokuSquares[rows][columns] = array[rows][columns];
            } // end columns
        } // end rows
        // amount of squares that will be empty, determining the difficulty of the current puzzle
        int numSquaresRemoved = 64;
        // while the number of squares removed is more than 0 squares
        while (numSquaresRemoved > 0) {
            int x = (int)(Math.random() * 9);
            int y = (int)(Math.random() * 9);
            // if an empty square does not have a "0" contained in it
            if (sudokuSquares[x][y] != 0) {
                // put a "0" in that empty square
                sudokuSquares[x][y] = 0;
                // iterate down
                numSquaresRemoved--;
            }
        }
        // return the array that now has the random empty squares
        return sudokuSquares;
    } // end masterDifficulty

    /**
     * savePuzzle allows a user to save the current state of the Sudoku puzzle to an external file of which the user
     * will choose the name of, and choose the location directory of where to save the file on their machine
     * @throws IOException error in saving the file
     */
    public void savePuzzle() throws IOException {
        // when dialog first opens, the default directory is the current Java project directory
        JFileChooser fileChooser = new JFileChooser("../Sudoku");
        int dialogChoice = fileChooser.showDialog(this, "Save");

        // tell BufferedWriter which file it will be writing
        BufferedWriter saveToFile = null;
        // declare the user save file
        File userSaveFile;

        // tell FileOutputStream which stream will be writing the output of the puzzle solution to a file
        FileOutputStream stream = null;
        // tell PrintStream where it will be writing the puzzle solution to
        PrintStream solutionToFile = null;
        // the text file that contains the correct solution to the current puzzle
        File solutionFile;

        // ff the user has canceled, no need to continue the save process
        if (dialogChoice != JFileChooser.APPROVE_OPTION) {
            // close dialog window
            return;
        }

        // user can choose the location and directory where the file is saved, and the name of the file as well
        userSaveFile = fileChooser.getSelectedFile();
        // use BufferedWriter to write current puzzle contents to the user save file
        saveToFile = new BufferedWriter(new FileWriter(userSaveFile));

        /* save the solution text file with the name of the user save file and a "-solution" extension, so that user
        can save multiple puzzles with different names, and the solution will always be written to a file that contains
        the name of the user's save file with the extension appended to it */
        solutionFile = new File(userSaveFile + "-solution");
        // the output stream will be the puzzle solution file
        stream = new FileOutputStream(solutionFile);
        // write the stream
        solutionToFile = new PrintStream(stream);
        // set the system output to go to the current text file
        System.setOut(solutionToFile);

        // write rows
        for (int rows = 0; rows < ROWS; rows++) {
            // write columns
            for (int columns = 0; columns < COLUMNS; columns++) {
                // add text from current square into its corresponding array element in the save file
                saveToFile.write(inputBoxes[rows][columns].getText());
                // if grid square is empty, it will be designated by a "-" when written to the save file
                if (inputBoxes[rows][columns].getText().length() == 0) {
                    saveToFile.write("-");
                }
                // remove the last column, which is an empty space, in the save file
                if (columns < COLUMNS - 1) {
                    // separate values in the user save file by commas
                    saveToFile.write(",");
                }

                // hold the correct puzzle solution in solutionArray
                int[][] solutionArray;
                // set the array that prints the puzzle to the console to the "anArray" variable
                solutionArray = array;
                // print stored puzzle solution to an external text file, line by line, separating numbers with spaces
                System.out.print(solutionArray[rows][columns] + " ");
            } // end writing columns
            saveToFile.write("\n");
            // separate lines in the text file that contains the puzzle solution
            System.out.println();
        } // end writing rows
        saveToFile.close();
    } // end savePuzzle

    /**
     * openPuzzle opens a Sudoku file that a user has previously saved to a directory on their machine
     * @throws FileNotFoundException the file to be opened is not found
     * @throws IllegalArgumentException the file contains errors preventing it from being loaded properly
     */
    public void openPuzzle() throws FileNotFoundException {
        // when dialog first opens, the default directory is the current Java project directory
        JFileChooser fileChooser = new JFileChooser("../Sudoku");
        int returnVal = fileChooser.showDialog(this, "Open");
        String cellColor = "#1e30ff";

        // set the scanner that reads an external save file
        Scanner readFile;
        // set the scanner that reads an external puzzle solution file
        Scanner readSolutionFile;
        // the user's save file that was previously saved
        File userSaveFile;
        // hold each line of the user save file in an array
        String[] splitLine;
        // hold each line of the puzzle solution file in an array
        String[] splitSolution;

        // If the user has canceled, no need to continue with open process
        if (returnVal != JFileChooser.APPROVE_OPTION) {
            // close dialog window
            return;
        }

        // user's save file is the file that will be imported into grid
        userSaveFile = fileChooser.getSelectedFile();
        // read the the user save file
        readFile = new Scanner(userSaveFile);
        /* read the puzzle solution file. The puzzle solution file that contains the right puzzle has a "-solution"
           extension, to differentiate between the regular save file that saves the current state of a puzzle, versus
           the save file that contains the correct puzzle solution */
        readSolutionFile = new Scanner(new File(userSaveFile + "-solution"));

        // hold which row we are currently in
        int rows = 0;

        // keep reading user's save file and the puzzle solution file while both files have a line to read
        while (readFile.hasNextLine() && readSolutionFile.hasNextLine()) {
            // split save file contents at "," and store split values into array
            splitLine = readFile.nextLine().split(",");
            // split puzzle solution file contents at " " and store split values into an array
            splitSolution = readSolutionFile.nextLine().split(" ");

            // verify the length of the row
            if (splitLine.length != ROWS) {
                // if length of the row is not equal to 9, print an error
                throw new IllegalArgumentException("The row length is incorrect, indicating an issue with save file");
            }

            // read the file by columns
            for (int columns = 0; columns < COLUMNS; columns++) {
                // update the squares with new numbers from the user's selected save file
                inputBoxes[rows][columns].setText(splitLine[columns]);
                // hold the correct puzzle solution in solutionArray
                int[][] solutionArray = array;
                // add the contents from the puzzle solution file into an array as parsed integer values
                solutionArray[rows][columns] = Integer.parseInt(splitSolution[columns]);
                // when save file is loaded into grid, empty cells will be populated with "-" as a placeholder
                if (inputBoxes[rows][columns].getText().contains("-")) {
                    // if that is the case, change those cells to display empty, for a more natural Sudoku grid look
                    inputBoxes[rows][columns].setText("");
                }
                if (inputBoxes[rows][columns].getText().length() == 0) {
                    // change that square to being empty, for a better look
                    inputBoxes[rows][columns].setText("");
                    // and make those cells editable
                    inputBoxes[rows][columns].setEditable(true);
                    inputBoxes[rows][columns].setForeground(Color.BLACK);
                    // if cells contain a value on load
                } else if (inputBoxes[rows][columns].getText() != "") {
                    // make those cells uneditable
                    inputBoxes[rows][columns].setEditable(false);
                    // set the uneditable cells font color
                    inputBoxes[rows][columns].setForeground(Color.decode(cellColor));
                }
            } // end columns
            // move to the next row and continue loading
            rows++;
        }
    } // end openPuzzle

    /**
     * printToConsole prints the puzzle that is the correct solution to the console.
     * @param array the array that contains the correct puzzle that is printed to the console
     */
    public static void printToConsole(int[][] array) {
        // print rows to console
        for (int rows = 0; rows < ROWS; rows++) {
            // print columns to console
            for (int columns = 0; columns < COLUMNS; columns++) {
                // print out the 2d array that is the puzzle solution, to the console, with spaces between each number
                System.out.print(array[rows][columns] + " ");
            } // end printing columns
            System.out.println();
        } // end printing rows
    } // end printToConsole

    /**
     * addBlankLines is a simple method which simply adds blank lines to the console, to add separation between console
     * output text. For example, when a random puzzle is initially generated and the Sudoku board is loaded, the puzzle
     * that is the correct solution is printed to the console. If another puzzle is opened from an external file, that
     * puzzle solution is now printed to the console, and the old initial puzzle output is moved out of view. The new
     * console output is also written to memory, so that the SudokuSolver can solve the new puzzle as well.
     */
    public static void addBlankLines() {
        /* set the number of blank lines to print to the console whenever this method is called. The blank lines make it
         easier to decipher which is the current puzzle (solution) that is currently in memory. This method is just a
         cosmetic preference, and nothing more. I thought it was more elegant to write a method call for what I needed,
         rather than repeating this very code multiple times, wherever I needed done what this method does */
        for (int blankLines = 0; blankLines < 10; blankLines++) {
            // print empty strings on all the blank lines generated with this method
            System.out.println("");
        }
    } // end addBlankLines

    /**
     * main runs all the "Sudoku Puzzle" logic
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // generate a new random puzzle and write its contents to an external file named "generated.txt"
        Generator.writeToFile(array);
        // populate puzzle grid with contents from the created "generated.txt" file
        array = readFromFile();
        /* load puzzle with default difficulty on initial Sudoku board load, removing 48 random values from squares. The
         user can choose a different difficulty after the initial puzzle has loaded */
        sudoku = defaultDifficulty();

        // run the Sudoku puzzle
        new SudokuGUI();
    } // end main

} // end class SudokuGUI
