package com.YassineGroup.service.AbstractGraphColoring;

public class Algorithm {

    private String algorithm;
    private int numberColors;
    private int[] usedColors;
    private int[] coloredNodes;
    double time;

    public Algorithm(String name, int num, int[] colors, int[] coloredNodes, double time) {
        this.algorithm = name;
        this.numberColors = num;
        this.usedColors = colors;
        this.coloredNodes = coloredNodes;
        this.time= time;
    }
}






