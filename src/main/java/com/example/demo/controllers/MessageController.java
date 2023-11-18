package com.example.demo.controllers;

import com.example.demo.models.Message;
import com.example.demo.services.MessageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @PostMapping("/create_message")
    public String create(@Valid Message message, BindingResult bindingResult, Principal principal, Model model) {
        if (bindingResult.hasErrors()) {
            model
                    .addAttribute("companion", message.getToUserLogin())
                    .addAttribute("messages", messageService.show(message.getToUserLogin(), principal))
                    .addAttribute("thisUser", principal);
            return "message";
        }
        messageService.create(message);
        return "redirect:/messages/" + message.getToUserLogin();
    }

    @GetMapping("/messages")
    public String showAll(Principal principal, Model model) {
        model
                .addAttribute("messages", messageService.showAll(principal))
                .addAttribute("thisUser", principal);
        return "messages";
    }

    @GetMapping("/messages/{companion}")
    public String show(@PathVariable String companion, Principal principal, Model model, Message message) {
        model
                .addAttribute("companion", companion)
                .addAttribute("messages", messageService.show(companion, principal))
                .addAttribute("thisUser", principal);
        return "message";
    }

    @PostMapping("/delete_message")
    public String delete(Message message) {
        messageService.delete(message);
        return "redirect:/messages/" + message.getToUserLogin();
    }

}
