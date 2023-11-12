package com.example.demo.controllers;

import com.example.demo.models.enums.Role;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public String users(Principal principal, Model model) {
        if (userService.getUserByPrincipal(principal).getRoles().contains(Role.ROLE_ADMIN)) {
            model
                    .addAttribute("users", userService.showAllUsers());
            return "adminUsers";
        } else return "redirect:/";
    }

}
