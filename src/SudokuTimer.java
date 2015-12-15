/**
 * PROJECT: Sudoku Puzzle
 *
 * CLASS: SudokuTimer
 *
 * DESCRIPTION: A clock timer to keep track of how much time a user would take to complete their current puzzle. The
 *              timer has the option to start the clock, pause the clock, or start a new clock from 0.
 *
 * DATE: November 2015
 *
 * AUTHOR: Lush Sleutsky
 *
 * IDE: IntelliJ IDEA 15.0.1
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SudokuTimer extends JFrame {
    // make a new timer
    private Timer timer;
    // time one second in milliseconds
    public static final int ONE_SEC = 1000;
    // time one tenth of a second
    public static final int TENTH_SEC = 100;

    // create new "start" button
    private JButton startBtn = new JButton("Start Timer");
    // create new "stop" button
    private JButton stopBtn = new JButton("Stop Timer");
    // create new "reset" button
    private JButton resetBtn = new JButton("New Timer");
    // time labels
    private JLabel timeLabel = new JLabel();
    // create a new top panel
    private JPanel topPanel = new JPanel();
    // create a new bottom panel
    private JPanel bottomPanel = new JPanel();

    // number of clock ticks; tick can be 1.0s or 0.1s
    private int clockTick = 0;
    // time in seconds and milliseconds
    private double clockTime = (double)clockTick / 10.0;
    // string to display clock time
    private String clockTimeToString = Double.toString(clockTime);
    // declare font size
    private int fontSize = 90;
    // set font type, font weight, and font size for clock display
    private Font myClockFont = new Font("Helvetica", Font.BOLD, fontSize);

    /**
     * This constructor displays the actual Sudoku Timer stopwatch on the screen.
     * Buttons are available to start the timer, end the timer, and reset the timer.
     */
    public SudokuTimer() {
        timeLabel.setFont(myClockFont);
        timeLabel.setText(clockTimeToString);

        timer = new Timer(TENTH_SEC, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                clockTick++;
                // so tenth of seconds are shown when timer runs
                clockTime = (double)clockTick / 10.0;
                clockTimeToString = Double.toString(clockTime);
                timeLabel.setText(clockTimeToString);
            }
        });

        // call new button listener object
        ButtonListener buttonListener = new ButtonListener();
        // start button action listener
        startBtn.addActionListener(buttonListener);
        // stop button action listener
        stopBtn.addActionListener(buttonListener);
        // reset button action listener
        resetBtn.addActionListener(buttonListener);
    } // end SudokuTimer constructor

    /**
     * This method runs the actual Sudoku Timer stopwatch.
     * Allows the SudokuTimer method to use this logic.
     */
    public void launchTimer() {
        // hold light red HTML color in variable
        String lightRed = "#ff8484";
        // hold light blue HTML color in variable
        String lightBlue = "#c1d1ff";
        // background color for top panel that holds clock timer
        topPanel.setBackground(Color.decode(lightRed));
        // background color for bottom panel that holds buttons
        bottomPanel.setBackground(Color.decode(lightBlue));
        // add clock to top panel
        topPanel.add(timeLabel);
        // add start button to bottom panel
        bottomPanel.add(startBtn);
        // add stop button to bottom panel
        bottomPanel.add(stopBtn);
        // add reset button to bottom panel
        bottomPanel.add(resetBtn);

        // set a layout to display all panels in the frame
        setLayout(new BorderLayout());
        // position top panel in the center
        add(topPanel, BorderLayout.CENTER);
        // position bottom panel at the bottom
        add(bottomPanel, BorderLayout.SOUTH);
        // set size of frame
        setSize(375,150);
        // set frame title
        setTitle("Sudoku Puzzle Timer");
        // set visibility of frame
        setVisible(true);
        // place timer in middle of screen
        setLocationRelativeTo(null);
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    } // end launchTimer

    /**
     * This private inner class adds action listeners to the buttons so they can be clicked.
     * Allows each button to have an independent action when clicked, defined in method body.
     */
    private class ButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent evt) {
            if (evt.getSource() == startBtn) { // start button
                // start the timer
                timer.start();
            } else if (evt.getSource() == stopBtn) { // stop button
                // stop the timer
                timer.stop();
            } else if (evt.getSource() == resetBtn) { // reset button
                // set clock tick to 0
                clockTick = 0;
                // so NaN doesn't show when clock is reset
                clockTime = (double)clockTick / 10.0;
                // put the clock ticks to a string that can be displayed
                clockTimeToString = Double.toString(clockTime);
                // display clock time string
                timeLabel.setText(clockTimeToString);
            }
        }
    } // end inner class ButtonListener

} // end class SudokuTimer