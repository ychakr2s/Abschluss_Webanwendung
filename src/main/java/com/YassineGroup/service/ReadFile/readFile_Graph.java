package com.YassineGroup.service.ReadFile;


import com.YassineGroup.service.Applications.Solve_Sudoku;
import com.YassineGroup.service.Graph.Graph;
import com.google.gson.Gson;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

public class readFile_Graph {

    public int edges;

    public readFile_Graph() {
    }

    public static void deleteFiles(String filename) {
        File myfile = new File(filename);

        if (myfile.isDirectory()) {
            if (Objects.requireNonNull(myfile.list()).length > 0) {
                Arrays.stream(Objects.requireNonNull(new File(filename).listFiles())).forEach(File::delete);
            }
        }
    }

    /*
     * This method read a File and produce a Graph.
     */
    public Graph dimacsToGraph(String filename) {
        Path path = Paths.get(filename);
        Graph gr = null;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(String.valueOf(path)));
            String line = reader.readLine();
            while (line != null) {
                String[] splited = line.split("\\s+");
                if (splited[0].equals("p")) {
                    gr = new Graph(Integer.parseInt(splited[2]));
                    gr.setEdge(Integer.parseInt(splited[3]));
                    gr.computeDensity();
                }
                if (splited[0].equals("e")) {
                    assert gr != null;
                    gr.addEdge(Integer.parseInt(splited[1]) - 1, Integer.parseInt(splited[2]) - 1);
                }
                line = reader.readLine();
            }
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return gr;
    }

    public Graph self_generated(String filename) {

        Path path = Paths.get(filename);
        Graph gr = null;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(String.valueOf(path)));
            String line = reader.readLine();
            while (line != null) {
                String[] splited = line.split("\\s+");
                if (splited[0].equals("p")) {
                    gr = new Graph(Integer.parseInt(splited[1]));
                    gr.setEdge(Integer.parseInt(splited[2]));
                    gr.computeDensity();
                } else if (splited[0].equals("")) {
                    break;
                } else {
                    assert gr != null;
                    gr.addEdge(Integer.parseInt(splited[0]), Integer.parseInt(splited[1]));
                }

                line = reader.readLine();
            }
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return gr;
    }

    /*
     * this method get a Json file and parse it to Java. It is parsed to Graph.
     */
    public Graph jsonToGraph(String filename) {

        Gson gson = new Gson();
        Graph gr = new Graph(0);

        try {
            BufferedReader br = new BufferedReader(
                    new FileReader(filename));

            //convert the json string back to object
            Graph graph = gson.fromJson(br, Graph.class);

            gr = new Graph(graph.getNumVertices());

            for (int i = 0; i < graph.getNumVertices(); i++) {
                Iterator itr = graph.getEdges(i).iterator();
                while (itr.hasNext()) {

                    double a = (double) itr.next();
                    int c = (int) a;
                    gr.addEdge(i, c);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return gr;
    }

    /*
     * This method read the Graph and decide which method of production is suitable for it
     */
    public Graph readByMethode(String filename) {

        Path path = Paths.get(filename);
        Graph gr = null;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(String.valueOf(path)));
            String line = reader.readLine();
            while (line != null) {
//                String[] splited = line.split("\\s+");
                String[] splited = line.split("\\s+");
                if (splited[0].equals("p")) {
                    while (line != null) {
                        splited = line.split("\\s+");
                        if (splited[0].equals("e")) {
                            gr = dimacsToGraph(filename);
                            return gr;
                        } else if (isNumeric(splited[0])) {
                            gr = self_generated(filename);
                            return gr;
                        }
                        line = reader.readLine();
                    }
                }
                line = reader.readLine();
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return gr;
    }

    /*
     * This Method tests wether the Graph-File is suitable to implement algorithms on it.
     */
    public boolean isSuitable(String filename) throws IOException {

        Path path = Paths.get(filename);

        BufferedReader reader = new BufferedReader(new FileReader(String.valueOf(path)));
        String line = reader.readLine();
        while (line != null) {
            String[] splited = line.split("\\s+");
            if (splited[0].equals("c")) {
                while (line != null) {
                    splited = line.split("\\s+");
                    if (!splited[0].equals("c")) {
                        if (!splited[0].equals("p")) {
                            while (line != null) {
                                splited = line.split("\\s+");

                                if (!splited[0].equals("e")) {
                                    return false;
                                }
                                line = reader.readLine();
                            }
                        }
                    }
                    line = reader.readLine();
                }
                reader.close();
            } else if (splited[0].equals("p")) {
                line = reader.readLine();
                while (line != null) {
                    splited = line.split("\\s+");
                    if (!isNumeric(splited[0])) {
                        return false;
                    }
                    line = reader.readLine();
                }
            }
        }
        return true;
    }

    /*
     * This method read a Sudoku File and produce from him a Sudoku File.
     */
    public static Solve_Sudoku readGraphSudoku(int[] arr) {
        Solve_Sudoku sd = new Solve_Sudoku();
        int vertex = 0;

        int j = 0;
        while (j < arr.length) {
            if (arr[j] != 0) {
                int color = arr[j];
                sd.setColor(vertex, color);
                sd.setFixedColor(vertex);
            }
            vertex++;
            j++;
        }
        return sd;
    }

    private static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            double a = Double.parseDouble(str);
            return !(a < 0);
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
