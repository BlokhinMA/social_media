package com.example.demo.controllers;

import com.example.demo.models.User;
import com.example.demo.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.Objects;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String index(Principal principal) {
        if (principal != null)
            return "redirect:/my_profile";
        return "index";
    }

    @GetMapping("/sign_up")
    public String signUp(Principal principal, User user) {
        if (principal != null)
            return "redirect:/my_profile";
        return "sign_up";
    }

    @GetMapping("/sign_in")
    public String signIn(Principal principal) {
        if (principal != null)
            return "redirect:/my_profile";
        return "sign_in";
    }

    @PostMapping("/sign_up")
    public String signUp(@Valid User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors())
            return "sign_up";
        if (!userService.create(user)) {
            model
                    .addAttribute("condition", true);
            return "sign_up";
        }
        return "redirect:/sign_in";
    }

    @GetMapping("/my_profile")
    public String myProfile(Principal principal, Model model) {
        model
                .addAttribute("user", userService.getUserByPrincipal(principal));
        return "my_profile";
    }

    @GetMapping("/profile/{login}")
    public String profile(@PathVariable String login, Principal principal, Model model) {
        if (Objects.equals(login, principal.getName())) {
            return "redirect:/my_profile";
        }
        model
                .addAttribute("user", userService.getUserByLogin(login));
        return "profile";
    }

}
