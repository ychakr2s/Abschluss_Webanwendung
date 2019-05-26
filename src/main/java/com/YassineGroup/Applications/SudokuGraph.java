package com.YassineGroup.Applications;


import com.YassineGroup.Graph.Graph;

import java.util.HashSet;
import java.util.Iterator;

public class SudokuGraph extends Graph {

    public SudokuGraph() {
        super(81);
        constructSudoku();
    }

    private void constructSudoku() {

        int col = 3;
        int temp = 19;
        int countSquare = 1;
        HashSet[] tmp = new HashSet[81];

        for (int i = 0; i < tmp.length; i++) {
            tmp[i] = new HashSet();
        }

        // bind each node to another nodes in the same Block.
        for (int square = 0; square < 81; square = square + 3) {
            if (square / 9 == countSquare) {
                countSquare = countSquare + 3;
                square = square + 18;
                col = square + 3;
                temp = temp + 18;
            }

            if (square == 81) {
                break;
            }

            for (int j = square; j < col; j++) {
                for (int j2 = j; j2 < temp; j2 = j2 + 9) {
                    tmp[j].add(j2);
                }
                temp++;
            }
            col = col + 3;
            int start = square;
            int end = start + 3;
            for (int i = start; i < end; i++) {
                Iterator<Integer> it = tmp[i].iterator();

                while (it.hasNext()) {
                    int x = it.next();
                    for (int j1 = start; j1 < end; j1++) {
                        Iterator<Integer> ite = tmp[j1].iterator();
                        while (ite.hasNext()) {
                            int y = ite.next();
                            addEdge(x, y);
                        }
                    }
                }
            }
        }

        // bind each node to another nodes in the same row and column.
        for (int v = 0; v < getNumVertices(); v++) {

            int row = Math.abs(v - v % 9);
            int limit = Math.abs(v - v % 9) + 9;
            for (; row < limit; row++) {
                addEdge(v, row);
            }

            for (int column = v % 9; column < 81; column = column + 9) {
                addEdge(v, column);
            }
        }
    }
}
