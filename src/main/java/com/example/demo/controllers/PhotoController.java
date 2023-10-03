package com.example.demo.controllers;

import com.example.demo.services.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.File;
import java.util.List;

@Controller
public class PhotoController {

    private final PhotoService photoService;

    @Autowired
    public PhotoController(PhotoService photoService) {
        this.photoService = photoService;
    }

    @GetMapping("/photos")
    public String photos() {
        return "photos";
    }

    @PostMapping("/create_photos")
    public String createPhotos(List<File> files, Model model) {
        if (!photoService.addPhotos(files)) {
            model.addAttribute("condition", true);
            return "/photos";
        }
        return "redirect:/photos";
    }

}
