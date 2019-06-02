package com.YassineGroup.factory;


import com.YassineGroup.service.AbstractGraphColoring.GraphColoring;
import com.YassineGroup.service.Exact_Algorithm.Backtracking;
import com.YassineGroup.service.Exact_Algorithm.Linear_Programming;
import com.YassineGroup.service.Graph.Graph;
import com.YassineGroup.service.Heuristic_Algorithms.*;

import java.util.ArrayList;
import java.util.Scanner;

/*
 * Factory generates objects of concrete class based on given information.
 */
public class FactoryAlgorithms {

    static int m;

    FactoryAlgorithms() {
    }

    FactoryAlgorithms(int m) {
        this.m = m;
    }

    public static ArrayList<GraphColoring> getAlgorithms(ArrayList<String> algorithms, Graph gr, int m) {
        ArrayList<GraphColoring> algorithm = new ArrayList<>();

        for (String algorithm1 : algorithms) {
            if (algorithm1.contains("Greedy")) {
                algorithm.add(new Greedy_Algorithm(gr));
            }
            if (algorithm1.contains("Tiefen")) {
                algorithm.add(new Depth_First_Search_Algorithm(gr));
            }
            if (algorithm1.equals("Recursive Largest First")) {
                algorithm.add(new Recursive_Largest_First_Algorithm(gr));
            }
            if (algorithm1.equals("Largest First")) {
                algorithm.add(new Largest_First_Algorithm(gr));
            }
            if (algorithm1.contains("Breiten")) {
                algorithm.add(new Breadth_First_Search_Algorithm(gr));
            }
            if (algorithm1.contains("DSATUR")) {
                algorithm.add(new dSatur_Algorithm(gr));
            }
            if (algorithm1.contains("Welsh")) {
                algorithm.add(new welsh_Powell_Algorithm(gr));
            }
            if (algorithm1.contains("Lineare")) {
                algorithm.add(new Linear_Programming(gr));
            }
            if (algorithm1.contains("Backtracking")) {
//                System.out.println("geben Sie fuer Backtracking Problem Anzahl der m Farben ein: ");
//                Scanner sc = new Scanner(System.in);
//                int m = sc.nextInt();
                algorithm.add(new Backtracking(gr, m));
            }
        }
        return algorithm;
    }
}
