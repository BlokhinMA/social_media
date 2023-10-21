package com.example.demo.services;

import com.example.demo.models.Friend;
import com.example.demo.models.User;
import com.example.demo.repositories.FriendRepository;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Service
public class FriendService {

    private final UserRepository userRepository;
    private final FriendRepository friendRepository;

    @Autowired
    public FriendService(UserRepository userRepository, FriendRepository friendRepository) {
        this.userRepository = userRepository;
        this.friendRepository = friendRepository;
    }

    public boolean add(String friendLogin, Principal principal) {
        if (userRepository.findByLogin(friendLogin) == null) // такого пользователя нет
            return false;
        Friend friend = new Friend();
        friend.setFirstUserLogin(principal.getName());
        friend.setSecondUserLogin(friendLogin);
        friendRepository.save(friend);
        return true;
    }

    public List<User> showIncomingRequests(Principal principal) {
        return friendRepository.findIncomingRequestsByUserLogin(principal.getName());
    }

    public List<User> showOutgoingRequests(Principal principal) {
        return friendRepository.findOutgoingRequestsByUserLogin(principal.getName());
    }

    public void accept(Friend friend) {
        friendRepository.accept(friend);
    }

    public List<User> show(Principal principal) {
        List<Friend> possibleFriends = friendRepository.findAllAcceptedByFirstUserLoginOrSecondUserLogin(principal.getName());
        List<User> friends = new ArrayList<>();
        for (Friend possibleFriend : possibleFriends)
            if (Objects.equals(possibleFriend.getFirstUserLogin(), principal.getName()))
                friends.add(userRepository.findByLogin(possibleFriend.getSecondUserLogin()));
            else friends.add(userRepository.findByLogin(possibleFriend.getFirstUserLogin()));
        return friends;
    }

    public List<User> find(Principal principal, String word) {
        /*String type = strings.get(0),
                word = strings.get(1);
        List<User> users = new ArrayList<>();
        if (Objects.equals(type, "username")) {
            users.add(userRepository.findByUsername(word));
            return users;
        }
        if (Objects.equals(type, "name")) {
            String[] names = word.split(" ");
            String firstName = names[0],
                    lastName = names[1];
            firstName = "%".concat(firstName).concat("%");
            lastName = "%".concat(lastName).concat("%");
            return userRepository.findByFirstNameAndLastName(firstName, lastName);
        }*/
        if (word != null) {
            word = "%".concat(word).concat("%");
            List<User> possibleFriends = friendRepository.findByLoginOrFirstNameOrLastNameExceptThisUser(principal.getName(), word);
            return possibleFriends;
        }
        return null;
    }

}
