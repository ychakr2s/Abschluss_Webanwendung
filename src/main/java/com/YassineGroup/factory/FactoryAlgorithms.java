package com.YassineGroup.factory;


import com.YassineGroup.AbstractGraphColoring.GraphColoring;
import com.YassineGroup.Exact_Algorithm.Backtracking;
import com.YassineGroup.Exact_Algorithm.Linear_Programming;
import com.YassineGroup.Graph.Graph;
import com.YassineGroup.Heuristic_Algorithms.*;

import java.util.ArrayList;
import java.util.Scanner;

/*
 * Factory generates objects of concrete class based on given information.
 */
public class FactoryAlgorithms {

    FactoryAlgorithms() {
    }

    public static ArrayList<GraphColoring> getAlgorithms(ArrayList<String> algorithms, Graph gr) {
        ArrayList<GraphColoring> algorithm = new ArrayList<>();

        for (String algorithm1 : algorithms) {
            if (algorithm1.contains("Greedy")) {
                algorithm.add(new Greedy_Algorithm(gr));
            }
            if (algorithm1.contains("Depth")) {
                algorithm.add(new Depth_First_Search_Algorithm(gr));
            }
            if (algorithm1.contains("Recursive")) {
                algorithm.add(new Recursive_Largest_First_Algorithm(gr));
            }
            if (algorithm1.contains("Largest")) {
                algorithm.add(new Largest_First_Algorithm(gr));
            }
            if (algorithm1.contains("Breadth")) {
                algorithm.add(new Breadth_First_Search_Algorithm(gr));
            }
            if (algorithm1.contains("dSatur")) {
                algorithm.add(new dSatur_Algorithm(gr));
            }
            if (algorithm1.contains("welsh")) {
                algorithm.add(new welsh_Powell_Algorithm(gr));
            }
            if (algorithm1.contains("Linear")) {
                algorithm.add(new Linear_Programming(gr));
            }
            if (algorithm1.contains("Backtracking")) {
                System.out.println("geben Sie fuer Backtracking Problem Anzahl der m Farben ein: ");
                Scanner sc = new Scanner(System.in);
                int m = sc.nextInt();
                algorithm.add(new Backtracking(gr, m));
            }
        }
        return algorithm;
    }
}
