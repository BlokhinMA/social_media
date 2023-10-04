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
        model.addAttribute("albums", albumService.showAlbums(principal.getName()));
        return "albums";
    }

    @PostMapping("/add_album")
    public String addAlbum(Album album, Principal principal, List<MultipartFile> files, Model model) throws IOException {
        if (!albumService.addAlbum(album, principal.getName(), files)) {
            model.addAttribute("condition", true);
            return "/albums";
        }
        return "redirect:/albums";
    }

}
