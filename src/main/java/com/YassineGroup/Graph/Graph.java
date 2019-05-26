package com.YassineGroup.Graph;

import java.util.HashSet;
import java.util.Iterator;

public class Graph {

    // No. of vertices
    private int V;
    // Adjacency List
    private int[] vertices;
    private HashSet[] edges;

    /*
     * Constructor
     */
    public Graph(int v) {
        this.V = v;
        this.edges = new HashSet[V];

        for (int i = 0; i < V; ++i)
            edges[i] = new HashSet<>();

        this.vertices = new int[V];
        for (int i = 0; i < V; i++) {
            vertices[i] = i;
        }
    }

    /*
     * The first method returns the vertices of this Graph.
     * the second method returns the number of the vertices.
     * the third returns all edges of an vertex
     */
    public int[] getVertices() {
        return this.vertices;
    }

    public int getNumVertices() {
        return this.V;
    }

    public HashSet<Integer> getEdges(int v) {
        return this.edges[v];
    }

    /*
     * This Method adds Edges between two Vertices
     */
    public void addEdge(int from, int to) {
        if (to > getNumVertices() || from > getNumVertices())
            System.out.println("The vertices does not exists");
        else {
            if (from != to) {
                getEdges(from).add(to);
                getEdges(to).add(from);
            }
        }
    }

    /*
     * This method checks whether there is an Edge between two vertices
     */
    public boolean isEdges(int v1, int v2) {
        Iterator<Integer> it = getEdges(v1).iterator();
        while (it.hasNext()) {
            int v = it.next();
            if (v == v2)
                return true;
        }
        return false;
    }

    /*
     * This method returns the Degree of the intended Vertex
     */
    public int getVertexDegree(int v) {
        return getEdges(v).size();
    }

    /*
     * ----------------------- print methods ------------------------------
     */
    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append("+++++++++ This is the representation of the Graph as Linked List: +++++++++ \n");
        for (int v = 0; v < V; v++) {
            string.append(" Adjacency list of vertex ").append(v).append(" Edges of Vertex: ").append(v).append(":  ").append(v);

            for (Integer pCrawl : getEdges(v)) {

                string.append(" -> ").append(pCrawl);
            }
            string.append("\n");
        }
        return string.toString();
    }
}