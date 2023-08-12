package entities;

import database.BankDatabase;
import model.Transaction;

public class Withdrawal extends Transaction {
    private int amount;

    private Keypad keypad;
    private CashDispenser cashDispenser;
    private final static int CANCELED = 6;

    // Construtor de Withdrawal
    public Withdrawal(int userAccountNumber, Screen atmScreen,
                      BankDatabase atmBankDatabase, Keypad atmKeypad,
                      CashDispenser atmCashDispenser) {
        // inicializa as variáveis da superclasse
        super(userAccountNumber, atmScreen, atmBankDatabase);

        // inicializa as referências ao teclado numérico e ao dispensador de cédulas
        keypad = atmKeypad;
        cashDispenser = atmCashDispenser;
    }

    @Override
    public void execute() {
        boolean cashDispensed = false;
        double availableBalance;
        BankDatabase bankDatabase = getBankDatabase();
        Screen screen = getScreen();

        // faz um loop até as cédulas serem entregues ou o usuário cancelar
        do {
            amount = displayMenuOfAmounts();

            if (amount != CANCELED) {
                availableBalance = bankDatabase.getAvailableBalance(getAccountNumber());

                if (amount <= availableBalance) {

                    if (cashDispenser.isSufficientCashAvailable(amount)) {
                        bankDatabase.debit(getAccountNumber(), amount);
                        cashDispenser.dispenseCash(amount);
                        cashDispensed = true;
                        screen.displayMessageLine("\nPegue o seu dinheiro na gaveta.");
                    } else
                        screen.displayMessageLine("\nEsse caixa eletrônico não tem o valor solicitado disponível..." +
                                "Por favor, tente solicitar um valor menor ");

                } else {
                    screen.displayMessageLine("\nSua conta não tem o saldo suficiente para essa solicitação..." +
                            "Por favor, tente solicitar outro valor.");
                }
            } else {
                screen.displayMessageLine("\nCancelando a transação...");
                return;
            }
        } while (!cashDispensed);
    }// fim do metodo execute

    private int displayMenuOfAmounts() {
        int userChoice = 0;
        Screen screen = getScreen();
        int amounts[] = {0, 20, 40, 60, 100, 200};

        while (userChoice == 0) {
            // exibe o menu
            screen.displayMessageLine("\nMenu de Saque:");
            screen.displayMessageLine("1 - R$ 20");
            screen.displayMessageLine("2 - R$ 40");
            screen.displayMessageLine("3 - R$ 60");
            screen.displayMessageLine("4 - R$ 100");
            screen.displayMessageLine("5 - R$ 200");
            screen.displayMessageLine("6 - Cancelar");
            screen.displayMessage("\nEscolha um valor de saque: ");
            int input = keypad.getInput();

            switch (input) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                    userChoice = amounts[input];
                    break;
                case CANCELED:
                    userChoice = CANCELED;
                    break;
                default:
                    screen.displayMessageLine("\nValor indefinido, tente novamente");
            }
        }// fim do while
        return userChoice;
    }// fim do metodo displayMenuOfAmounts

}// fim da classe

