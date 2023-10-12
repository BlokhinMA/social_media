package com.example.demo.controllers;

import com.example.demo.models.Friend;
import com.example.demo.services.FriendService;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

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

    @PostMapping("/add_friend")
    public String addFriend(int friendId, Principal principal, Model model) {
        if (!friendService.addFriend(friendId, principal)) {
            model.addAttribute("condition", true);
            return "find_friends";
        }
        return "redirect:/find_friends";
    }

    @GetMapping("friends/requests")
    public String friendRequests(Model model, Principal principal) {
        model.addAttribute("incomingRequests", friendService.showIncomingRequests(principal.getName()));
        model.addAttribute("outgoingRequests", friendService.showOutgoingRequests(principal.getName()));
        model.addAttribute("thisUser", userService.getUserByPrincipal(principal)); // контроллер должен быть тонким!
        return "friend_requests";
    }

    @PostMapping("/accept_friend")
    public String acceptFriend(Friend friend) {
        friendService.accept(friend);
        return "redirect:/friends";
    }

    @GetMapping("/find_friends")
    public String findFriends(Principal principal, String word, Model model) {
        model.addAttribute("possibleFriends", friendService.findFriends(principal.getName(), word));
        return "find_friends";
    }

}
