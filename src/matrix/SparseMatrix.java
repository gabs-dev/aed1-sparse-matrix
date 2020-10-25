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

    public Node getNodeByIndex(int row, int column) {
        return null;
    }

    // essa função eu tenho arrumar, mas o resto eu acho que ta funcionando de boa
    // tenho que rotacionar e inserir borda ainda
    public void insert(int value, int row, int column) {

//        if (row >= this.rows || row < 0 || column >= this.columns || column < 0)
//            throw new ArrayIndexOutOfBoundsException("Posição da matriz inválida");
//        else {
//            Node sentinelRow = this.getSentinel(row, -1);
//            Node sentinelColumn = this.getSentinel(-1, column);
//            Node node = new Node(value, row, column);
//            Node aux = null, nodeColumn = null, nodeRow = null;
//
//            if (sentinelRow.getRight() == null && sentinelColumn.getDown() == null) {
//                sentinelRow.setRight(node);
//                sentinelColumn.setDown(node);
//            } else if (sentinelRow.getRight() != null && sentinelColumn.getDown() == null) {
//                aux = sentinelRow;
//
//                do {
//                    if (aux.getColumn() < column && (aux.getRight() != null && aux.getRight().getColumn() > column))
//                        break;
//                    nodeRow = aux;
//                    aux = aux.getRight();
//                } while (aux != null);
//
//                node.setRight(nodeRow.getRight());
//                nodeRow.setRight(node);
//                sentinelColumn.setDown(node);
//            } else if (sentinelRow.getRight() == null && sentinelColumn.getDown() != null) {
//                aux = sentinelColumn;
//
//                do {
//                    if (aux.getRow() < row && (aux.getDown() != null && aux.getDown().getRow() > row))
//                        break;
//                    nodeColumn = aux;
//                    aux = aux.getDown();
//                } while (aux != null);
//
//                node.setDown(nodeColumn.getDown());
//                nodeColumn.setDown(node);
//                sentinelRow.setRight(node);
//            } else if (sentinelRow.getRight() != null && sentinelColumn.getDown() != null) {
//                nodeColumn = null;
//                nodeRow = null;
//
//                aux = sentinelRow;
//                do {
//                    if (aux.getColumn() < column && (aux.getRight() != null && aux.getRight().getColumn() > column))
//                        break;
//                    nodeRow = aux;
//                    aux = aux.getRight();
//                } while (aux != null);
//
//                aux = sentinelColumn;
//                do {
//                    if (aux.getRow() < row && (aux.getDown() != null && aux.getDown().getRow() > row))
//                        break;
//                    nodeColumn = aux;
//                    aux = aux.getDown();
//                } while (aux != null);
//
//                if (nodeRow.getRight() != null)
//                    node.setRight(nodeRow.getRight());
//                if (nodeColumn.getDown() != null)
//                    node.setDown(nodeColumn.getDown());
//                nodeRow.setRight(node);
//                nodeColumn.setDown(node);
//            }
//
//        }

        if (row >= this.rows && row < 0 || column >= this.columns && column < 0)
            throw new ArrayIndexOutOfBoundsException("Posição da matriz inválida");

        Node node = new Node(value, row, column);
        Node aux = null;

        Node sentinelRow = getSentinel(row, -1);
        Node sentinelColumn = getSentinel(-1, column);

        if (sentinelRow.getRight() != null && sentinelColumn.getDown() != null) {
            aux = sentinelRow.getRight();
            Node previousNode = null;
            do {
                previousNode = aux;
                aux = aux.getRight();
            } while(aux != null);

            aux = sentinelColumn.getDown();
            Node nodeAbove = null;
            do {
                nodeAbove = aux;
                aux = aux.getDown();
            } while(aux != null);

            previousNode.setRight(node);
            nodeAbove.setDown(node);
        }

        if (sentinelRow.getRight() == null && sentinelColumn.getDown() == null) {
            sentinelRow.setRight(node);
            sentinelColumn.setDown(node);
        }

        if (sentinelRow.getRight() != null && sentinelColumn.getDown() == null) {
            aux = sentinelRow.getRight();
            Node previousNode = null;
            do {
                previousNode = aux;
                aux = aux.getRight();
            } while(aux != null);

            previousNode.setRight(node);
            sentinelColumn.setDown(node);
        }

        if (sentinelRow.getRight() == null && sentinelColumn.getDown() != null) {
            aux = sentinelColumn.getDown();
            Node nodeAbove = null;
            do {
                nodeAbove = aux;
                aux = aux.getDown();
            } while(aux != null);

            sentinelRow.setRight(node);
            nodeAbove.setDown(node);
        }

        this.size++;
    }

    public int delete(int row, int column) {
        Node sentinelRow = this.getSentinel(row, -1), sentinelColumn = this.getSentinel(-1, column),
                nodeRow = null, nodeColumn = null;
        if (sentinelRow.getRight() != null && sentinelColumn.getDown() != null) {
            do {
                nodeRow = sentinelRow;
                sentinelRow = sentinelRow.getRight();
                if (sentinelRow!= null && sentinelRow.getColumn() == column)
                    break;
            } while (sentinelRow != null);
            do {
                nodeColumn = sentinelColumn;
                sentinelColumn = sentinelColumn.getRight();
                if (sentinelColumn != null && sentinelColumn.getRow() == row)
                    break;
            } while(sentinelColumn != null);

            if (sentinelRow != null)
                nodeRow.setRight(sentinelRow.getRight());
            if (sentinelColumn != null)
                nodeColumn.setDown(sentinelColumn.getDown());

            this.size--;
            return sentinelRow.getValue();
        }

        return 0;
    }

    public void reverseColors(int maxValue) {
        if (maxValue < 0)
            throw new IllegalArgumentException("O valor maimo é inválido");
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

    public SparseMatrix rotateClockwise() {
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

    public void insertBorders(int width, int value) {
        this.borders(value, width, (this.rows - 1), 0, 0, (this.columns - 1));
    }

    /** Método para retorno de uma sentinela na posição (linha, coluna) passados por parâmetro.
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

    private Node getNodeAt(int row, int column) {
        if (isEmpty())
            throw new NullPointerException("A matriz está vazia");

        Node nodeRow = head.getDown(), node = null;
                //this.getSentinel(row, -1), node = null;
        do {
            node = nodeRow.getRight();
            do {
                if (node.getRow() == row && node.getColumn() == column)
                    return node;
                node = node.getRight();
            } while(node != null);
            nodeRow = nodeRow.getDown();
        } while(nodeRow != null);

//        do {
//            do {
//                if (node.getRow() == row && node.getColumn() == column) {
//                    return node;
//                }
//                node = node.getRight();
//            } while (node != null);
//            nodeRow = nodeRow.getDown();
//            node = nodeRow;
//        } while (nodeRow != null);

        return null;
    }

    private void createSentinels() {
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

    public int getValueAt(int row, int column) {
        if (this.getNodeAt(row, column) == null)
            return -1;
        else
            return this.getNodeAt(row, column).getValue();
    }

    private boolean isEmpty() {
        return (this.size == 0);
    }

    public long size() {
        return this.size;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.columns; j++) {
                Node aux = this.getNodeAt(i, j);
                if (aux == null)
                    sb.append("-");
                else if (aux != null)
                    sb.append(aux.getValue());
                sb.append("\t");
            }
            sb.append("\n");
        }

        return sb.toString();
    }

}
