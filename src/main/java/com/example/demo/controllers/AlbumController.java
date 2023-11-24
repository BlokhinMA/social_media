package com.example.demo.controllers;

import com.example.demo.models.Album;
import com.example.demo.services.AlbumService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class AlbumController {

    private final AlbumService albumService;

    @GetMapping("/my_albums")
    public String myAlbums(Principal principal, Model model, Album album) {
        model
                .addAttribute("albums", albumService.showAll(principal))
                .addAttribute("thisUser", principal);
        return "my_albums";
    }

    @GetMapping("/albums/{userLogin}")
    public String albums(@PathVariable String userLogin, Principal principal, Model model) {
        if (Objects.equals(userLogin, principal.getName())) {
            return "redirect:/my_albums";
        }
        model
                .addAttribute("albums", albumService.showAll(userLogin))
                .addAttribute("userLogin", userLogin);
        return "albums";
    }

    @PostMapping("/add_album")
    public String addAlbum(@Valid Album album, BindingResult bindingResult, List<MultipartFile> files, Principal principal, Model model) throws IOException {
        if (bindingResult.hasErrors()) {
            model
                    .addAttribute("albums", albumService.showAll(principal));
            return "my_albums";
        }
        albumService.create(album, files, principal);
        return "redirect:/my_albums";
    }

    @GetMapping("/album/{id}")
    public String album(@PathVariable int id, Principal principal, Model model, Album album) {
        model
                .addAttribute("album", albumService.show(id))
                .addAttribute("thisUser", principal)
                .addAttribute("isFriend", albumService.isFriend(principal, albumService.show(id).getUserLogin()));
        return "album";
    }

    @PostMapping("/create_photos")
    public String addPhotos(List<MultipartFile> files, int albumId, Principal principal, Model model) throws IOException {
        if (!albumService.createPhotos(files, albumId, principal)) {
            model
                    .addAttribute("condition", true)
                    .addAttribute("thisUser", principal);
            return "album";
        }
        return "redirect:/album/" + albumId;
    }

    @PostMapping("/delete_album")
    public String deleteAlbum(int id, Principal principal) {
        albumService.delete(id, principal);
        return "redirect:/my_albums";
    }

    @GetMapping("/find_albums")
    public String findAlbums(String word, Model model) {
        model
                .addAttribute("albums", albumService.find(word))
                .addAttribute("word", word);
        return "find_albums";
    }

    @PostMapping("/change_access_type")
    public String changeAccessType(@Valid Album album, BindingResult bindingResult, Principal principal, Model model) {
        if (bindingResult.hasErrors()) {
            model
                    .addAttribute("album", albumService.show(album.getId()))
                    .addAttribute("thisUser", principal);
            return "album";
        }
        if (!albumService.changeAccessType(album, principal))
            return "redirect:/my_albums";
        return "redirect:/album/" + album.getId();
    }

}
