package com.example.demo.controllers;

import com.example.demo.models.Photo;
import com.example.demo.models.PhotoComment;
import com.example.demo.models.PhotoRating;
import com.example.demo.models.PhotoTag;
import com.example.demo.services.PhotoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class PhotoController {

    private final PhotoService photoService;

    @GetMapping("/photo/{id}")
    public String photo(@PathVariable int id, Principal principal, Model model) {
        model
                .addAttribute("photo", photoService.show(id, principal))
                .addAttribute("thisUser", principal)
                .addAttribute("isFriend", photoService.isFriend(principal, photoService.show(id, principal).getAlbum().getUserLogin()));
        return "photo";
    }

    @PostMapping("/delete_photo")
    public String deletePhoto(Photo photo) {
        if (!photoService.delete(photo))
            return "redirect:/my_albums";
        return "redirect:/album/" + photo.getAlbumId();
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
            model
                    .addAttribute("photo", photoService.show(photoTag.getPhotoId(), principal))
                    .addAttribute("thisUser", principal);
            return "photo";
        }
        if (!photoService.createTag(photoTag)) {
            model
                    .addAttribute("photo", photoService.show(photoTag.getPhotoId(), principal))
                    .addAttribute("thisUser", principal)
                    .addAttribute("condition", true);
            return "photo";
        }
        return "redirect:/photo/" + photoTag.getPhotoId();
    }

    @PostMapping("/delete_photo_tag")
    public String deleteTag(PhotoTag photoTag) {
        if (!photoService.deleteTag(photoTag))
            return "redirect:/my_albums";
        return "redirect:/photo/" + photoTag.getPhotoId();
    }

    @PostMapping("/add_photo_rating")
    public String addPhotoRating(PhotoRating photoRating, Principal principal) {
        if (!photoService.createRating(photoRating, principal))
            return "redirect:/my_albums";
        return "redirect:/photo/" + photoRating.getPhotoId();
    }

    @PostMapping("/update_photo_rating")
    public String updatePhotoRating(PhotoRating photoRating) {
        if (!photoService.updateRating(photoRating))
            return "redirect:/my_albums";
        return "redirect:/photo/" + photoRating.getPhotoId();
    }

    @PostMapping("/delete_photo_rating")
    public String deletePhotoRating(PhotoRating photoRating) {
        if (!photoService.deleteRating(photoRating))
            return "redirect:/my_albums";
        return "redirect:/photo/" + photoRating.getPhotoId();
    }

    @PostMapping("/add_photo_comment")
    public String addPhotoComment(@Valid PhotoComment photoComment, BindingResult bindingResult, Principal principal, Model model) {
        if (bindingResult.hasErrors()) {
            model
                    .addAttribute("photo", photoService.show(photoComment.getPhotoId(), principal))
                    .addAttribute("thisUser", principal);
            return "photo";
        }
        photoService.createComment(photoComment, principal);
        return "redirect:/photo/" + photoComment.getPhotoId();
    }

    @PostMapping("/delete_photo_comment")
    public String deletePhotoComment(PhotoComment photoComment) {
        if (!photoService.deleteComment(photoComment))
            return "redirect:/my_albums";
        return "redirect:/photo/" + photoComment.getPhotoId();
    }

    @GetMapping("/find_photos")
    public String findPhotos(String searchTerm, String word, Model model) {
        model
                .addAttribute("photos", photoService.find(searchTerm, word))
                .addAttribute("searchTerm", searchTerm)
                .addAttribute("word", word);
        return "find_photos";
    }

}
