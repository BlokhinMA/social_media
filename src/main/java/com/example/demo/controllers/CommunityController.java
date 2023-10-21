package com.example.demo.controllers;

import com.example.demo.models.Community;
import com.example.demo.services.CommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class CommunityController {

    private final CommunityService communityService;

    @Autowired
    public CommunityController(CommunityService communityService) {
        this.communityService = communityService;
    }

    @GetMapping("/my_communities")
    public String myCommunities(Principal principal, Model model) {
        model.addAttribute("communities", communityService.showAll(principal));
        return "my_communities";
    }

    @PostMapping("/add_community")
    public String addCommunity(Community community, Principal principal, Model model) {
        if (!communityService.add(community, principal)) {
            model.addAttribute("condition", true);
            return "/my_communities";
        }
        return "redirect:/my_communities";
    }

    @GetMapping("/community/{id}")
    public String community(@PathVariable int id, Model model) {
        model.addAttribute("community", communityService.show(id));
        return "community";
    }

}
