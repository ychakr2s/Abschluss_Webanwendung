package com.YassineGroup.service.Exact_Algorithm;

import com.YassineGroup.service.AbstractGraphColoring.Algorithm;
import com.YassineGroup.service.AbstractGraphColoring.GraphColoring;
import com.YassineGroup.service.Graph.Graph;

import java.util.Arrays;

public class Backtracking extends GraphColoring {
    private int m;
    private int[] resultColors;
    private double start = System.currentTimeMillis();
    private double end = start + 15 * 60 * 1000; // 60 seconds * 1000 ms/sec

    public Backtracking(Graph g, int m) {
        super(g);
        this.m = m;
        this.resultColors = new int[V];
        Arrays.fill(resultColors, -1);
    }

    /*
     * A utility function to check if the current color assignment is safe for vertex v
     */
    private boolean isSafe(int v, int c) {
        for (int edge : graph.getEdges(v)) {
            if (getColor(edge, resultColors) == c)
                return false;
        }
        return true;
    }

    /*
     * A recursive utility function to solve m coloring problem
     */
    private boolean graphColoringUtil(int v) {

        /*
         * base case: If all vertices are assigned a color then return true
         */
        if (v == V)
            return true;
        else if (System.currentTimeMillis() > end) {
            return false;
        }

        /*
         * Consider this vertex v and try different colors
         */
        for (int c = 0; c < m; c++) {
            /*
             * Check if assignment of color c to v is fine
             */
            if (isSafe(v, c)) {
                setColor(v, c, resultColors);
                /*
                 * recur to assign colors to rest of the vertices
                 */
                if (graphColoringUtil(v + 1))
                    return true;
                /*
                 * If no color can be assigned to this vertex then return false
                 */
                setColor(v, -1, resultColors);
            }
        }
        return false;
    }

    /*
     * This function solves the m Coloring problem using Backtracking.
     * It mainly uses graphColoringUtil() to solve the problem.
     * It returns false if the m colors cannot be assigned, otherwise return true and prints the JsonOutput.
     */
    @Override
    public Algorithm executeGraphAlgorithm() {
//        long start = System.currentTimeMillis();
        if (graphColoringUtil(0)) {
            printSolution();
        } else
            System.out.println("the JsonOutput does not exists");
        double ende = (System.currentTimeMillis() - start) / 1000;

        return new Algorithm("Backtracking", computeResultsColors(resultColors), usedColor(resultColors), resultColors, ende);
    }


    @Override
    public void description() {
        System.out.println("This is the implementation of the Backtracking algorithm \n" +
                "The JsonOutput exists: +++++++++++++");
    }

    @Override
    public void printSolution() {
        description();
        printTest(resultColors, graph);
    }
}
