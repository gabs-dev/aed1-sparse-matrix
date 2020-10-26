package view;

import matrix.SparseMatrix;

import java.io.*;
import java.util.Calendar;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MenuView {

    static Scanner input = new Scanner(System.in);
    static SparseMatrix matrix;
    static int maxValue;

    /**
     * Método que inicia a aplicação
     */
    public static void init() {
        menu();
    }

    /**
     * Método que exibe o menu principal no console
     */
    private static void menu() {
        int option = -1;
        while (option != 0) {
            System.out.println("------------------------");
            System.out.println("     MENU PRINCIPAL     ");
            System.out.println("------------------------");
            System.out.println("--- ESCOLHA UM OPÇÃO ---");
            System.out.println("[1] Ler arquivo");
            System.out.println("[0] Encerrar a aplicação");
            System.out.println("------------------------");
            System.out.print("Opção: ");
            option = input.nextInt();
            System.out.println("------------------------");
            switch (option) {
                case 1:
                    readFile();
                    break;
                case 0:
                    System.out.println("Encerrando a aplicação...");
                    break;
                default:
                    System.out.println("Opção inválida");
                    break;
            }
        }
    }

    /**
     * Método exibe no cosole as opções de alteracões que podem ser feitas na imagem
     */
    private static void imageMenu() {
        int option = -1;
        while (option != 0) {
            System.out.println("------------------------");
            System.out.println("       MENU IMAGEM      ");
            System.out.println("------------------------");
            System.out.println("--- ESCOLHA UM OPÇÃO ---");
            System.out.println("[1] Exibir imagem");
            System.out.println("[2] Inserir bordas");
            System.out.println("[3] Inverter cores");
            System.out.println("[4] Rotacionar 90°");
            System.out.println("[5] Salvar imagem");
            System.out.println("[0] Encerrar a aplicação");
            System.out.println("------------------------");
            System.out.print("Opção: ");
            option = input.nextInt();
            System.out.println("------------------------");
            switch (option) {
                case 1:
                    showImage();
                    break;
                case 2:
                    insertBorders();
                    break;
                case 3:
                    reverseColors();
                    break;
                case 4:
                    rotate();
                    break;
                case 5:
                    saveFile();
                    break;
                case 0:
                    System.out.println("Encerrando a aplicação...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opção inválida");
                    break;
            }
        }
    }

    /**
     * Método cria um novo arquivo e insere os dados da matriz dentro do mesmo.
     */
    private static void saveFile() {
        System.out.println("------------------------");
        System.out.println("     SALVAR ARQUIVO     ");
        System.out.println("------------------------");

        BufferedWriter writer;
        String fileName = generateName();

        try {
            System.out.println("Salvando o arquivo. Aguarde...");
            writer = new BufferedWriter(new FileWriter(fileName));
            String stringPgm = matrix.generatePgmString(maxValue);
            writer.write(stringPgm);
            writer.close();
            java.awt.Desktop.getDesktop().open(new File(fileName));
            System.out.println("Arquivo " + fileName+ " salvo na raiz do projeto.");
        } catch (IOException e) {
            System.err.println("Erro ao abrir ou criar o arquivo");
        }
    }

    /**
     * Método que rotaciona matriz 90° no sentido horário
     */
    private static void rotate() {
        System.out.println("------------------------");
        System.out.println("    ROTACIONAR IMAGEM   ");
        System.out.println("------------------------");
        try {
            System.out.println("Rotacionando imagem. Aguarde...");
            matrix = matrix.rotateClockwise();
            System.out.println("Imagem rotacionada com sucesso!");
        } catch (NullPointerException e) {
            System.err.println("Erro ao rotacionar imagem cores:\n" + e.getMessage());
            imageMenu();
        }
    }

    /**
     * Método que inverte os valores da matriz utilizando o valor máximo inserido na mesma.
     */
    private static void reverseColors() {
        System.out.println("------------------------");
        System.out.println("     INVERTER CORES     ");
        System.out.println("------------------------");

        try {
            System.out.println("Invertendo as cores...");
            matrix.reverseColors(maxValue);
            System.out.println("Cores invertidas com sucesso!");
        } catch (NullPointerException e) {
            System.err.println("Erro ao inverter cores:\n" + e.getMessage());
            imageMenu();
        }
    }

    /**
     * Método que tem como entrada o tamanho da borda que será inserida na imagem
     */
    private static void insertBorders() {
        System.out.println("------------------------");
        System.out.println("      INSERIR BORDAS    ");
        System.out.println("------------------------");

        try {
            System.out.print("Tamanho da borda que deseja inserir: ");
            int width = input.nextInt();
            System.out.println("Inserindo bordas...");
            matrix.insertBorders(width, maxValue);
            System.out.println("Bordas inseridas com sucesso!");
        } catch (InputMismatchException e) {
            System.err.println("Valor inválido.");
            imageMenu();
        } catch (IllegalArgumentException e) {
            System.err.println("Erro ao inserir bordas:\n" + e.getMessage());
            imageMenu();
        } catch (NullPointerException e) {
            System.err.println("Erro ao inserir bordas:\n" + e.getMessage());
            imageMenu();
        }
    }

    /**
     * Método que imprime no console a imagem no formato de matriz.
     * Utiliza o toString para fazer a impressão.
     */
    private static void showImage() {
        System.out.println("------------------------");
        System.out.println("      EXIBIR IMAGEM     ");
        System.out.println("------------------------");
        System.out.println("--       IMAGEM       --");
        if(matrix.size() > 0)
            System.out.println(matrix);
        else
            System.out.println("A matriz está vazia");
    }

    /**
     * Método que lê o nome do arquivo da imagem.
     * Após a leitura do nome do arquivo ele chama o método loadFile() que é resposável por careegar o arquivo.
     */
    private static void readFile() {
        System.out.println("------------------------");
        System.out.println("       LER ARQUIVO      ");
        System.out.println("------------------------");
        System.out.println("-O arquivo deve estar na\n" + "pasta raíz do projeto-");
        System.out.print("Nome do arquivo: ");
        String fileName = input.next();
        fileName += ".pgm";
        System.out.println("Lendo o arquivo. Aguarde...");
        loadFile(fileName);
        imageMenu();
    }

    /**
     * Método que lê uma aquivo .pgm e cria uma Matriz Esparsa contendo os valores da imagem.
     * @param fileName Nome da imagem que será lida
     */
    private static void loadFile(String fileName) {
        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);

            if(file.exists()) {
                String fileType = scanner.next();
                if (!fileType.equals("P2"))
                    throw new IOException("O formato do arquivo não é suportado");

                int column = scanner.nextInt();
                int row = scanner.nextInt();
                maxValue = scanner.nextInt();
                matrix = new SparseMatrix(row, column);

                for (int i = 0; i < row; i++) {
                    for (int j = 0; j < column; j++) {
                        int value = scanner.nextShort();
                        if (value != 0)
                            matrix.insert(value, i , j);
                    }
                }
            }

            scanner.close();
            System.out.println("Sucesso ao ler o arquivo.");
        } catch (FileNotFoundException e) {
            System.err.println("O arquivo não foi encontrado");
            menu();
        } catch (IOException e) {
            System.err.println("Falha ao ler o arquivo:\n" + e.getMessage());
            menu();
        } catch (Exception e) {
            System.err.println("Falha ao ler o arquivo:\n" + e.getMessage());
        }
    }

    /**
     * Método gera um novo nome para o arquivo que será salvo
     * @return String contendo o novo nome do arquivo
     */
    private static String generateName() {
        Date date = new Date();
        Calendar today = Calendar.getInstance();
        today.setTime(date);
        return "nova-imagem" + "_" + today.get(Calendar.DAY_OF_MONTH) + "-" +
                today.get(Calendar.MONTH) + "-" + today.get(Calendar.YEAR) + "_" + today.get(Calendar.HOUR) +
                "h" + today.get(Calendar.MINUTE) + "m" + today.get(Calendar.SECOND) +
                "s" + ".pgm";
    }

}
