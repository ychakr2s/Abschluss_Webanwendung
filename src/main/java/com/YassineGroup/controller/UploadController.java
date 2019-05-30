package com.YassineGroup.controller;

import com.YassineGroup.Applications.Solve_Sudoku;
import com.YassineGroup.Graph.Graph;
import com.YassineGroup.factory.FactoryAlgorithms;
import com.YassineGroup.model.Context;
import com.YassineGroup.ReadFile.readFile;
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
public class UploadController {

    public static String UPLOADED_FOLDER = "uploadingDir/";
    private String fileName = "";


    @GetMapping("/")
    public String index() {
        readFile.deleteFiles(UPLOADED_FOLDER);
        fileName = "";

        return "index";
    }

    @PostMapping("/upload") //new annotation since 4.3
    public String singleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            return "redirect:indexStatus";
        }

        try {
            /*
             * This checks whether a directory contains a file or not. If yes than delete everything and add
             * the new File to the directory.
             */
            readFile.deleteFiles(UPLOADED_FOLDER);

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

    @RequestMapping(value = "/check", method = RequestMethod.POST)
    public @ResponseBody
    String yourMethod(@RequestBody ArrayList<String> selected) {

        if (fileName.equals("")) {
            return "Bitte laden Sie eine Graph-Datei hoch";
        } else {
            readFile rd = new readFile();
            Graph gr = rd.readGraph(fileName);
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
        int k = 0;
        int[] a = new int[81];
        int[] b = new int[81];

        for (Integer data : dataArrayToSend) {
            a[k] = data;
            k++;
        }
        Solve_Sudoku sd = readFile.readGraphSudoku(a);
        String out = sd.executeGraphAlgorithm();
        System.out.println(out.toString());
        int count = 0;
        String[] splited = out.split("\\s+");
        for (int i = 0; i < 81; i++) {
//            if (out.charAt(count) != ' ') {
                b[i] = Integer.parseInt(splited[count]);
                System.out.println(b[i]);
//            }
            count++;
        }
//        for (int i = 0; i < 81; i++) {
////            if (out.charAt(count) != ' ') {
//            System.out.println(b[i]);
////            }
////            count++;
//        }

        return b;
    }

    @GetMapping("/uploadStatus")
    public String uploadStatus() {
        return "indexStatus";
    }

}