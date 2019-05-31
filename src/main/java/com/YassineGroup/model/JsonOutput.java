package com.YassineGroup.model;


import com.YassineGroup.service.AbstractGraphColoring.Algorithm;
import com.YassineGroup.service.Graph.Graph;

import java.util.ArrayList;

public class JsonOutput {
    private Graph graph;
    private ArrayList<Algorithm> algorithms;

    public JsonOutput(Graph gr, ArrayList<Algorithm> algorithms) {
        this.graph = gr;
        this.algorithms = algorithms;
    }

    public String toString() {
        String rueckgabe = graph.toString();
//        rueckgabe+= algorithms.
        return rueckgabe;
    }
}
