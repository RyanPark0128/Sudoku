package model;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;


public class Matrix {
    private List<List<Cell>> m;
    private List<Integer> list;

    public Matrix() {
        list = new ArrayList<>();
        for (int i = 1; i <= 9; i++) {
            list.add(i);
        }


        m = new ArrayList<List<Cell>>();
        for (int i=0; i< 9; i++) {
            m.add(new ArrayList<Cell>());
        }

        for (int i=0; i< 9; i++) {
            for (int j=0; j<9; j++) {
                m.get(i).add(new Cell());
            }
        }
        generateMatrix(0, 0);
    }

    public boolean generateMatrix(int row, int col) {
        if (row == m.size()) {
            return true;
        }

        int nextRow = (col == m.size() - 1) ? row + 1 : row;
        int nextCol = (col == m.size() - 1) ? 0 : col + 1;
        Collections.shuffle(list);

        for (int num : list) {
            if (validation(row,col,num)) {
                m.get(row).get(col).setValue(num);
                if (generateMatrix(nextRow, nextCol)) {
                    return true;
                }
                m.get(row).get(col).setValue(0);
            }
        }

        return false;
    }

    public boolean validation(int row, int col, int value) {
        for (int i = 0 ; i < col; i++) {
            if (m.get(row).get(i).getValue() == value) {
                return false;
            }
        }

        for (int i=0; i < row; i++) {
            if (m.get(i).get(col).getValue() == value) {
                return false;
            }
        }

        int subGridRowStart = (row / 3) * 3;
        int subGridColStart = (col / 3) * 3;

        for (int i = subGridRowStart; i < subGridRowStart + 3; i++) {
            for (int j = subGridColStart; j < subGridColStart + 3; j++) {
                if (m.get(i).get(j).getValue() == value) {
                    return false;
                }
            }
        }

        return true;
    }

    public void printMatrix() {
        System.out.println();
        System.out.println("- - - - - - - - - - - - -");
        for (int i=0; i<m.size(); i++) {
            System.out.print("| ");
            for (int j=0; j< m.get(i).size(); j++) {
                System.out.print(m.get(i).get(j).getValue() + " ");
                if (j % 3 == 2) {
                    System.out.print("| ");
                }
            }
            System.out.println();
            if (i % 3 == 2) {
                System.out.println("- - - - - - - - - - - - -");
            }
        }
        System.out.println();
    }
}
