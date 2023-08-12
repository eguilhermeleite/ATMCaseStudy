package database;

import entities.Account;

public class BankDatabase {

    private Account accounts[];

    // construtor BankDatabase sem argumento inicializa as contas
    public BankDatabase() {
        accounts = new Account[3];
        accounts[0] = new Account(12345, 54321, 5_582, 10_000);
        accounts[1] = new Account(56789, 12345, 3_280, 5_000);
        accounts[2] = new Account(11223, 11223, 400, 0);
    }

    // recupera o objeto Account que contém o número de conta especificado
    private Account getAccount(int accountNumber) {
        // faz um loop pelas contas procurando uma correspondência com o número de conta
        for (Account currentAccount : accounts) {
            // retorna a conta atual se uma correspondência for localizada
            if (currentAccount.getAccountNumber() == accountNumber) return currentAccount;
        }
        return null;
    }

    public boolean authenticateUser(int userAccountNumber, int userPIN) {
        Account userAccount = getAccount(userAccountNumber);
        return (userAccount != null) ? userAccount.validatePIN(userPIN) : false;
    }

    public double getAvailableBalance(int userAccountNumber) {
        return getAccount(userAccountNumber).getAvailableBalance();
    }

    public  double getLimit(int userAccountNumber){
        return getAccount(userAccountNumber).getLimit();
    }

    public double getTotalBalance(int userAccountNumber) {
        return getAccount(userAccountNumber).getTotalBalance();
    }

    public void credit(int userAccountNumber, double amount) {
        getAccount(userAccountNumber).credit(amount);
    }

    public void debit(int userAccountNumber, double amount) {
        getAccount(userAccountNumber).debit(amount);
    }
}
