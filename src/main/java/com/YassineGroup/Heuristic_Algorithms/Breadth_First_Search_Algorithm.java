package com.YassineGroup.Heuristic_Algorithms;

import com.YassineGroup.AbstractGraphColoring.Algorithm;
import com.YassineGroup.AbstractGraphColoring.GraphColoring;
import com.YassineGroup.Graph.Graph;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

public class Breadth_First_Search_Algorithm extends GraphColoring {

    // No. of vertices
    private boolean[] visited;
    private boolean[] available;
    private int[] resultColors;

    // Constructor
    public Breadth_First_Search_Algorithm(Graph g) {
        super(g);

        // Mark all the vertices as not visited(By default set as false)
        this.visited = new boolean[V];

        this.resultColors = new int[V];
        Arrays.fill(resultColors, -1);

        this.available = new boolean[V];
        Arrays.fill(available, true);
    }

    private void assignColor(int node) {
        int cr = findRightColor(graph, node, resultColors, available);

        // Assign the found color
        setColor(node, cr, resultColors);

        // Reset the values back to true for the next iteration
        Arrays.fill(available, true);
    }

    private void utilBFS(int s) {

        // Create a queue for BFS
        LinkedList<Integer> queue = new LinkedList<>();

        // Mark the current node as visited and enqueue it
        visited[s] = true;
        int c = 0;
        setColor(s, c, resultColors);

        queue.add(s);

        while (queue.size() != 0) {
            // Dequeue a vertex from queue and print it
            s = queue.poll();

            // Get all adjacent vertices of the dequeued vertex s.
            // If a adjacent has not been visited, then mark it visited and enqueue it.

            for (int n : graph.getEdges(s)) {
                if (!visited[n]) {
                    visited[n] = true;

                    // assign the uncolored Vertex the suitable Color
                    assignColor(n);
                    queue.add(n);
                }
            }
        }
    }

    private boolean isAllVisited(int x) {
        return visited[x];
    }

    @Override
    public Algorithm executeGraphAlgorithm() {
        Random rn = new Random();
        int chooseStart = rn.nextInt(V);
        utilBFS(chooseStart);
        for (int i = 0; i < V; i++) {

            if (!isAllVisited(i)) {
                utilBFS(i);
            }
        }
        printSolution();

        return new Algorithm("Breadth First Search", computeResultsColors(resultColors), usedColor(resultColors), resultColors);
    }

    @Override
    public void description() {
        System.out.println("This is the implementation of the Breadth First Search Algorithm ");
    }

    @Override
    public void printSolution() {
        description();
        printTest(resultColors, graph);
    }
}
