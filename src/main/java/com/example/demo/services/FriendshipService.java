package com.example.demo.services;

import com.example.demo.models.Friendship;
import com.example.demo.models.User;
import com.example.demo.repositories.FriendshipRepository;
import com.example.demo.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendshipService {

    private final UserRepository userRepository;
    private final FriendshipRepository friendshipRepository;

    public List<User> find(String word, Principal principal) {
        if (word != null && !word.isEmpty())
            return friendshipRepository.findLikeLoginOrFirstNameOrLastNameExceptThisUser(principal.getName(), word);
        return null;
    }

    public boolean create(String friendLogin, Principal principal) {
        if (userRepository.findByLogin(friendLogin) == null || friendshipRepository.findByFriendLoginAndLogin(friendLogin, principal.getName()) != null)
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
        return friendshipRepository.findAllAcceptedByFirstUserLoginOrSecondUserLogin(principal.getName());
    }

    public List<User> show(String userLogin) {
        return friendshipRepository.findAllAcceptedByFirstUserLoginOrSecondUserLogin(userLogin);
    }

    public void delete(String friendLogin, Principal principal) {
        friendshipRepository.deleteByFriendLoginAndLogin(friendLogin, principal.getName());
    }

}
