package com.YassineGroup.Heuristic_Algorithms;


import com.YassineGroup.AbstractGraphColoring.Algorithm;
import com.YassineGroup.AbstractGraphColoring.GraphColoring;
import com.YassineGroup.Graph.Graph;

import java.util.Arrays;
import java.util.Random;

public class Depth_First_Search_Algorithm extends GraphColoring {

    private boolean[] visited;
    private boolean[] alreadyColored;
    private boolean[] available;
    private int[] resultColors;

    // Constructor
    public Depth_First_Search_Algorithm(Graph g) {
        super(g);

        this.resultColors = new int[V];
        Arrays.fill(resultColors, -1);

        // Mark all vertices as not colored (By default set as false)
        this.alreadyColored = new boolean[V];

        // Mark all the vertices as not visited(By default set as false)
        this.visited = new boolean[V];

        this.available = new boolean[V];
        Arrays.fill(available, true);
    }

    private boolean isSafe(int v) {
        return !alreadyColored[v];
    }

    private void colorVertex(int vertex, int c) {
        setColor(vertex, c, resultColors);
        alreadyColored[vertex] = true;
    }

    private void assignColor(int node) {
        int cr = findRightColor(graph, node, resultColors, available);
        // Assign the found color
        colorVertex(node, cr);
        // Reset the values back to true for the next iteration
        Arrays.fill(available, true);
    }

    // A helper function used by DFS
    private void DFSUtil(int v, boolean[] visited) {
        // Mark the current node as visited
        visited[v] = true;
        if (isSafe(v)) {
            assignColor(v);
        }

        // Recur for all the vertices adjacent to this vertex
        for (int n : graph.getEdges(v)) {
            if (!visited[n]) {
                assignColor(n);
                DFSUtil(n, visited);
            }
        }
    }

    private boolean isAllVisited(int vertex) {
        return visited[vertex];
    }

    @Override
    public Algorithm executeGraphAlgorithm() {
        Random rn = new Random();
        int chooseStart = rn.nextInt(V);
        // Call the recursive helper function to print DFS traversal
        DFSUtil(chooseStart, visited);

        for (int i = 0; i < V; i++) {
            if (!isAllVisited(i)) {
                DFSUtil(i, visited);
            }
        }
        printSolution();

        return new Algorithm("DEPTH FIRST SEARCH", computeResultsColors(resultColors), usedColor(resultColors), resultColors);
    }

    @Override
    public void description() {
        System.out.println("This is the implementation of the DEPTH FIRST SEARCH ");
    }

    @Override
    public void printSolution() {
        description();
        printTest(resultColors, graph);
    }
}
