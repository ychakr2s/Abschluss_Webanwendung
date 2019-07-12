package com.YassineGroup.service.Heuristic_Algorithms;


import com.YassineGroup.service.AbstractGraphColoring.Algorithm;
import com.YassineGroup.service.AbstractGraphColoring.GraphColoring;
import com.YassineGroup.service.Graph.Graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class Recursive_Largest_First_Algorithm extends GraphColoring {

    private int[] resultColors;

    // which contains uncoloured vertices that can currently be colored
    private int[] X;
    private int[] Y;
    // This variable represent whether a color available for this Vertex or not
    private boolean[] isColored;

    public Recursive_Largest_First_Algorithm(Graph g) {
        super(g);
        this.X = new int[0];
        this.Y = new int[0];

        this.resultColors = new int[V];
        Arrays.fill(resultColors, -1);

        // Initialize all vertices as unassigned
        this.isColored = new boolean[V];
        Arrays.fill(isColored, false);
    }

    private void colorVertex(int vertex, int c) {
        setColor(vertex, c, resultColors);
        isColored[vertex] = true;
    }

    /*
     * This method returns the Vertices which can not be colored
     */
    private int[] canbeColor(int[] ver, int v) {
        ArrayList<Integer> colo = new ArrayList<>();
        for (int i : ver) {
            if (!graph.isEdges(v, i) && i != v) {
                colo.add(i);
            }
        }

        return colo.stream().mapToInt(i -> i).toArray();
    }

    /*
     * This method returns the Vertices which can not be colored
     */
    private int[] cantBeColored(int[] a, int x) {
        HashSet<Integer> convert = arrayToHash(a);
        for (int i : graph.getEdges(x)) {
            if (!isColored[i]) {
                convert.add(i);

            }
        }
        return toArray(convert);
    }

    // This Method convert the Hashset to Array of integer
    private int[] toArray(HashSet<Integer> a) {
        int[] b = new int[a.size()];
        int j = 0;
        for (int i : a) {
            b[j] = i;
            j++;
        }
        return b;
    }

    private HashSet<Integer> arrayToHash(int[] a) {
        HashSet<Integer> con = new HashSet<>();
        for (int i1 : a) {
            con.add(i1);
        }
        return con;
    }

    @Override
    public Algorithm executeGraphAlgorithm() {
        double start = System.currentTimeMillis();
        // now i will produce Color for the uncolored Vertices

        X = graph.getVertices();
        int si = 0;
        while (V > 0) {
            while (X.length >= 1) {
                int vertex = vertexHighstAdjDegree(X);

                // assign a color to the Vertex x
                colorVertex(vertex, si);

                /*
                 * This Variable Y holds vertices, which can't be colored with Si
                 */
                Y = cantBeColored(Y, vertex);

                /*
                 * X Variable represents the Vertices, which can be colored with Si
                 */
                X = canbeColor(X, vertex);

                // by every Loop the Number of Vertices decreases by 1
                V--;

            }

            /*
             * Swap the X with Y. X represents the Variables which can be colored in next
             * Loop
             */
            int[] tmp = {};
            X = Y;
            Y = tmp;

            si++;
        }
//        printSolution();
        double end = (System.currentTimeMillis() - start) / 1000;
        return new Algorithm("RECURSIVE LARGEST FIRST", computeResultsColors(resultColors), usedColor(resultColors), resultColors, end);
    }

    @Override
    public void description() {
        System.out.println("This is the implementation of the RECURSIVE LARGEST FIRST ALGORITHM");
    }

    @Override
    public void printSolution() {
        description();
        printTest(resultColors, graph);
    }

}
