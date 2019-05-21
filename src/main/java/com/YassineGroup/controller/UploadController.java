package com.YassineGroup.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class UploadController {

    public static String UPLOADED_FOLDER = "uploadingDir/";

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/upload") // //new annotation since 4.3
    public String singleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {

        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            return "redirect:indexStatus";
        }

        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            Files.write(path, bytes);

            redirectAttributes.addFlashAttribute("message", "You successfully uploaded '" + file.getOriginalFilename() + "'");

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:/uploadStatus";
    }
    @RequestMapping(value = "/editCustomer" , method = RequestMethod. POST)
    public void editCustomer(@RequestParam(value = "checkboxName", required = false) String checkboxValue)
    {
        if(checkboxValue != null)
        {
            System.out.println("checkbox is checked Yassssssss");
        }
        else
        {
            System.out.println("checkbox is not checked Yassssssss");
        }
    }

//    @PostMapping("/admin/rates/prices")
//    public String delete(@RequestParam("idChecked") List<String> idrates){
//
//        if(idrates != null){
//            for(String idrateStr : idrates){
//                int idrate = Integer.parseInt(idrateStr);
//                rateRepository.deleteRate(idrate);
//            }
//        }
//        return "redirect:/admin/rates/prices";
//    }

    @GetMapping("/uploadStatus")
    public String uploadStatus() {
        return "indexStatus";
    }

}