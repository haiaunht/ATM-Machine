
package DreamTeam_ATM;

import java.util.Scanner;

/**
 * Lake Washington Institute of Technology ATM Machine
 * DreamTeam 
 */
public class Keypad {

    private Scanner input; // reads data from the command line

    // no-argument constructor initializes the Scanner
    public Keypad() {
        input = new Scanner(System.in);
    }

    // return an integer value entered by user 
    public int getInput() {
        return input.nextInt(); // we assume that user enters an integer
    }
}
