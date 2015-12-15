/**
 * PROJECT: Sudoku Puzzle
 *
 * CLASS: SudokuChecks
 *
 * DESCRIPTION: This class checks to make sure that a Sudoku puzzle was solved correctly. All 9 of the 3x3 colored blocks
 *              will be tested and checked that they add up to 45, and do not contain an repeating numbers. All of the
 *              rows and columns will also be tested and checked, respectively, to make sure that all rows add up to 45
 *              across, and that all columns add up to 45 up & down. Rows and columns cannot contain repeating numbers.
 *
 * DATE: December 2015
 *
 * AUTHOR: Lush Sleutsky
 *
 * IDE: IntelliJ IDEA 15.0.1
 */

import java.util.Arrays;

public class SudokuChecks {

    // set unchangeable number of rows to 9
    private static final int ROWS = 9;
    // set unchangeable number of columns to 9
    private static final int COLUMNS = 9;

    /**
     * checkAll loops through a multidimensional array. If the array has no duplicate values and if each row and column
     * of the array is summed to 45, it returns true. Used as a final check after puzzle submission.
     * @param sudoku the multidimensional array that will be checked for passing or failing.
     * @return will return a boolean indicating success or failure of check.
     */
    public static  boolean checkPuzzle(int[][] sudoku) {
        // if these boolean tests all return true
        if (checkRows(sudoku) && checkColumns(sudoku) && checkBlock(sudoku)) {
            // the puzzle has passed
            return true;
        }
        // the puzzle has failed
        return false;
    } // end checkPuzzle

    /**
     * checkRows loops through a multidimensional array. If the array has no duplicate values and if each row of the
     * array is summed to 45, it returns true.
     * @param sudoku the multidimensional array that is being checked for duplicate numbers and correct sum.
     * @return will return a boolean indicating success or failure of the row check.
     */
    public static boolean checkRows(int[][] sudoku) {
        // boolean indicating that the row has passed
        boolean passed = true;
        System.out.println("\nCHECKING ALL 9 OF THE ROWS:");

        // check rows
        for (int rows = 0; rows < sudoku.length; rows++) {
            // put current puzzle row into an array
            int[] puzzleRow = new int[ROWS];
            // check columns
            for (int columns = 0; columns < sudoku.length; columns++) {
                puzzleRow[columns] = sudoku[rows][columns];
                // print is used to make sure everything is looping correctly
                System.out.print(puzzleRow[columns] + " ");
            } // end columns check

            // if rows sum up to 45 and there are no duplicate numbers in them
            if (isSumCorrect(puzzleRow) && hasNoDuplicates(puzzleRow)) {
                // write to the console the values in each row, and that it passed
                System.out.println("(Row " + (rows + 1) + " has PASSED)");
            } else {
                // otherwise write to the console that the row has failed
                System.out.println("(Row " + (rows + 1) + " has FAILED)");
                // set boolean to false, indicating failure
                passed = false;
            }

        } // end rows check
        // return rows pass-fail boolean check
        return passed;
    } // end checkRows

    /**
     * checkColumns loops through a multidimensional array. If the array has no duplicate values and if each column of the
     * array is summed to 45, it returns true.
     * @param sudoku the multidimensional array that is being checked for duplicate numbers and correct sum.
     * @return will return a boolean indicating success or failure of the column check.
     */
    public static boolean checkColumns(int[][] sudoku) {
        // boolean indicating that the column has passed
        boolean passed = true;
        System.out.println("\nCHECKING ALL 9 OF THE COLUMNS:");

        // check rows
        for (int rows = 0; rows < sudoku.length; rows++) {
            // put current puzzle column into an array
            int[] puzzleColumn = new int[COLUMNS];
            // check columns
            for (int columns = 0; columns < sudoku.length; columns++) {
                puzzleColumn[columns] = sudoku[columns][rows];
                // print is used to make sure everything is looping correctly
                System.out.print(puzzleColumn[columns] + " ");
            } // end columns check

            // if columns sum up to 45 and there are no duplicate numbers in them
            if (isSumCorrect(puzzleColumn) && hasNoDuplicates(puzzleColumn)) {
                // write to the console the values in each row, and that it passed
                System.out.println("(Column " + (rows + 1) + " has PASSED)");
            } else {
                // otherwise write to the console that the row has failed
                System.out.println("Column " + (rows + 1) + " has FAILED");
                // set boolean to false, indicating failure
                passed = false;
            }

        } // end rows check
        // return columns pass-fail boolean check
        return passed;
    } // end checkColumns

    /**
     * checkBlock loops through a multidimensional array. If the array has no duplicate values and if each block of the
     * array is summed to 45, it returns true.
     * checkBlock checks each 3x3 colored block for the above rules.
     * Each 3x3 block is referenced in this method as follows:
     *                                                        1|2|3
     *                                                        4|5|6
     *                                                        7|8|9
     * @param sudoku the multidimensional array of 3x3 blocks that is being checked for the above rules.
     * @return will return boolean indicating success or failure of the 3x3 block check.
     */
    public static boolean checkBlock(int[][] sudoku) {
        // boolean indicating that the 3x3 block has passed
        boolean passed = true;
        System.out.println("\nCHECKING ALL 9 OF THE 3x3 BLOCKS:");

        // check columns of the 3x3 block
        for (int columns = 0; columns < COLUMNS; columns++) {
            // put current 3x3 block into an array
            int[] blocks = new int[9];
            // check rows of the 3x3 block
            for (int rows = 0; rows < ROWS; rows++) {
                // puts the checked 3x3 blocks of the puzzle into the 3x3 blocks array
                blocks[rows] = sudoku[(columns / 3) * 3 + (rows / 3)][(columns * 3) % 9 + (rows % 3)];
                // print is used to make sure everything is looping correctly
                System.out.print(blocks[rows] + " ");
            } // end rows check

            // if there are no duplicates in each 3x3 block and the sum of each 3x3 block is equal to 45
            if (hasNoDuplicates(blocks) && isSumCorrect(blocks)) {
                // print out the individual cases for each passed block to the console, next to the block being tested
                switch (columns) {
                    // block 1
                    case 0: System.out.println("(Top left block has PASSED)");
                            break;
                    // block 2
                    case 1: System.out.println("(Top center block has PASSED)");
                            break;
                    // block 3
                    case 2: System.out.println("(Top right block has PASSED)");
                            break;
                    // block 4
                    case 3: System.out.println("(Middle left block has PASSED)");
                            break;
                    // block 5
                    case 4: System.out.println("(Middle center block has PASSED)");
                            break;
                    // block 6
                    case 5: System.out.println("(Middle right block has PASSED)");
                            break;
                    // block 7
                    case 6: System.out.println("(Bottom left block has PASSED)");
                            break;
                    // block 8
                    case 7: System.out.println("(Bottom center block has PASSED)");
                            break;
                    // block 9
                    case 8: System.out.println("(Bottom right block has PASSED)");
                            break;
                }
            } else {
                // otherwise print out failed to console
                System.out.println("(Sorry, but block " + (columns + 1) + " has FAILED)");
                // and set passed boolean to failed
                passed = false;
            }
        } // end columns check
        // return 3x3 blocks pass-fail boolean check
        return passed;
    } // end checkBlock

    /**
     * isSumCorrect loops through an array. If the array's values sum up 45, it will return true.
     * @param checkArray the array being checked for a sum of 45.
     * @return will return boolean indicating success or failure of the sum check.
     */
    private static boolean isSumCorrect(int[] checkArray) {
        // set initial sum to 0
        int sum = 0;
        // sort the array from lowest number to highest number
        Arrays.sort(checkArray);

        // get the number from each index of the array
        for (int i = 0; i < checkArray.length; i++) {
            // add number in current index to the array that is being summed
            sum += checkArray[i];
        }
        // if the sum is wrong
        if (sum != 45) {
            // print out the incorrect sum to the console
            System.out.println("Sorry, but the sum is " + sum + ", and not 45");
        }
        // return correct sum, and sumIsCorrect is true
        return sum == 45;
    } // end isSumCorrect

    /**
     * hasNoDuplicates loops through an array after it has been sorted. If the numbers are not 1 through 9 or the array
     * has duplicates, it will return false. If the numbers are 1 through 9, and there are no duplicates in the array,
     * it will return true. hasNoDuplicates is best used for final checking, and not incomplete puzzles.
     * @param checkArray the array being checked for duplicates.
     * @return will return boolean indicating success or failure of the number duplicates check.
     */
    private static boolean hasNoDuplicates(int[] checkArray) {
        // set initial index
        int index = 1;
        // sort the array from lowest number to highest number
        Arrays.sort(checkArray);
        // enhanced for loop to loop over the values at each index of the array
        for (int number : checkArray) {
            // if number is not equal to the index
            if (number != index) {
                // print error to console
                System.out.printf("\nERROR: %d is not equal to %d\n", number, index);
                return false;
            }
            // increment counter
            index++;
        } // end enhanced for loop
        // return that no duplicates were found
        return true;
    } // end hasNoDuplicates

    /**
     * findRowDuplicates is used to find duplicate row values for the passed number.
     * @param sudoku the puzzle multidimensional array being passed for checking.
     * @param theRow checks the given row for duplicated values.
     * @param num the number passed that is being checked for duplicated values.
     * @return will return true if there are no duplicate values found in rows.
     */
    public static boolean findRowDuplicates(int[][] sudoku, int theRow, int num ) {
        // search columns
        for (int columns = 0; columns < COLUMNS; columns++) {
            // if the row has the duplicate number being searched
            if (sudoku[theRow][columns] == num) {
                // return fail
                return false;
            }
        } // end columns search
        // return true if no duplicates were found in rows
        return true;
    } // end findRowDuplicates

    /**
     * findColumnDuplicates is used to find duplicate column values for the passed number.
     * @param sudoku the puzzle multidimensional array being passed for checking.
     * @param theColumn checks the given column for duplicated values.
     * @param num the number passed that is being checked for duplicated values.
     * @return will return true if there are no duplicate values found in columns.
     */
    public static boolean findColumnDuplicates(int[][] sudoku, int theColumn, int num) {
        // search rows
        for (int row = 0; row < 9; row++) {
            // if the column has the duplicate number being searched
            if (sudoku[row][theColumn] == num) {
                // return fail
                return false;
            }
        } // end rows search
        // return true if no duplicates were found in columns
        return true;
    } // end findColumnDuplicates

    /**
     * findBlockDuplicates is used to find duplicate block values for the passed number.
     * @param sudoku the puzzle multidimensional array being passed for checking.
     * @param theRow the row value is passed so we can iterate correctly through the array.
     * @param theColumn the column value is passed so we can iterate correctly through the array.
     * @param num the number passed that is being checked for duplicated values.
     * @return will return true if there are no duplicate values found in all 9 of the 3x3 blocks.
     */
    public static boolean findBlockDuplicates(int[][] sudoku, int theRow, int theColumn, int num) {
        // the rows in each 3x3 block
        theRow = (theRow / 3) * 3 ;
        // the columns in each 3x3 block
        theColumn = (theColumn / 3) * 3 ;

        // search the rows in each 3x3 block
        for( int rows = 0; rows < 3; rows++ ) {
            // search the columns in each 3x3 block
            for(int columns = 0; columns < 3; columns++) {
                // if a row or column element has the duplicate number that is being searched for
                if(sudoku[theRow + rows][theColumn + columns] == num ) {
                    // return fail, duplicate number found in 3x3 block
                    return false;
                }
            } // end columns search
        } // end rows search
        // return pass, no duplicate numbers in 3x3 block
        return true;
    } // end findBlockDuplicates

} // end class SudokuChecks