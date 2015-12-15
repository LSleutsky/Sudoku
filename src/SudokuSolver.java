/**
 * PROJECT: Sudoku Puzzle
 *
 * CLASS: SudokuSolver
 *
 * DESCRIPTION: This class will check each row, column, and 3x3 block for existing values. If there is a number from
 *              1 to 9 found in each cell, skip that cell and continue checking for empty cells. If an empty cell is
 *              found, fill that cell with a number from 1 to 9, so long as that number is not already in the row or in
 *              the column. If the ends of each row and column are reached, and there are no duplicates, return an array
 *              of usable puzzle values, which will be used to populate grid, solving an empty puzzle.
 *
 * DATE: December 2015
 *
 * AUTHOR: Lush Sleutsky
 *
 * IDE: IntelliJ IDEA 15.0.1
 */

public class SudokuSolver {

    // set unchangeable number of rows to 9
    private static final int ROWS = 9;
    // set unchangeable number of columns to 9
    private static final int COLUMNS = 9;
    // the multidimensional array that will be the solution
    private int[][] puzzleSolution;
    // the 9x9 solution multidimensional array containing the correct puzzle values
    public static int[][] solution = new int[ROWS][COLUMNS];

    /**
     * SudokuSolver constructor takes your incomplete puzzle and calls the solver method on it in order to get a
     * completed puzzle. The completed puzzle is stored in a solution multidimensional array.
     * @param puzzle the puzzle you want to solve.
     */
    public SudokuSolver(int[][] puzzle) {
        // store puzzle solution into "puzzle" parameter
        puzzleSolution = puzzle;
        // solve the puzzle
        solvePuzzle(puzzleSolution, 0, 0);
    } // end SudokuSolver

    /**
     * next takes the current position in the puzzle array and moves it forward.
     * next calls upon the solvePuzzle method to continue checking values of the next array location. This is done so
     * if there is a non empty value in the array, we can skip to an empty one.
     * @param puzzle the array that is being moved forward
     * @param row the row position in the array
     * @param column the column position in the array
     */
    public void next(int[][] puzzle, int row, int column) {
        // if we are still within the puzzle columns
        if (column < 8) {
            // solve puzzle by filling empty grid squares in columns
            solvePuzzle(puzzle, row, column + 1);
        } else {
            // otherwise, solve the puzzle by filling empty grid squares in rows
            solvePuzzle(puzzle, row + 1, 0);
        }
    } // end next

    /**
     * solvePuzzle loops through an incomplete sudoku puzzle in order to solve it.
     * It skips already entered 1 to 9 values using the next function, which then calls the updated function with
     * new row and column positioning. If a value doesn't already exist in its respective row, column, or block, solve
     * adds the value to the array and updates the positioning to check for a new value. At the end of the array, the
     * solution is sent to the "solution" array.
     * @param puzzle the incomplete sudoku puzzle being solved.
     * @param row the current row position of the array.
     * @param col the current column position of the array.
     */
    public void solvePuzzle(int[][] puzzle, int row, int col) {
        // if the row value is above 8, then every empty value should be solved
        if (row > 8) {
            System.out.println("\nThe puzzle has been automatically solved!\nSOLUTION APPLIED:");
            // writing the rows
            for (int r = 0; r < ROWS; r++) {
                // writing the columns
                for (int c = 0; c < COLUMNS; c++) {
                    // array to hold the solution
                    int[][] solutionArray;
                    // places the solved array into the solution array
                    solutionArray = puzzle;
                    // print out the solution puzzle to console
                    System.out.print(solutionArray[r][c] + " ");
                } // end writing columns
                // this empty println is needed to print the puzzle solution line by line in the console
                System.out.println();
            } // end writing rows
        } else {
            // as long as the value in the array is not zero, skip to next position
            if (puzzle[row][col] != 0) {
                // move to next position of row and column
                next(puzzle, row, col);
            } else {
                /* checks for duplicate values of 1 to 9 in rows, columns and 3x3 blocks, and if there are none, places
                 the number in an empty location in the array  */
                for (int index = 1; index <= 9; index++) {
                    /* checks that there are no duplicated numbers in the row, column or block, so that we can enter
                     the correct number into the right position in the array */
                    if (SudokuChecks.findRowDuplicates(puzzle, row, index) &&
                        SudokuChecks.findColumnDuplicates(puzzle, col, index) &&
                        SudokuChecks.findBlockDuplicates(puzzle, row, col, index)) {
                            // set number at current position to index
                            puzzle[row][col] = index;
                            // move to the next position of row and column
                            next(puzzle, row, col);
                    }
                }
                //puzzle[row][col] = 0;
            }
        }
    } // end solvePuzzle

    /**
     * getSolution is a getter method that will get the values of a solved sudoku.
     * @return will return the correct solution to a solved puzzle
     */

    public static int[][] getSolution() {
        // return the solution
        return SudokuSolver.solution;
    } // end getSolution

} // end class SudokuSolver