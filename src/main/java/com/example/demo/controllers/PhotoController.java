package com.example.demo.controllers;

import com.example.demo.models.Photo;
import com.example.demo.models.PhotoComment;
import com.example.demo.models.PhotoTag;
import com.example.demo.services.PhotoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
    public String photo(@PathVariable int id, Principal principal, Model model) {
        model.addAttribute("photo", photoService.show(id));
        model.addAttribute("thisUser", principal);
        return "photo";
    }

    @ModelAttribute("photoTag")
    public PhotoTag photoTag() {
        return new PhotoTag();
    }

    @ModelAttribute("photoComment")
    public PhotoComment photoComment() {
        return new PhotoComment();
    }

    @PostMapping("/add_photo_tag")
    public String addPhotoTag(@Valid PhotoTag photoTag, BindingResult bindingResult, Principal principal, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("photo", photoService.show(photoTag.getPhotoId()));
            model.addAttribute("thisUser", principal);
            return "photo";
        }
        photoService.createTag(photoTag);
        return "redirect:/photo/" + photoTag.getPhotoId();
    }

    @PostMapping("/add_photo_comment")
    public String addPhotoComment(@Valid PhotoComment photoComment, BindingResult bindingResult, Principal principal, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("photo", photoService.show(photoComment.getPhotoId()));
            model.addAttribute("thisUser", principal);
            return "photo";
        }
        photoService.createComment(photoComment, principal);
        return "redirect:/photo/" + photoComment.getPhotoId();
    }

    @PostMapping("/delete_photo")
    public String deletePhoto(Photo photo) {
        if (!photoService.delete(photo))
            return "redirect:/my_albums";
        return "redirect:/album/" + photo.getAlbumId();
    }

    @PostMapping("/delete_photo_tag")
    public String deleteTag(PhotoTag photoTag) {
        if (!photoService.deleteTag(photoTag))
            return "redirect:/my_albums";
        return "redirect:/photo/" + photoTag.getPhotoId();
    }

    @PostMapping("/delete_photo_comment")
    public String deletePhotoComment(PhotoComment photoComment) {
        if (!photoService.deleteComment(photoComment))
            return "redirect:/my_albums";
        return "redirect:/photo/" + photoComment.getPhotoId();
    }

}
