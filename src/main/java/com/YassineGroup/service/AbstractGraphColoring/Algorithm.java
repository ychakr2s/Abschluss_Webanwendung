package com.YassineGroup.service.AbstractGraphColoring;

public class Algorithm {
    /*
     * This Class contains these Attributes to describe the Algorithm, which have to be outputed in Json-Format
     */
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
        this.time = time;
    }
}






