package matrix;

public class SparseMatrix {

    private Node head;
    private int rows;
    private int columns;

    public SparseMatrix() {
        head = new Node();
        head.setRow(-1);
        head.setColumn(-1);
        createSentries();
    }

    public SparseMatrix(int rows, int columns) {
        head = new Node();
        head.setRow(-1);
        head.setColumn(-1);
        this.rows = rows;
        this.columns = columns;
        createSentries();
    }

    public Node getHead() {
        return head;
    }

    public void setHead(Node head) {
        this.head = head;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    private void createSentries() {
        // cria as sentinelas das colunas
        Node aux = new Node(); // cria um nova celula
        aux.setColumn(0);
        head.setRight(aux); // adiciona a celula no valor a direita da cabeça
        aux = head.getRight();
        for (int i = 1; i < columns; i++) {
            aux.setRight(new Node()); // cria uma nova celula a direita da ultima celula criadaa
            aux.getRight().setColumn(i);
            aux = aux.getRight(); // pega a celula a direita da ultima celula criada
        }

        // cria as sentinelas das linhas
        aux = null;
        aux = new Node();
        aux.setRow(0);
        head.setDown(aux); // adiciona a celula no valor abaixo da cabeça
        aux = head.getDown();
        for (int i = 1; i < rows; i++) {
            aux.setDown(new Node()); // cria uma nova celula abaixo da ultima celula criada
            aux.getDown().setRow(i);
            aux = aux.getDown(); // pega a celula abaixo da ultima celula criada
        }
    }

}
