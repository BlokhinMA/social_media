package com.example.demo.services;

import com.example.demo.models.Friendship;
import com.example.demo.models.User;
import com.example.demo.repositories.FriendshipRepository;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class FriendshipService {

    private final UserRepository userRepository;
    private final FriendshipRepository friendshipRepository;

    @Autowired
    public FriendshipService(UserRepository userRepository, FriendshipRepository friendshipRepository) {
        this.userRepository = userRepository;
        this.friendshipRepository = friendshipRepository;
    }

    public boolean create(String friendLogin, Principal principal) {
        if (userRepository.findByLogin(friendLogin) == null)
            return false;
        Friendship friendship = new Friendship();
        friendship.setFirstUserLogin(principal.getName());
        friendship.setSecondUserLogin(friendLogin);
        friendshipRepository.save(friendship);
        return true;
    }

    public List<User> showIncomingRequests(Principal principal) {
        return friendshipRepository.findIncomingRequestsByUserLogin(principal.getName());
    }

    public List<User> showOutgoingRequests(Principal principal) {
        return friendshipRepository.findOutgoingRequestsByUserLogin(principal.getName());
    }

    public void accept(Friendship friendship) {
        friendshipRepository.accept(friendship);
    }

    public List<User> show(Principal principal) {
        /*List<Friendship> possibleFriendships = friendshipRepository.findAllAcceptedByFirstUserLoginOrSecondUserLogin(principal.getName());
        List<User> friends = new ArrayList<>();
        for (Friendship possibleFriendship : possibleFriendships)
            if (Objects.equals(possibleFriendship.getFirstUserLogin(), principal.getName()))
                friends.add(userRepository.findByLogin(possibleFriendship.getSecondUserLogin()));
            else friends.add(userRepository.findByLogin(possibleFriendship.getFirstUserLogin()));
        return friends;*/
        return friendshipRepository.findAllAcceptedByFirstUserLoginOrSecondUserLogin(principal.getName());
    }

    public List<User> find(Principal principal, String word) {
        if (word != null) {
            word = "%".concat(word).concat("%");
            List<User> possibleFriends = friendshipRepository.findByLoginOrFirstNameOrLastNameExceptThisUser(principal.getName(), word);
            return possibleFriends;
        }
        return null;
    }

}
