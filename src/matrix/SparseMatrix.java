package matrix;

public class SparseMatrix {

    private Node head;
    private int rows;
    private int columns;

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

        if (row >= this.rows || row < 0 || column >= this.columns || column < 0)
            throw new ArrayIndexOutOfBoundsException("Posição da matriz inválida");
        else {
            Node sentinelRow = this.getSentinel(row, -1);
            Node sentinelColumn = this.getSentinel(-1, column);
            Node node = new Node(value, row, column);

            if (sentinelRow.getRight() != null || sentinelColumn.getDown() != null) {
                Node aux = null, nodeRow = null, nodeColumn = null;
                aux = sentinelRow;
                do {
                    if (aux.getColumn() < column && (aux.getRight() != null && aux.getRight().getColumn() > column))
                        break;
                    nodeRow = aux;
                    aux = aux.getRight();
                } while (aux != null);

                aux = sentinelColumn;
                do {
                    if (aux.getRow() < row && (aux.getDown() != null && aux.getDown().getRow() > row))
                        break;
                    nodeColumn = aux;
                    aux = aux.getDown();
                } while (aux != null);

                node.setRight(nodeRow.getRight());
                node.setDown(nodeColumn.getDown());
                nodeRow.setRight(node);
                nodeColumn.setDown(node);
            } else {
                if (sentinelRow.getRight() == null)
                    sentinelRow.setRight(node);
                if (sentinelColumn.getDown() == null)
                    sentinelColumn.setDown(node);
            }
        }

//        if (row >= this.rows && row < 0 || column >= this.columns && column < 0)
//            throw new ArrayIndexOutOfBoundsException("Posição da matriz inválida");
//
//        Node node = new Node(value, row, column);
//        Node aux = null;
//
//        Node sentinelRow = getSentinel(row, -1);
//        Node sentinelColumn = getSentinel(-1, column);
//
//        if (sentinelRow.getRight() != null && sentinelColumn.getDown() != null) {
//            aux = sentinelRow.getRight();
//            Node previousNode = null;
//            do {
//                previousNode = aux;
//                aux = aux.getRight();
//            } while(aux != null);
//
//            aux = sentinelColumn.getDown();
//            Node nodeAbove = null;
//            do {
//                nodeAbove = aux;
//                aux = aux.getDown();
//            } while(aux != null);
//
//            previousNode.setRight(node);
//            nodeAbove.setDown(node);
//        }
//
//        if (sentinelRow.getRight() == null && sentinelColumn.getDown() == null) {
//            sentinelRow.setRight(node);
//            sentinelColumn.setDown(node);
//        }
//
//        if (sentinelRow.getRight() != null && sentinelColumn.getDown() == null) {
//            aux = sentinelRow.getRight();
//            Node previousNode = null;
//            do {
//                previousNode = aux;
//                aux = aux.getRight();
//            } while(aux != null);
//
//            previousNode.setRight(node);
//            sentinelColumn.setDown(node);
//        }
//
//        if (sentinelRow.getRight() == null && sentinelColumn.getDown() != null) {
//            aux = sentinelColumn.getDown();
//            Node nodeAbove = null;
//            do {
//                nodeAbove = aux;
//                aux = aux.getDown();
//            } while(aux != null);
//
//            sentinelRow.setRight(node);
//            nodeAbove.setDown(node);
//        }
    }

    public void reverseColors(int maxValue) {
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.columns; j++) {
                Node node = getNodeAt(i, j);
                if (node == null) {
                    this.insert(maxValue, i, j);
                } else {
                    int value = maxValue - node.getValue();
                    node.setValue(value);
                }
            }
        }
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
        Node nodeRow = head.getDown();
        Node node = null;

        do {
            node = nodeRow.getRight();
            do {
                if (node.getRow() == row && node.getColumn() == column) {
                    return node;
                }
                node = node.getRight();
            } while (node != null);
            nodeRow = nodeRow.getDown();
        } while (nodeRow != null);

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
