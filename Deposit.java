/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atm_csd321;

/**
 *
 * @author HaiAu
 */
public class Deposit extends Transaction {
    
    private Keypad keypad;
    private DepositSlot depositSlot;
    private double depositAmount;
    private boolean isEnvelopRecieved;
    
    public Deposit(int currentAccountNumber, Screen screen,
                        BankDatabase bankDatabase, Keypad keypad, DepositSlot depositSlot){
        super( currentAccountNumber, screen, bankDatabase );
        this.keypad = keypad;
        this.depositSlot = depositSlot;
    }

    @Override
    public void execute() {
        //prompt user to enter the deposit amount by calling getUserInputAmount()
        getUserInputAmount();
        
        if( depositAmount == 0 ){
            getScreen().displayMessageLine("\nCanceling the transaction...");
        }else{
            getScreen().displayMessageLine("Your deposit amount: $" + depositAmount );
            getScreen().displayMessageLine("Please insert a deposit envelope into the deposit slot.");
            
            if( depositSlot.isEnvelopeReceived() ){
                getScreen().displayMessage("Your total balance is: ");
                //credit the total amount balance, after checking will display in available balance
                getBankDatabase().credit(getAccountNumber(), depositAmount);
                getScreen().displayDollarAmount(getBankDatabase().getTotalBalance(getAccountNumber()));
            }else{
                getScreen().displayMessageLine("Canceling the transaction due to inactivity..");
            }
        }
        
    }
    
    private double getUserInputAmount (){
        getScreen().displayMessage("Enter the deposit amount as a number of cents or 0 to cancel: ");
        int userInput = keypad.getInput();
        
        //if user choose not enter 0 (canceling) then divi
        if( userInput != 0){
            return depositAmount = userInput/100.00;
        }else{
            return depositAmount = 0;
        }
    }
    
}
