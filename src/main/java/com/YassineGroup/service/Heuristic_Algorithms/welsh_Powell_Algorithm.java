package com.YassineGroup.service.Heuristic_Algorithms;


import com.YassineGroup.service.AbstractGraphColoring.Algorithm;
import com.YassineGroup.service.AbstractGraphColoring.GraphColoring;
import com.YassineGroup.service.Graph.Graph;

import java.util.ArrayList;
import java.util.Arrays;

public class welsh_Powell_Algorithm extends GraphColoring {


    private int[] resultColors;

    /*
     * Constructor
     */
    public welsh_Powell_Algorithm(Graph graph) {
        super(graph);
        this.resultColors = new int[V];
        Arrays.fill(resultColors, -1);
    }

    /*
     * This Method sorted the Vertices descending according to the Vertex degree
     */
    private int[] sortDesc(int[] a) {
        int[] ret = new int[V];
        for (int i = 0; i < V; i++) {
            ret[i] = vertexHighstAdjDegree(a);
            a = remove(a, ret[i]);
        }
        return ret;
    }

    /*
     *  this method checks whether the passed vertex is adjacency to already colored vertices.
     */
    private boolean AdjToAlreadyColored(ArrayList<Integer> y, int ver) {
        int i = 0;
        while (i < y.size()) {
            if (graph.isEdges(y.get(i), ver)) {
                return true;
            }
            i++;
        }
        return false;
    }

    /*
     * this method removes the ALREADY COLORED VERTICES from the sorted array
     */
    private int[] removeColored(ArrayList<Integer> alreadyColor, int[] sortedArray) {
        int[] b = sortedArray;
        for (Integer integer : alreadyColor)
            b = remove(b, integer);
        return b;
    }

    @Override
    public Algorithm executeGraphAlgorithm() {
        double start = System.currentTimeMillis();

        // List the vertices in order of descending valence i.e.valence degree(v(i)) >= degree(v(i+1)) .
        int[] verts = sortDesc(graph.getVertices());
        // Colour the first vertex in the list.
        int color = 0;
        ArrayList<Integer> alreadyColored = new ArrayList<>();

        while (V > 0) {

            for (int v : verts) {
                /*
                 * Go down the sorted list and color every vertex not connected to the colored vertices above the same color
                 */
                if (!AdjToAlreadyColored(alreadyColored, v)) {
                    setColor(v, color, resultColors);
                    alreadyColored.add(v);
                    V--;
                }
            }
            // cross out all colored vertices in the list.
            verts = removeColored(alreadyColored, verts);
            alreadyColored.clear();

            /*
             * Repeat the process on the uncolored vertices with a new color-always until
             * all in descending order of degree until all vertices are colored.
             */
            color++;
        }

        double end = (System.currentTimeMillis() - start) / 1000; // 60 seconds * 1000 ms/sec
        return new Algorithm("Welsh-Powell Algorithm", computeResultsColors(resultColors), usedColor(resultColors), resultColors, end);
    }

    @Override
    public void description() {
        System.out.println("This is the implementation of the Welsh-Powell Algorithm ");
    }

    @Override
    public void printSolution() {
        description();
        printTest(resultColors, graph);
    }
}