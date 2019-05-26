package com.YassineGroup.model;


import com.YassineGroup.AbstractGraphColoring.Algorithm;
import com.YassineGroup.Graph.Graph;

import java.util.ArrayList;

public class JsonOutput {
    private Graph graph;
    private ArrayList<Algorithm> algorithms;

    JsonOutput(Graph gr, ArrayList<Algorithm> algorithms) {
        this.graph = gr;
        this.algorithms = algorithms;
    }
}
