package matrix;

public class SparseMatrix {

    private Node head;
    private int rows;
    private int columns;
    private long size = 0;

    public SparseMatrix() {
        head = new Node();
        head.setRow(-1);
        head.setColumn(-1);
        createSentinels();
    }

    public SparseMatrix(int rows, int columns) {
        head = new Node();
        head.setRow(-1);
        head.setColumn(-1);
        this.rows = rows;
        this.columns = columns;
        this.createSentinels();
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

    /**
     * Método que insere uma nova célula na matriz.
     * Percorre as linhas e a colunas até encontrar a posição onde a nova célula deve ser inserida.
     * @param value Valor que será inserido na célula
     * @param row Linha onde a célula será inserida
     * @param column Coluna onde a célula será inserida
     */
    public void insert(int value, int row, int column) {

        if (row >= this.rows || row < 0 || column >= this.columns || column < 0)
            throw new ArrayIndexOutOfBoundsException("A posição da matriz é inválida");

        Node node = new Node(value, row, column), nodeRow = getSentinel(row, -1),
                nodeColumn = getSentinel(-1, column), auxRow = null, auxColumn = null;

        while (nodeRow != null && nodeRow.getColumn() != node.getColumn()) {
            auxRow = nodeRow;
            nodeRow = nodeRow.getRight();
        }

        while (nodeColumn != null && nodeColumn.getRow() != node.getRow()) {
            auxColumn = nodeColumn;
            nodeColumn = nodeColumn.getDown();
        }

        if (nodeRow == null || nodeColumn == null) {
            auxRow.setRight(node);
            auxColumn.setDown(node);
        } else {
            auxRow.setRight(node);
            node.setRight(nodeRow.getRight());
            auxColumn.setDown(node);
            node.setDown(nodeColumn.getDown());
        }

        this.size++;
    }

    /**
     * Método que remove, se existir, uma célula na posição [linha, coluna] da matriz
     * @param row Linha onde está a célula que será excluída
     * @param column Coluna onde está a célula que será excluída
     */
    public void delete(int row, int column) {

        if (row >= rows || column >= columns) {
            throw new ArrayIndexOutOfBoundsException("Erro ao excluir elemento.");
        }
        if (isEmpty()) {
            throw new NullPointerException("Matriz vazia.");
        }
        if (getNodeAt(row, column) == null) {
            throw new NullPointerException("Elemento nulo.");
        }

        Node nodeRow = getSentinel(row, -1);
        Node previousRow = nodeRow;
        nodeRow = nodeRow.getRight();
        Node nodeColumn = getSentinel(-1, column);
        Node previousColumn = nodeColumn;
        nodeColumn = nodeColumn.getDown();
        while (nodeRow != null && nodeRow.getColumn() != column) {
            previousRow = nodeRow;
            nodeRow = nodeRow.getRight();
        }

        while (nodeColumn != null && nodeColumn.getRow() != row) {
            previousColumn = nodeColumn;
            nodeColumn = nodeColumn.getDown();
        }

        if (nodeRow == null || nodeColumn == null) {
            throw new IllegalArgumentException("Node nao encontrado.");
        }
        previousRow.setRight(nodeRow.getRight());
        previousColumn.setDown(nodeColumn.getDown());
        this.size--;

    }

    /**
     * Método que percorre todos os elementos da matriz e inverte os valores. A inversão é feita subtraindo do valor
     * maáximo o valor atual da célula.
     * @author Gabriel Felipe
     * @param maxValue Maior valor inserido na matriz
     */
    public void reverseColors(int maxValue) {
        if (isEmpty())
            throw new NullPointerException("A matriz está vazia");

        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.columns; j++) {
                Node node = getNodeAt(i, j);
                if (node == null)
                    this.insert(maxValue, i, j);
                else if (node.getValue() == maxValue)
                    this.delete(i, j);
                else {
                    int value = maxValue - node.getValue();
                    node.setValue(value);
                }
            }
        }
    }

    /**
     * Método que rotaciona a matriz atual 90° no sentido horário
     * @author Gabriel Felipe
     * @return Matriz esparsa rotacionada
     */
    public SparseMatrix rotateClockwise() {
        if (isEmpty()) {
            throw new NullPointerException("A matriz está vazia!");
        }
        SparseMatrix rotated = new SparseMatrix(this.getColumns(), this.getRows());
        int c;
        for (int i = 0; i < rotated.rows; i++) {
            c = rotated.columns;
            for (int j = 0; j < rotated.columns; j++) {
                c--;
                int value = getValueAt(c, i);
                if (value != -1)
                    rotated.insert(value, i, j);
            }
        }
        return rotated;
    }

    /**
     * Método recebe a largura e o valor da borda.
     * Chama outro método passando os valores necessários por parâmetro para que as bordas sejam inseridas.
     * @param width Largura da borda que será inseirda
     * @param value Valor a ser inserido nas bordas
     */
    public void insertBorders(int width, int value) {
        if (width < 0 || width >= (columns / 2) || width >= (rows / 2))
            throw new IllegalArgumentException("Tamanho de borda inválido");
        if (isEmpty())
            throw new NullPointerException("A matriz está vazia");
        this.borders(value, width, (this.rows - 1), 0, 0, (this.columns - 1));
    }

    /**
     * Método para retorno de uma sentinela na posição (linha, coluna) passados por parâmetro.
     * @author Gabriel Felipe
     * @param row int - Linha da sentinela
     * @param column int - Coluna da sentinela
     * @return Node - Sentinela na posição informada
     */
    private Node getSentinel(int row, int column) {
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

    /**
     * Método que retorna a célula localizada na posição [linha, coluna] da matriz
     * @param row Linha onde a célula se encontra
     * @param column Coluna onde a célula se encontra
     * @return A célula na posição [linha, coluna]
     */
    private Node getNodeAt(int row, int column) {
        if (isEmpty())
            throw new NullPointerException("A matriz está vazia");

        Node node = this.head;
        while (node != null && node.getRow() != row)
            node = node.getDown();

        if (node != null) {
            while (node != null && node.getColumn() != column)
                node = node.getRight();
            return node;
        }

        return null;
    }

    /**
     * Método que cria todas as sentinelas de linha e colula da matriz utilizando a célula cabeça.
     */
    private void createSentinels() {
        // cria as sentinelas das colunas
        Node aux = head;
        for (int i = 0; i < columns; i++) {
            aux.setRight(new Node(-1, i));
            aux = aux.getRight(); // pega a sentinela a direita da ultima celula criada
        }

        // cria as sentinelas das linhas
        aux = head;
        for (int i = 0; i < rows; i++) {
            aux.setDown(new Node(i, -1)); // cria uma nova sentinela abaixo da ultima celula criada
            aux = aux.getDown(); // pega a sentinela abaixo da ultima celula criada
        }
    }

    /**
     * Método que chama a si mesmo e vai inserindo a borda pixel por pixel até que o tamanho da borda seja atingido
     * @param value Valor que será inserido nas bordas
     * @param width Tamanho da borda
     * @param bottomEdge Linha onde a borda inferior será inserida
     * @param topEdge Linha onde a borda superior será inserida
     * @param leftEdge Coluna onde a borda esquerda será inserida
     * @param rightEdge Coluna onde a borda direita será inserida
     */
    private void borders(int value, int width, int bottomEdge, int topEdge, int leftEdge, int rightEdge) {
        if (topEdge < width) {
            for (int i = leftEdge; i <= rightEdge; i++) {
                this.insert(value, topEdge, i);
                this.insert(value, bottomEdge, i);
            }
            for (int i = (topEdge + 1); i < bottomEdge; i++) {
                this.insert(value, i, leftEdge);
                this.insert(value, i, rightEdge);
            }
            this.borders(value, width, (bottomEdge - 1), (topEdge + 1), (leftEdge + 1), (rightEdge - 1));
        }
    }

    /**
     * Método retorna o valor da celula na posição [linha, coluna] da matriz.
     * Caso a célula não exista, retorna -1
     * @param row Linha em que se encontra a célula
     * @param column Coluna em que se encontra a célula
     * @return int - Valor da célula na posição [linha, coluna]
     */
    public int getValueAt(int row, int column) {
        if (this.getNodeAt(row, column) == null)
            return -1;
        else
            return this.getNodeAt(row, column).getValue();
    }

    /**
     * Método verifica se a matriz está vazia.
     * @return boolean - true se a matriz estiver vazia, caso contrário false.
     */
    private boolean isEmpty() {
        return (this.size == 0);
    }

    /**
     * Método que retorna o tamanho da matriz com base na quatidade de células inseridas
     * @return long - Tamanho da matriz
     */
    public long size() {
        return this.size;
    }

    /**
     * Método que retorna uma String para criar um arquivo pgm da matriz atual
     * @param maxValue Maior valor inserido na matriz
     * @return String com as informações necessárias para criar um arquivo pgm
     */
    public String generatePgmString(int maxValue) {
        StringBuilder pgm = new StringBuilder();
        pgm.delete(0, pgm.length());
        pgm.append("P2\n");
        pgm.append(this.columns).append(" ").append(this.rows).append("\n");
        pgm.append(maxValue).append("\n");
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < columns; j++) {
                Node node = this.getNodeAt(i, j);
                if (node == null)
                    pgm.append("0");
                else
                    pgm.append(node.getValue());
                pgm.append(" ");
            }
            pgm.append("\n");
        }
        return pgm.toString();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.columns; j++) {
                Node aux = this.getNodeAt(i, j);
                if (aux == null)
                    sb.append("0");
                else if (aux != null)
                    sb.append(aux.getValue());
                sb.append("\t");
            }
            sb.append("\n");
        }

        return sb.toString();
    }

}
