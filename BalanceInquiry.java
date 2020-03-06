
package atm_csd321;

/**
 *
 * @author HaiAu
 */
public class BalanceInquiry extends Transaction {

    private double availableBalance;
    private double totalBalance;
    
    public BalanceInquiry( int currentAccountNumber, Screen screen, BankDatabase bankDatabase){
        super( currentAccountNumber, screen, bankDatabase );      
    }

    @Override
    public void execute() {
        //available and total balance will call superclass Transaction methods
        //getBankDatabase() get available and total amount 
        availableBalance = getBankDatabase().getAvailableBalance(getAccountNumber());
        totalBalance = getBankDatabase().getTotalBalance(getAccountNumber());
        
        //calling supperclass getScreen() method to display the available and total balance
        getScreen().displayMessageLine("Balance information: ");
        getScreen().displayMessage("\t+ Available balance: ");
        getScreen().displayDollarAmount(availableBalance);
        getScreen().displayMessage("\n\t+ Total balance: ");
        getScreen().displayDollarAmount(totalBalance);
    }
}
