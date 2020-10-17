package view;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MenuView {

    static Scanner input = new Scanner(System.in);

    public static void init() {
        menu();
    }

    private static void menu() {
        Scanner input = new Scanner(System.in);
        boolean exit = false;
        while (!exit) {
            System.out.println("SELECIONE UMA OPÇÃO:");
            System.out.println("[1] Inserir bordas");
            System.out.println("[2] Inverter cores");
            System.out.println("[3] Girar 90º");
            System.out.println("[4] Salvar imagem");
            System.out.println("[5] Imprimir imagem no console");
            System.out.println("[0] Encerrar aplicação");
            System.out.print("Opção: ");
            try {
                int option = input.nextInt();
                switch (option) {
                    case 1:
                        System.out.println("Opção 1");
                        break;
                    case 2:
                        System.out.println("Opção 2");
                        break;
                    case 3:
                        System.out.println("Opção 3");
                        break;
                    case 4:
                        System.out.println("Opção 4");
                        break;
                    case 5:
                        System.out.println("Opção 5");
                        break;
                    case 0:
                        System.out.println("\nENCERRANDO APLICAÇÃO...");
                        exit = true;
                        break;
                    default:
                        System.err.println("Opção inválida!");
                        break;
                }
            } catch (InputMismatchException e) {
                System.err.println("Valor inválido! Tente novamente!");
                menu();
                break;
            }
        }
    }



}
