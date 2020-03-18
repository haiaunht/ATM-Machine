
package DreamTeam_ATM;

/**
 * Lake Washington Institute of Technology ATM Machine, DreamTeam
 */
public class Withdrawal extends Transaction {

    private Keypad keypad;  //ATM's keypad
    private CashDispenser cashDispenser;    //ATM's cash dispenser  
    private double withdrawalAmount;    //the withdrawal amount
    final int CANCEL_WITHDRAWAL = 6;    //constant indicate canceling transaction

    //Withdrawal constructor initializes attributes
    public Withdrawal(int currentAccountNumber, Screen screen,
            BankDatabase bankDatabase, Keypad keypad, CashDispenser cashDispenser) {
        super(currentAccountNumber, screen, bankDatabase);
        this.keypad = keypad;
        this.cashDispenser = cashDispenser;
    }

    //implement abstract method of parent Transaction class
    @Override
    public void execute() {
        boolean cashDispensed = false;
        boolean transactionIsCancel = false;

        while (!transactionIsCancel && !cashDispensed) {
            //calling getUserInputFromMenuSelection() to have the user's withdrawalAmount      
            getUserInputFromMenuSelection();

            //user enter 6 for cancel or 1-5 for the corresponding amount
            if (withdrawalAmount == CANCEL_WITHDRAWAL) {
                getScreen().displayMessageLine("Canceling the transaction...");
                transactionIsCancel = true;
            } else {
                //retrieve the account's available balance from database 
                double availableBalance = getBankDatabase().getAvailableBalance(getAccountNumber());

                //checking the withdrawal amount with the available balance
                if (withdrawalAmount > availableBalance) {
                    getScreen().displayMessageLine("Insufficient funds in your account. "
                            + "Please choose a smaller amount.");
                } else {
                    //if the amount to withdraw is less or equal then account's available balance
                    //check if cash dispense has enough cash
                    if (!cashDispenser.isSufficientCashAvailable((int) withdrawalAmount)) {
                        getScreen().displayMessageLine("Please choose a smaller withdrawal amount due to "
                                + "the cash dispenser not contain enough cash.");
                        getUserInputFromMenuSelection();
                    } else {
                        getBankDatabase().debit(getAccountNumber(), withdrawalAmount);
                        cashDispenser.dispenseCash((int) withdrawalAmount);
                        cashDispensed = true;

                        getScreen().displayMessage("Your withdrawal amount is: ");
                        getScreen().displayDollarAmount(withdrawalAmount);
                        //display message remind user to take the cash
                        getScreen().displayMessageLine("\nPlease take the money from the cash dispenser!");
                    }
                }
            }
        }
    }

    //return the withdrawal amount coressponding with their choice from 1-5
    private double getUserInputFromMenuSelection() {
        //user enter 0 for cancel or 1 to 5 with the corresponding amount of $20, $40, $60, $100, and $200
        displayWithdrawalMenu();
        int selection = keypad.getInput();
        
        //if user keep enter select out of range 1-6, keep prompting
        while ( convertAmountFromSelection(selection) == 0) {
            //the amount of withdrawal will converted by the selection
            displayWithdrawalMenu();
            selection = keypad.getInput();
        }
        return withdrawalAmount = convertAmountFromSelection(selection);
    }

    //determines what amount by selecting choice 1 - 5
    private void displayWithdrawalMenu() {
        getScreen().displayMessageLine("Withdrawal menu");
        getScreen().displayMessageLine("\t1 - $20\t4 - $100");
        getScreen().displayMessageLine("\t2 - $40\t5 - $200");
        getScreen().displayMessageLine("\t3 - $60\t6 - Cancel transaction");
        getScreen().displayMessage("Choose a withdrawal amount: ");

    }

    //convert the seclection of 1 through 5 to the corresponding withdrawal amount 
    private double convertAmountFromSelection(int select) {
        switch (select) {
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
            case 6:
                return withdrawalAmount = CANCEL_WITHDRAWAL;
            default:
                System.out.println("Invalid withdrawal amount!\n");
                return 0;
        }
    }

}
