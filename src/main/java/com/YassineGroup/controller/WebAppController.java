package com.YassineGroup.controller;

import com.YassineGroup.service.Applications.Solve_Sudoku;
import com.YassineGroup.service.Graph.Graph;
import com.YassineGroup.factory.FactoryAlgorithms;
import com.YassineGroup.model.Context;
import com.YassineGroup.service.ReadFile.readFile_Graph;
import com.YassineGroup.model.JsonOutput;
import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

@Controller
public class WebAppController {

    public static String UPLOADED_FOLDER = "uploadingDir/";
    private String fileName = "";
    private int m;

    @GetMapping("/")
    public String index() {
        readFile_Graph.deleteFiles(UPLOADED_FOLDER);
        fileName = "";
        return "index";
    }

    @GetMapping("/implementierung")
    public String indexStatus() {
        readFile_Graph.deleteFiles(UPLOADED_FOLDER);
        fileName = "";

        return "implementierung";
    }

    @GetMapping("/references")
    public String reference() {
        return "references";
    }

    @GetMapping("/impressum")
    public String impressum() {
        return "impressum";
    }

    @PostMapping("/upload") //new annotation since 4.3
    public String singleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            return "redirect:/uploadStatus";
        }
        if (file.getSize() > 1048576) {
            redirectAttributes.addFlashAttribute("message", "Die Datei ist zu groß: Die Datei muss die Größe 1048.576 kb nicht überschreiten.!!!");
            return "redirect:/uploadStatus";
        }

        try {
            /*
             * This checks whether a directory contains a file or not. If yes than delete everything and add
             * the new File to the directory.
             */
            readFile_Graph.deleteFiles(UPLOADED_FOLDER);

            // Upload the new File to the Directory "uploadingDir"
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            Files.write(path, bytes);

            fileName = path.toString();

            redirectAttributes.addFlashAttribute("message", "You successfully uploaded '" + file.getOriginalFilename() + "'");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/uploadStatus";
    }

    @RequestMapping(value = "/backtracking", method = RequestMethod.POST)
    public @ResponseBody
    void notAloneBacktracking(@RequestBody int selected) {
        this.m = selected;
        System.out.println(m);
    }
    @RequestMapping(value = "/graphZeigen", method = RequestMethod.POST)
    public @ResponseBody
    String graphAnzeigen(@RequestBody ArrayList<String> selected) throws IOException {

        if (fileName.equals("")) {
            return "Bitte laden Sie eine Graph-Datei hoch";
        } else {
            readFile_Graph rd = new readFile_Graph();

            if (!rd.isSuitable(fileName)) {
                return "Graph-Datei entspricht nicht den festgelegten Maßstäbe ";
            }
            Graph gr = rd.readByMethode(fileName);

            if (gr == null) {
                return "Die Datei konnte nicht eingelesen werden.!! Sehen Sie bitte oben die Anweisungen";
            }
//            System.out.println(m);
//            Context imp = new Context(FactoryAlgorithms.getAlgorithms(selected, gr));
//            imp.execute();
//            JsonOutput jso = new JsonOutput(gr, imp.execute());
            Gson gs = new Gson();

            return gs.toJson(gr);
        }
    }

    @RequestMapping(value = "/check", method = RequestMethod.POST)
    public @ResponseBody
    String yourMethod(@RequestBody ArrayList<String> selected) throws IOException {

        if (fileName.equals("")) {
            return "Bitte laden Sie eine Graph-Datei hoch";
        } else {
            readFile_Graph rd = new readFile_Graph();

            if (!rd.isSuitable(fileName)) {
                return "Graph-Datei entspricht nicht den festgelegten Maßstäbe ";
            }
            Graph gr = rd.readByMethode(fileName);

            if (gr == null) {
                return "Die Datei konnte nicht eingelesen werden.!! Sehen Sie bitte oben die Anweisungen";
            }
            System.out.println(m);
            Context imp = new Context(FactoryAlgorithms.getAlgorithms(selected, gr));
            imp.execute();
            JsonOutput jso = new JsonOutput(gr, imp.execute());
            Gson gs = new Gson();

            return gs.toJson(jso);
        }
    }

    @RequestMapping(value = "/appUrl", method = RequestMethod.POST)
    public @ResponseBody
    int[] sudoku(@RequestBody int[] dataArrayToSend) {

        Solve_Sudoku sd = readFile_Graph.readGraphSudoku(dataArrayToSend);

        String out = sd.executeGraphAlgorithm();
        String[] splited = out.split("\\s+");

        for (int i = 0; i < 81; i++) {
            dataArrayToSend[i] = Integer.parseInt(splited[i]);
        }

        return dataArrayToSend;
    }

    @GetMapping("/uploadStatus")
    public String uploadStatus() {
        return "indexStatus";
    }
}