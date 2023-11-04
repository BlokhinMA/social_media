package com.example.demo.controllers;

import com.example.demo.models.PhotoComment;
import com.example.demo.services.PhotoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class PhotoController {

    @Autowired
    private final PhotoService photoService;

    public PhotoController(PhotoService photoService) {
        this.photoService = photoService;
    }

    @GetMapping("/photo/{id}")
    public String photo(@PathVariable int id, Model model, PhotoComment photoComment) {
        model.addAttribute("photo", photoService.show(id));
        return "photo";
    }

    @PostMapping("/add_photo_tags")
    public String addPhotoTags(String string, int photoId, Model model) {
        if (!photoService.createTags(string, photoId)) {
            model.addAttribute("condition", true);
            model.addAttribute("photo", photoService.show(photoId));
            return "photo";
        }
        return "redirect:/photo/" + photoId;
    }

    @PostMapping("/add_photo_comment")
    public String addPhotoComment(@Valid PhotoComment photoComment, BindingResult bindingResult, Principal principal, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("photo", photoService.show(photoComment.getPhotoId()));
            return "photo";
        }
        photoService.createComment(photoComment, principal);
        return "redirect:/photo/" + photoComment.getPhotoId();
    }

}
