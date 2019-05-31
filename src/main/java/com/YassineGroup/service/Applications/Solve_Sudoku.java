package com.YassineGroup.service.Applications;

public class Solve_Sudoku extends SudokuGraph {

    private int V;
    private int[] resultColors;
    private boolean[] fixedColor;

    public Solve_Sudoku() {
        this.V = getNumVertices();
        this.resultColors = new int[V];
        this.fixedColor = new boolean[V];
    }

    /*
     * A utility function to check if the current color assignment is safe for vertex v.
     */
    private boolean isSafe(int v, int c) {

        for (int vertex : getEdges(v)) {
            if (getColor(vertex) == c)
                return false;
        }
        return true;
    }

    /*
     * A recursive utility function to solve m coloring problem
     */
    private boolean graphColoringUtil(int v) {
        /*
         * base case: If all vertices are assigned a color then return true
         */
        if (v == getNumVertices())
            return true;

        if (fixedColor[v]) {
            return graphColoringUtil(v + 1);
        }

        /*
         * Consider this vertex v and try different colors
         */
        for (int c = 1; c <= 9; c++) {
            /*
             * Check if assignment of color c to v is fine
             */
            if (isSafe(v, c) && !fixedColor[v]) {
                setColor(v, c);

                /*
                 * recur to assign colors to rest of the vertices
                 */
                if (graphColoringUtil(v + 1))
                    return true;

                // if next vertex was not colorable. reset the color and try next one
                setColor(v, -1);
            }
        }

        /*
         * If no color can be assigned to this vertex then return false
         */
        return false;
    }

    public void setFixedColor(int v) {
        this.fixedColor[v] = true;
    }

    public void setColor(int vertex, int color) {
        this.resultColors[vertex] = color;
    }

    private int getColor(int vertex) {
        return this.resultColors[vertex];
    }

    public String executeGraphAlgorithm() {

        if (graphColoringUtil(0)) {
            System.out.println("JsonOutput exist");
            return toString();
        } else {
            System.out.println("the JsonOutput does not exists");
            return "There is an Input Error";
        }

    }

    public String toString() {
        StringBuilder string = new StringBuilder();

        int j = 0;
        for (int i = 0; i <= V - 9; i = i + 9) {
            for (; j < i + 9; j++) {
                string.append(getColor(j)).append(" ");
            }
            string.append("\n");
        }
        return string.toString();
    }

}
