
package DreamTeam_ATM;

/**
 * Lake Washington Institute of Technology ATM Machine
 * DreamTeam 
 */
public class BalanceInquiry extends Transaction {
    
    //BalanceInquiry constructor initialize attributes
    public BalanceInquiry( int currentAccountNumber, Screen screen, BankDatabase bankDatabase){
        super( currentAccountNumber, screen, bankDatabase );      
    }

    //implement abstract method of parent Transaction class
    @Override
    public void execute() {
        //calling supperclass getScreen() method to display the available and total balance
        getScreen().displayMessageLine("Balance information: ");
        getScreen().displayMessage("\t+ Available balance: ");
        getScreen().displayDollarAmount(getBankDatabase().getAvailableBalance(getAccountNumber()));
        getScreen().displayMessage("\n\t+ Total balance: ");
        getScreen().displayDollarAmount(getBankDatabase().getTotalBalance(getAccountNumber()));
        getScreen().displayMessageLine("");
    }
}
