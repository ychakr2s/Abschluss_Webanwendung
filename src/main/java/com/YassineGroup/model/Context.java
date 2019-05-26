package com.YassineGroup.model;


import com.YassineGroup.AbstractGraphColoring.Algorithm;
import com.YassineGroup.AbstractGraphColoring.GraphColoring;

import java.util.ArrayList;

/*
 * The Context class doesn't implement an algorithm directly.
 * Instead, Context refers to the Strategy interface for performing an algorithm (algorithm.execute()),
 * which makes Context independent of how an algorithm is implemented.
 */
class Context {
    private ArrayList<GraphColoring> algorithms;

    Context(ArrayList<GraphColoring> algorithms) {
        this.algorithms = algorithms;
    }

    public ArrayList<Algorithm> execute() {
        ArrayList<Algorithm> alg = new ArrayList<>();
        //delegates behavior to Strategy object
        for (GraphColoring algorithm : algorithms) {
            alg.add(algorithm.executeGraphAlgorithm());
        }
        return alg;
    }

}
