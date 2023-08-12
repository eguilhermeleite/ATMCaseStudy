package entities;

import database.BankDatabase;
import model.Transaction;

public class ATM {
    private boolean userAuthenticated;
    private int currentAccountNumber;
    private Screen screen;
    private Keypad keypad;
    private CashDispenser cashDispenser;
    private DepositSlot depositSlot;
    private BankDatabase bankDatabase;

    private static final int BALANCE_INQUIRY = 1;
    private static final int WITHDRAWAL = 2;
    private static final int DEPOSIT = 3;
    private static final int EXIT = 4;

    public ATM() {
        userAuthenticated = false; // usuário não foi autenticado para iniciar
        currentAccountNumber = 0; // nenhum número atual de conta para iniciar
        screen = new Screen(); // cria a tela
        keypad = new Keypad(); // cria o teclado numérico
        cashDispenser = new CashDispenser(); // cria o dispensador de cédulas
        depositSlot = new DepositSlot(); // cria a abertura para depósito
        bankDatabase = new BankDatabase(); // cria o banco de dados de informações de contas
    } // fim do construtor ATM sem argumento

    // inicia ATM
    public void run() {
        // dá boas-vindas e autentica o usuário; realiza transações
        while (true) {
            // faz um loop enquanto o usuário ainda não está autenticado
            while (!userAuthenticated) {
                screen.displayMessageLine( "\nBoas Vindas ao EdBank!" );
                authenticateUser(); // autentica o usuário
            } // fim do while

            performTransactions(); // o usuário agora está autenticado
            userAuthenticated = false; // reinicializa antes da próxima sessão do ATM
            currentAccountNumber = 0; // reinicializa antes da próxima sessão do ATM
            screen.displayMessageLine( "\nObrigado! Tchau!" );
        } // fim do while
    } // fim do método run

    // tenta autenticar o usuário contra o banco de dados
    private void authenticateUser() {
        screen.displayMessage( "\nPor favor, digite o número da sua conta: " );
        int accountNumber = keypad.getInput(); // insere o número de conta
        screen.displayMessage( "\nDigite o seu PIN: " ); // solicita o PIN
        int pin = keypad.getInput(); // insere o PIN

        // configura userAuthenticated como um valor booleano retornado pelo banco de dados
        userAuthenticated =
                bankDatabase.authenticateUser(accountNumber, pin);

        // verifica se a autenticação foi bem-sucedida
        if (userAuthenticated) {
            currentAccountNumber = accountNumber; // salva a conta do usuário #
        } // fim do if
        else
            screen.displayMessageLine(
                    "Número da conta ou PIN inválido... Por favor, tente novamente." );
    } // fim do método authenticateUser

    // exibe o menu principal e realiza transações
    private void performTransactions() {
        // variável local para armazenar a transação atualmente processada
        Transaction currentTransaction = null;

        boolean userExited = false; // usuário optou por não sair

        // faz um loop enquanto o usuário não escolher a opção para sair do sistema
        while (!userExited) {
            // mostra o menu principal e obtém a seleção de usuário
            int mainMenuSelection = displayMainMenu();

            // decide como prosseguir com base na seleção de menu feita pelo usuário
            switch (mainMenuSelection) {
                // o usuário optou por realizar um entre três tipos de transações
                case BALANCE_INQUIRY:
                case WITHDRAWAL:
                case DEPOSIT:

                    // inicializa como o novo objeto do tipo escolhido
                    currentTransaction =
                            createTransaction(mainMenuSelection);

                    currentTransaction.execute(); // executa a transação
                    break;
                case EXIT: // usuário optou por terminar a sessão
                    screen.displayMessageLine( "\nEncerrando a seção..." );
                    userExited = true; // essa sessão de ATM deve terminar
                    break;
                default: // usuário não inseriu um inteiro de1a4
                    screen.displayMessageLine(
                            "\nVocê não inseriu uma opção válida... Tente novamente." );
                    break;
            } // fim de switch
        } // fim do while
    } // fim do método performTransactions

    // exibe o menu principal e retorna uma seleção de entrada
    private int displayMainMenu() {
        screen.displayMessageLine( "\nMenu Principal:" );
        screen.displayMessageLine( "1 - Saldo" );
        screen.displayMessageLine( "2 - Saque" );
        screen.displayMessageLine( "3 - Depósito" );
        screen.displayMessageLine( "4 - Sair\n" );
        screen.displayMessage( "Escolha uma opção: " );
        return keypad.getInput(); // retorna a seleção do usuário
    } // fim do método displayMainMenu

    // retorna o objeto da subclasse de Transaction especificada
    private Transaction createTransaction(int type) {
        Transaction temp = null; // variável Transaction temporária

        // determina qual tipo de Transaction criar
        switch (type) {
            case BALANCE_INQUIRY: // cria uma nova transação BalanceInquiry
                temp = new BalanceInquiry(
                        currentAccountNumber, screen, bankDatabase);
                break;
            case WITHDRAWAL: // cria uma nova transação Withdrawal
                temp = new Withdrawal(currentAccountNumber, screen,
                        bankDatabase, keypad, cashDispenser);
                break;
            case DEPOSIT: // cria uma nova transação Deposit
                temp = new Deposit(currentAccountNumber, screen,
                        bankDatabase, keypad, depositSlot);
                break;
        } // fim de switch

        return temp; // retorna o objeto recém-criado
    } // fim do método createTransaction
} // fim da classe ATM

