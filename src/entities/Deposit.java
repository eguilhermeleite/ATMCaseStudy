package entities;

import database.BankDatabase;
import model.Transaction;

public class Deposit extends Transaction {
    private double amount;
    private Keypad keypad;
    private DepositSlot depositSlot;
    private final static int CANCELED = 0;

    public Deposit(int userAccountNumber, Screen atmScreen, BankDatabase atmBankDatabase,
                   Keypad atmKeypad, DepositSlot atmDepositSlot) {
        super(userAccountNumber, atmScreen, atmBankDatabase);
        keypad = atmKeypad;
        depositSlot = atmDepositSlot;
    }

    @Override
    public void execute() {
        BankDatabase bankDatabase = getBankDatabase();
        Screen screen = getScreen();
        amount = promptForDepositAmount();
        if (amount != CANCELED) {
            screen.displayMessage("\nPor favor, insira o envelope contendo o valor de ");
            screen.displayRealAmount(amount);
            screen.displayMessageLine(".");
            boolean envelopeReceived = depositSlot.isEnvelopeReceived();
            if (envelopeReceived) {
                screen.displayMessageLine("\nSeu envelope foi " +
                        "recebido.\nOBSERVAÇÃO: O dinheiro recém-depositado " +
                        "não estará disponível até que verifiquemos o valor " +
                        "de qualquer envelope fechado e até que seus cheques sejam compensados");
                bankDatabase.credit(getAccountNumber(), amount);
            } else {
                screen.displayMessageLine("\nVocê não inseriu um " +
                        "envelope, então o caixa eletrônico cancelou sua transação.");
            }
        }else {
            screen.displayMessageLine( "\nCancelando a transação..." );
        }
    }// fim do metodo execute

    private double promptForDepositAmount(){
        Screen screen = getScreen();
        screen.displayMessage( "\nPor favor, digite um valor em CENTAVOS para depositar (EXEMPLO: " +
                "para depositar R$150,00 - Digite: 15000) OU digite 0 para cancelar: ");
        int input = keypad.getInput();
        if ( input == CANCELED )
            return CANCELED;
        else{
            return ( double ) input / 100;
        }
    }

}// fim da classe
