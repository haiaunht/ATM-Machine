/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atm_csd321;

import java.util.Scanner;

/**
 *
 * @author HaiAu
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
