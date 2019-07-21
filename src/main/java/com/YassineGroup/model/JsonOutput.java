package com.YassineGroup.model;

import com.YassineGroup.service.AbstractGraphColoring.Algorithm;
import com.YassineGroup.service.Graph.Graph;

import java.util.ArrayList;
/*
 * In order to Output the result in Json-Format, i this class contains a Graph Object and algorithms-Objects
 */

public class JsonOutput {
    private Graph graph;
    private ArrayList<Algorithm> algorithms;

    public JsonOutput(Graph gr, ArrayList<Algorithm> algorithms) {
        this.graph = gr;
        this.algorithms = algorithms;
    }

    public String toString() {
        String rueckgabe = graph.toString();
        return rueckgabe;
    }
}
