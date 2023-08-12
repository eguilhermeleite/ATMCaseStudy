package entities;

import java.util.Scanner;

public class Keypad {
    private Scanner input;

    //construtor sem argumentos inicializa a classe Scanner
    public Keypad(){
        input = new Scanner(System.in);
    }

    // retorna um valor inteiro inserido pelo usuário
    public int getInput(){
        return input.nextInt();//supondo que o usuario digite um valor inteiro
    }
}
