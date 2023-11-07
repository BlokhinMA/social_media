package com.example.demo.controllers;

import com.example.demo.models.Album;
import com.example.demo.services.AlbumService;
import jakarta.validation.Valid;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Objects;

@Controller
public class AlbumController {

    private final AlbumService albumService;

    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }

    @GetMapping("/my_albums")
    public String myAlbums(Principal principal, Model model, Album album) {
        model.addAttribute("albums", albumService.showAll(principal));
        model.addAttribute("thisUser", principal);
        return "my_albums";
    }

    @GetMapping("/albums/{userLogin}")
    public String albums(@PathVariable String userLogin, Principal principal, Model model) {
        if (Objects.equals(userLogin, principal.getName())) {
            return "redirect:/my_albums";
        }
        model.addAttribute("albums", albumService.showAll(userLogin));
        return "albums";
    }

    @PostMapping("/add_album")
    public String addAlbum(@Valid Album album, BindingResult bindingResult, List<MultipartFile> files, Principal principal, Model model) throws IOException {
        if (bindingResult.hasErrors()) { // если фотографии не добавлены, то добавляется пустая фотография
            model.addAttribute("albums", albumService.showAll(principal));
            return "my_albums";
        }
        albumService.create(album, files, principal);
        return "redirect:/my_albums";
    }

    @GetMapping("/album/{id}")
    public String album(@PathVariable int id, Principal principal, Model model) {
        model.addAttribute("album", albumService.show(id));
        model.addAttribute("thisUser", principal);
        return "album";
    }

    @PostMapping("/create_photos")
    public String addPhotos(List<MultipartFile> files, int albumId, Principal principal, Model model) throws IOException {
        if (!albumService.createPhotos(files, albumId)) {
            model.addAttribute("condition", true);
            model.addAttribute("thisUser", principal);
            return "album";
        }
        return "redirect:/album/" + albumId;
    }

    @PostMapping("/delete_album")
    public String deleteAlbum(int id) {
        albumService.delete(id);
        return "redirect:/my_albums";
    }

}
