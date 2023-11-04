package com.example.demo.controllers;

import com.example.demo.models.Community;
import com.example.demo.models.CommunityMember;
import com.example.demo.models.CommunityPost;
import com.example.demo.services.CommunityService;
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
public class CommunityController {

    private final CommunityService communityService;

    @Autowired
    public CommunityController(CommunityService communityService) {
        this.communityService = communityService;
    }

    @GetMapping("/my_own_communities")
    public String myOwnCommunities(Principal principal, Model model, Community community) {
        model.addAttribute("communities", communityService.showAllOwn(principal));
        return "my_own_communities";
    }

    @GetMapping("/my_communities")
    public String myCommunities(Principal principal, Model model) {
        model.addAttribute("communities", communityService.showAll(principal));
        return "my_communities";
    }

    @PostMapping("/add_community")
    public String addCommunity(@Valid Community community, BindingResult bindingResult, Principal principal, Model model) {
        if (bindingResult.hasErrors() || !communityService.create(community, principal)) {
            model.addAttribute("communities", communityService.showAll(principal));
            return "my_own_communities";
        }
        return "redirect:/my_own_communities";
    }

    @GetMapping("/communities/{memberLogin}")
    public String communities(@PathVariable String memberLogin, Principal principal, Model model) {
        if (Objects.equals(memberLogin, principal.getName()))
            return "redirect:/my_communities";
        model.addAttribute("memberLogin", memberLogin);
        model.addAttribute("communities", communityService.showAll(memberLogin));
        return "communities";
    }

    @GetMapping("/community/{id}")
    public String community(@PathVariable int id, Principal principal, Model model, CommunityPost communityPost) {
        model.addAttribute("community", communityService.show(id));
        model.addAttribute("isMember", communityService.isMember(principal, id));
        model.addAttribute("thisUser", principal);
        return "community";
    }

    @PostMapping("/join_community")
    public String joinCommunity(Principal principal, int communityId) {
        if (!communityService.join(principal, communityId)) {
            return "redirect:/communities";
        }
        return "redirect:/community/" + communityId;
    }

    @PostMapping("/leave_community")
    public String leaveCommunity(Principal principal, CommunityMember communityMember) {
        if (!communityService.leave(principal, communityMember)) {
            return "redirect:/communities";
        }
        return "redirect:/community/" + communityMember.getCommunityId();
    }

    @PostMapping("/add_community_post")
    public String addCommunityPost(@Valid CommunityPost communityPost, BindingResult bindingResult, Principal principal, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("community", communityService.show(communityPost.getCommunityId()));
            model.addAttribute("isMember", communityService.isMember(principal, communityPost.getCommunityId()));
            model.addAttribute("thisUser", principal);
            return "community";
        }
        communityService.createPost(communityPost, principal);
        return "redirect:/community/" + communityPost.getCommunityId();
    }

    @PostMapping("/delete_community_post")
    public String deleteCommunityPost(CommunityPost communityPost) {
        if (!communityService.deletePost(communityPost))
            return "redirect:/communities";
        return "redirect:/community/" + communityPost.getCommunityId();
    }

}
