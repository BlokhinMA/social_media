package com.example.demo.controllers;

import com.example.demo.models.Friendship;
import com.example.demo.services.FriendshipService;
import com.example.demo.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class FriendshipController {

    private final UserService userService;
    private final FriendshipService friendshipService;

    @GetMapping("/my_friends")
    public String myFriends(Model model, Principal principal) {
        model
                .addAttribute("friends", friendshipService.show(principal));
        return "my_friends";
    }

    @GetMapping("/friends/{userLogin}")
    public String friends(@PathVariable String userLogin, Principal principal, Model model) {
        if (Objects.equals(userLogin, principal.getName()))
            return "redirect:/my_friends";
        model
                .addAttribute("friends", friendshipService.show(userLogin))
                .addAttribute("userLogin", userLogin);
        return "friends";
    }

    @GetMapping("/find_friends")
    public String findFriends(String word, Principal principal, Model model) {
        model
                .addAttribute("possibleFriends", friendshipService.find(word, principal))
                .addAttribute("word", word);
        return "find_friends";
    }

    @PostMapping("/add_friend")
    public String addFriend(String friendLogin, Principal principal, Model model) {
        if (!friendshipService.create(friendLogin, principal)) {
            model
                    .addAttribute("condition", true);
            return "find_friends";
        }
        return "redirect:/find_friends";
    }

    @GetMapping("friends/requests")
    public String friendRequests(Model model, Principal principal) {
        model
                .addAttribute("incomingRequests", friendshipService.showIncomingRequests(principal))
                .addAttribute("outgoingRequests", friendshipService.showOutgoingRequests(principal))
                .addAttribute("thisUser", userService.getUserByPrincipal(principal));
        return "friend_requests";
    }

    @PostMapping("/accept_friend")
    public String acceptFriend(Friendship friendship) {
        friendshipService.accept(friendship);
        return "redirect:/my_friends";
    }

    @PostMapping("/delete_friend")
    public String deleteFriend(String friendLogin, Principal principal) {
        friendshipService.delete(friendLogin, principal);
        return "redirect:/my_friends";
    }

}
