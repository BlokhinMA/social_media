package com.example.demo.controllers;

import com.example.demo.services.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PhotoController {

    @Autowired
    private final PhotoService photoService;

    public PhotoController(PhotoService photoService) {
        this.photoService = photoService;
    }

    @GetMapping("/photo/{id}")
    public String photo(@PathVariable int id, Model model) {
        model.addAttribute("photo", photoService.show(id));
        return "photo";
    }

    @PostMapping("/add_photo_tags")
    public String addTags(String string, int photoId, Model model) {
        if (!photoService.addTags(string, photoId)) {
            model.addAttribute("condition", true);
            return "photo";
        }
        return "redirect:/photo/" + photoId;
    }

}
