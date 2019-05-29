package com.YassineGroup.controller;

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
    String fileName = "";


    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/upload") // //new annotation since 4.3
    public String singleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {
        Graph gr = null;

        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            return "redirect:indexStatus";
        }

        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            Files.write(path, bytes);

            readFile rd = new readFile();
            fileName = path.toString();
//            gr = rd.readGraph(fileName);
//            System.out.println(gr.toString());

//            Greedy_Algorithm gree = new Greedy_Algorithm(gr);
//            gree.executeGraphAlgorithm();
//            fileName =path.toString();

            redirectAttributes.addFlashAttribute("message", "You successfully uploaded '" + file.getOriginalFilename() + "'");

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:/uploadStatus";
    }

    @RequestMapping(value = "/check", method = RequestMethod.POST)
    public @ResponseBody
    String yourMethod(@RequestBody ArrayList<String> selected) {
//        int k = 0;
//        String a[] = new String[selected.size()];
//
//        for (String data : selected) {
//            a[k] = data;
//            System.out.println(a[k]);
//            k++;
//        }
        readFile rd = new readFile();
        Graph gr = rd.readGraph(fileName);
        Context imp = new Context(FactoryAlgorithms.getAlgorithms(selected, gr));
        JsonOutput jso = new JsonOutput(gr, imp.execute());
        Gson gs = new Gson();
        String json = gs.toJson(jso);




        return json;
    }

    @GetMapping("/uploadStatus")
    public String uploadStatus() {
        return "indexStatus";
    }

}