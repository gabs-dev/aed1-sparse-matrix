package controller;

import matrix.Node;
import matrix.SparseMatrix;

public class SparseMatrixController {

    private SparseMatrix matrix;

    public SparseMatrixController(SparseMatrix matrix) {
        this.matrix = matrix;
    }

    public void createMatrix(int rows, int columns) {
        matrix = new SparseMatrix(rows, columns);
    }

//    public void insert(int value, int row, int column) {
//        Node node = new Node(value, null, null, row, column);
//        Node aux = null;
//
//        Node sentryRow = matrix.getSentry(row, -1);
//        Node sentryColumn = matrix.getSentry(-1, column);
//
//        if (sentryRow.getRight() != null && sentryColumn.getDown() != null) {
//            aux = sentryRow.getRight();
//            Node previousNode = null;
//            do {
//                previousNode = aux;
//                aux = aux.getRight();
//            } while(aux != null);
//
//            aux = sentryColumn.getDown();
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
//        if (sentryRow.getRight() == null && sentryColumn.getDown() == null) {
//            sentryRow.setRight(node);
//            sentryColumn.setDown(node);
//        }
//
//        if (sentryRow.getRight() != null && sentryColumn.getDown() == null) {
//            aux = sentryRow.getRight();
//            Node previousNode = null;
//            do {
//                previousNode = aux;
//                aux = aux.getRight();
//            } while(aux != null);
//
//            previousNode.setRight(node);
//            sentryColumn.setDown(node);
//        }
//
//        if (sentryRow.getRight() == null && sentryColumn.getDown() != null) {
//            aux = sentryColumn.getDown();
//            Node nodeAbove = null;
//            do {
//                nodeAbove = aux;
//                aux = aux.getDown();
//            } while(aux != null);
//
//            sentryRow.setRight(node);
//            nodeAbove.setDown(node);
//        }
//
//    }

    public String printMatrix() {
        return matrix.toString();
    }

}
