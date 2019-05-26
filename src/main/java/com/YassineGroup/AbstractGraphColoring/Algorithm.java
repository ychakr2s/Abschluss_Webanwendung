package com.YassineGroup.AbstractGraphColoring;

public class Algorithm {

    private String algorithm;
    private int numberColors;
    private int[] usedColors;
    private int[] coloredNodes;

    public Algorithm(String name, int num, int[] colors, int[] coloredNodes) {
        this.algorithm = name;
        this.numberColors = num;
        this.usedColors = colors;
        this.coloredNodes = coloredNodes;
    }
}






