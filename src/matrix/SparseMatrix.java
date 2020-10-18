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

    public Node getNodeByIndex(int row, int column) {
        return null;
    }

    /** Método para retorno de uma sentinela na posição (linha, coluna) passados por parâmetro.
     * @author Gabriel Felipe
     * @param row int - Linha da sentinela
     * @param column int - Coluna da sentinela
     * @return Node - Sentinela na posição informada
     */
    public Node getSentry(int row, int column) {
        Node sentry = null;
        if (row == -1) {
            sentry = head.getRight();
            while (sentry != null) { // percorre as sentinelas a direita da cabeça até encontrar a sentinela na posição desejada
                if (sentry.getColumn() == column)
                    return sentry;
                sentry = sentry.getRight();
            }
        } else if (column == -1) {
            sentry = head.getDown();
            while (sentry != null) { // percorre as sentinelas abaixo da cabeça até encontrar a sentinela na posição desejada
                if (sentry.getRow() == row)
                    return sentry;
                sentry = sentry.getDown();
            }
        }
        throw new IllegalArgumentException("Nenhuma sentinela foi encontrada na posição: [" + row + ", " + column + "]");
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
            aux.getRight().setRow(-1);
            aux = aux.getRight(); // pega a celula a direita da ultima celula criada
        }

        // cria as sentinelas das linhas
        aux = new Node();
        aux.setRow(0);
        head.setDown(aux); // adiciona a celula no valor abaixo da cabeça
        aux = head.getDown();
        for (int i = 1; i < rows; i++) {
            aux.setDown(new Node()); // cria uma nova celula abaixo da ultima celula criada
            aux.getDown().setRow(i);
            aux.getDown().setColumn(-1);
            aux = aux.getDown(); // pega a celula abaixo da ultima celula criada
        }
    }

    @Override
    public String toString() {
        StringBuilder matrix = new StringBuilder();

        for (int i = 0; i < rows; i++) {
            Node aux = getSentry(i , -1).getRight();
            while (aux != null) {
                matrix.append(aux.getValue() + " ");
                aux = aux.getRight();
            }
            matrix.append("\n");
        }

        return matrix.toString();
    }

}
