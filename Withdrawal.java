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
public class Withdrawal extends Transaction {
    private Keypad keypad;
    private CashDispenser cashDispenser;
    private int currentAccount;
    private double withdrawalAmount;
    final int CANCEL_WITHDRAWAL = 6;
    
    public Withdrawal( int currentAccountNumber, Screen screen,
                        BankDatabase bankDatabase, Keypad keypad, CashDispenser cashDispenser){
        super( currentAccountNumber, screen, bankDatabase);
        this.keypad = keypad;
        this.cashDispenser = cashDispenser;
    }

    @Override
    public void execute() {
        //calling getUserInputFromMenuSelection() to have the user's withdrawal amount
        getUserInputFromMenuSelection();
        
        //user enter 6 for cancel
        if( withdrawalAmount == CANCEL_WITHDRAWAL ){
            getScreen().displayMessageLine("Canceling the transaction...");
        }else{
            //TODO --- checking with cash dispenser
            double availableBalance = getBankDatabase().getAvailableBalance( currentAccount );
            getScreen().displayDollarAmount( withdrawalAmount );
            
            if( withdrawalAmount > availableBalance ){
                getScreen().displayMessageLine("The amount is greater than your account balance. "
                        + "Please choose a smaller amount");
                getUserInputFromMenuSelection();
            }
            
            if( !cashDispenser.isSufficientCashAvailable( (int)withdrawalAmount )){
                getScreen().displayMessageLine("Please choose a smaller withdrawal amount due to "
                        + "the cash dispenser not contain enough cash.");
                getUserInputFromMenuSelection();
            }
            
            //debit from bank database and dispense the withdrawal cash amount
            getBankDatabase().debit( getAccountNumber(), withdrawalAmount );
            cashDispenser.dispenseCash( (int)withdrawalAmount );
            
            
        }
    }
    
    private double getUserInputFromMenuSelection(){
        //user enter 0 for cancel or 1 to 5 with the corresponding amount of $20, $40, $60, $100, and $200
        displayWithdrawalMenu();
        int selection = keypad.getInput();
        
        //the amount of withdrawal will converted by the selection
        return withdrawalAmount = convertAmountFromSelection( selection );        
    }
    
    private void displayWithdrawalMenu(){
        getScreen().displayMessageLine("Withdrawal menu");
        getScreen().displayMessageLine("\t1 - $20\t4 - $100");
        getScreen().displayMessageLine("\t2 - $40\t5 - $200");
        getScreen().displayMessageLine("\t3 - $60\t6 - Cancel transaction");
        getScreen().displayMessage("Choose a withdrawal amount: ");
        
    }
    
    private double convertAmountFromSelection( int select ){
        switch ( select ){
            case 1:
                return withdrawalAmount = 20;
            case 2:
                return withdrawalAmount = 40;
            case 3:
                return withdrawalAmount = 60;
            case 4:
                return withdrawalAmount = 100;
            case 5:
                return withdrawalAmount = 200;
            default:
                System.out.println("Invalid withdrawal amount!");
                return 0;
        }
    }
    
}
