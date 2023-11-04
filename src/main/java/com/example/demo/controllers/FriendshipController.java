package com.example.demo.controllers;

import com.example.demo.models.Friendship;
import com.example.demo.services.FriendshipService;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class FriendshipController {

    private final UserService userService;
    private final FriendshipService friendshipService;

    @Autowired
    public FriendshipController(FriendshipService friendshipService, UserService userService) {
        this.friendshipService = friendshipService;
        this.userService = userService;
    }

    @GetMapping("/friends")
    public String friends(Model model, Principal principal) {
        model.addAttribute("friends", friendshipService.show(principal));
        return "friends";
    }

    @GetMapping("/find_friends")
    public String findFriends(Principal principal, String word, Model model) {
        model.addAttribute("possibleFriends", friendshipService.find(principal, word)); // исключить пользователей, которые уже есть в друзьях/заявках
        return "find_friends";
    }

    @PostMapping("/add_friend")
    public String addFriend(String friendLogin, Principal principal, Model model) {
        if (!friendshipService.create(friendLogin, principal)) {
            model.addAttribute("condition", true);
            return "find_friends";
        }
        return "redirect:/find_friends";
    }

    @GetMapping("friends/requests")
    public String friendRequests(Model model, Principal principal) {
        model.addAttribute("incomingRequests", friendshipService.showIncomingRequests(principal));
        model.addAttribute("outgoingRequests", friendshipService.showOutgoingRequests(principal));
        model.addAttribute("thisUser", userService.getUserByPrincipal(principal)); // контроллер должен быть тонким!
        return "friend_requests";
    }

    @PostMapping("/accept_friend")
    public String acceptFriend(Friendship friendship) {
        friendshipService.accept(friendship);
        return "redirect:/friends";
    }

}
