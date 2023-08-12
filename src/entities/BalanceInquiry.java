package entities;

import database.BankDatabase;
import model.Transaction;

public class BalanceInquiry extends Transaction {
    public BalanceInquiry(int accountNumber, Screen screen, BankDatabase bankDatabase) {
        super(accountNumber, screen, bankDatabase);
    }

    @Override
    public void execute() {
        BankDatabase bankDatabase = getBankDatabase();
        Screen screen = getScreen();

        double availableBalance = bankDatabase.getAvailableBalance(getAccountNumber());
        double limit = bankDatabase.getLimit(getAccountNumber());
        double totalBalance = bankDatabase.getTotalBalance(getAccountNumber());

        screen.displayMessageLine("\nInformações do Saldo:");
        screen.displayMessage(" - Saldo disponível" +" ".repeat(1)+": ");
        screen.displayRealAmount(availableBalance);
        screen.displayMessage(" \n - Limite disponível: ");
        screen.displayRealAmount(limit);
        screen.displayMessage(" \n - Saldo total" +" ".repeat(6)+": ");
        screen.displayRealAmount(totalBalance);
        screen.displayMessageLine("");
    }
}
