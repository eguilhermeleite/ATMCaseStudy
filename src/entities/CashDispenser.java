package entities;

public class CashDispenser {

    // o número inicial padrão de cédulas no dispensador de cédulas
    private final static int INITIAL_COUNT = 500;
    private int count; // número de cédulas de R$20 remanescente

    // construtor sem argumento CashDispenser inicializa a count para o padrão
    public CashDispenser() {
        count = INITIAL_COUNT;  // configura atributo count como o padrão
    }

    // simula a entrega da quantia especificada de cédulas
    public void dispenseCash(int amount) {
        int billsRequired = amount / 20; // numero de cedulas de R$20 requerido
        count -= billsRequired; // atualiza a contagem das cedulas
    }

    // indica se o dispensador de cédulas pode entregar a quantia desejada
    public boolean isSufficientCashAvailable(int amount) {
        int billsRequired = amount / 20;
        return (count >= billsRequired) ? true : false;
    }


}
