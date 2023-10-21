package com.example.demo.controllers;

import com.example.demo.models.Album;
import com.example.demo.services.AlbumService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Controller
public class AlbumController {

    private final AlbumService albumService;

    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }

    @GetMapping("/albums")
    public String albums(Principal principal, Model model) {
        model.addAttribute("albums", albumService.showAll(principal));
        return "albums";
    }

    @PostMapping("/add_album")
    public String addAlbum(Album album, List<MultipartFile> files, Principal principal, Model model) throws IOException {
        if (!albumService.add(album, files, principal)) { // если фотографии не добавлены, то добавляется пустая фотография
            model.addAttribute("condition", true);
            return "/albums";
        }
        return "redirect:/albums";
    }

    @GetMapping("/albums/{id}")
    public String album(@PathVariable int id, Model model) {
        model.addAttribute("album", albumService.show(id));
        return "album";
    }

    @PostMapping("/create_photos")
    public String addPhotos(List<MultipartFile> files, int albumId, Model model) throws IOException {
        if (!albumService.addPhotos(files, albumId)) {
            model.addAttribute("condition", true);
            return "album";
        }
        return "redirect:/albums/" + albumId;
    }

}
