package com.example.demo.controllers;

import com.example.demo.models.Friend;
import com.example.demo.services.FriendService;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class FriendController {

    private final UserService userService;
    private final FriendService friendService;

    @Autowired
    public FriendController(FriendService friendService, UserService userService) {
        this.friendService = friendService;
        this.userService = userService;
    }

    @GetMapping("/friends")
    public String friends(Model model, Principal principal) {
        model.addAttribute("friends", friendService.showFriends(principal.getName()));
        return "friends";
    }

    @GetMapping("/add_friend")
    public String addFriend() {
        return "add_friend";
    }

    @PostMapping("/add_friend")
    public String addFriend(String friendUsername, Principal principal, Model model) {
        if (!friendService.addFriend(friendUsername, principal)) {
            model.addAttribute("condition", true);
            return "add_friend";
        }
        return "redirect:/friends";
    }

    @GetMapping("friends/requests")
    public String friendRequests(Model model, Principal principal) {
        model.addAttribute("incomingRequests", friendService.showIncomingRequests(principal.getName()));
        model.addAttribute("outgoingRequests", friendService.showOutgoingRequests(principal.getName()));
        model.addAttribute("thisUser", userService.getUserByPrincipal(principal));
        return "friend_requests";
    }

    @PostMapping("/accept_friend")
    public String acceptFriend(Friend friend) {
        friendService.accept(friend);
        return "redirect:/friends";
    }

}
