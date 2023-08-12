package entities;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
public class Account {

    @Getter(AccessLevel.NONE)
    private int pin;

    private int accountNumber;
    private double availableBalance;
    private double limit;
    private double totalBalance;

    public Account( int theAccountNumber, int thePIN,double theAvailableBalance,double theLimit ){
        accountNumber = theAccountNumber;
        pin = thePIN;
        availableBalance = theAvailableBalance;
        limit = theLimit;
        totalBalance = theAvailableBalance + theLimit;
    }

    // determina se um PIN especificado pelo usuário corresponde ao PIN em Account
    public boolean validatePIN(int userPIN) {
        return (userPIN == pin) ? true : false;
    }

    //credita valor na conta
    public void credit(double amount) {
        totalBalance += amount;
    }

    //debita valor na conta
    public void debit(double amount) {
        availableBalance -= amount;
        totalBalance -= amount;
    }
}
