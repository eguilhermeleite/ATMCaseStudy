package entities;

public class Screen {

    //exibe mensagem sem retorno de carro
    public void displayMessage(String message){
        System.out.print(message);
    }

    //exibe mensagem com retorno de carro
    public void displayMessageLine(String message){
        System.out.println(message);
    }

    //exibe um valor em reais
    public void displayRealAmount(double amount){
        System.out.printf("R$ %,.2f", amount);
    }

}
