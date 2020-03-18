
package DreamTeam_ATM;

/**
 * Lake Washington Institute of Technology ATM Machine
 * DreamTeam 
 */
public class Deposit extends Transaction {
    
    private Keypad keypad;  //ATM's keypad
    private DepositSlot depositSlot;    //ATM's deposit slot    
    private double depositAmount;   //deposit amount 
    
    //Deposit constructor initializes attributes
    public Deposit(int currentAccountNumber, Screen screen,
                        BankDatabase bankDatabase, Keypad keypad, DepositSlot depositSlot){
        super( currentAccountNumber, screen, bankDatabase );
        this.keypad = keypad;
        this.depositSlot = depositSlot;
    }

    //implement abstract method of parent Transaction class
    @Override
    public void execute() {
        //prompt user to enter the deposit amount by calling getUserInputAmount()
        getUserInputAmount();
        
        if( depositAmount == 0 ){
            getScreen().displayMessageLine("\nCanceling the transaction...");
        }else{
            getScreen().displayMessage("Your deposit amount: ");
            getScreen().displayDollarAmount( depositAmount );
            getScreen().displayMessageLine("\nPlease insert a deposit envelope into the deposit slot.");
            
            if( depositSlot.isEnvelopeReceived() ){
                getScreen().displayMessage("Your total balance is: ");
                //credit the total amount balance, after checking will display in available balance
                getBankDatabase().credit(getAccountNumber(), depositAmount);
                getScreen().displayDollarAmount(getBankDatabase().getTotalBalance(getAccountNumber()));
                getScreen().displayMessageLine("");
            }else{
                getScreen().displayMessageLine("Canceling the transaction due to inactivity..");
            }
        }
        
    }
    
    //return the deposit amount entered by user
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
