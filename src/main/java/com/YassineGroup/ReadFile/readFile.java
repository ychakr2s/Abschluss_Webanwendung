package com.YassineGroup.ReadFile;


import com.YassineGroup.Applications.Solve_Sudoku;
import com.YassineGroup.Graph.Graph;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Objects;

public class readFile {

    public readFile() {
    }

    public static void deleteFiles(String filename ){
        File myfile = new File(filename);

        if (myfile.isDirectory()) {
            if (Objects.requireNonNull(myfile.list()).length > 0) {
                Arrays.stream(Objects.requireNonNull(new File(filename).listFiles())).forEach(File::delete);
//                fileName = "";
            }
        }
    }

    /*
     * This method read a File and produce a Graph.
     */
    public Graph readGraph(String filename) {

        Path path = Paths.get(filename);
        Graph gr = null;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(String.valueOf(path)));
            String line = reader.readLine();
            while (line != null) {
                String[] splited = line.split("\\s+");
                if (splited[0].equals("p")) {
                    gr = new Graph(Integer.parseInt(splited[2]));
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

    /*
     * This method read a Sudoku File and produce from him a Sudoku File.
     */
    public Solve_Sudoku readGraphSudoku(String filename) {

        Path path = Paths.get(filename);
        Solve_Sudoku sd = new Solve_Sudoku();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(String.valueOf(path)));
            String line = reader.readLine();
            int vertex = 0;

            while (line != null) {
                for (int j = 0; j < line.length(); j++) {

                    if (Character.isDigit(line.charAt(j))) {
                        int color = Character.getNumericValue(line.charAt(j));
                        sd.setColor(vertex, color);
                        sd.setFixedColor(vertex);
                    }
                    vertex++;

                }
                line = reader.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return sd;
    }
}
