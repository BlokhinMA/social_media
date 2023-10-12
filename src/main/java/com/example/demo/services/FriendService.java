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

    public boolean addFriend(int friendId, Principal principal) {
        if (userRepository.findById(friendId) == null) // такого пользователя нет
            return false;
        Friend friend = new Friend();
        friend.setFirstUserId(userRepository.findByUsername(principal.getName()).getId());
        friend.setSecondUserId(userRepository.findById(friendId).getId());
        friendRepository.save(friend);
        return true;
    }

    public List<User> showIncomingRequests(String username) {
        /*int userId = userRepository.findByUsername(username).getUserId();
        List<Friend> incomingRequests = friendRepository.findIncomingRequestsByUserId(userId);
        List<User> users = new ArrayList<>();
        for (Friend incomingRequest : incomingRequests)
            users.add(userRepository.findById(incomingRequest.getFirstUserId()));
        return users;*/
        return friendRepository.findIncomingRequestsByUsername(username);
    }

    public List<User> showOutgoingRequests(String username) {
        return friendRepository.findOutgoingRequestsByUsername(username);
    }

    public void accept(Friend friend) {
        friendRepository.accept(friend);
    }

    public List<User> showFriends(String username) {
        int userId = userRepository.findByUsername(username).getId();
        List<Friend> possibleFriends = friendRepository.findAllAcceptedByUserId(userId);
        List<User> friends = new ArrayList<>();
        for (Friend possibleFriend : possibleFriends)
            if (possibleFriend.getFirstUserId() == userId)
                friends.add(userRepository.findById(possibleFriend.getSecondUserId()));
            else friends.add(userRepository.findById(possibleFriend.getFirstUserId()));
        return friends;
    }

    public List<User> findFriends(String thisUserUsername, String word) {
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
            List<User> possibleFriends = friendRepository.findByUsernameOrFirstNameOrLastNameExceptThisUser(thisUserUsername, word);
        }
        return null;
    }

    /*public List<User> showNotAcceptedFriends(int userId) {
        return friendRepository.findFriendsByFriendId(userId);
    }*/

    /*public List<User> friends(int userId) {
        List<Friend> possibleFriends = friendRepository.friends(userId, userId);
        if (possibleFriends == null)
            return null;
        List<User> friends = new ArrayList<>();
        int topLeft, topRight, bottomLeft, bottomRight;
        for (int i = 0; i < possibleFriends.size() - 1; i++) {
            for (int j = i + 1; j < possibleFriends.size(); j++) {
                topLeft = possibleFriends.get(i).getUserId();
                bottomRight = possibleFriends.get(j).getFriendId();
                topRight = possibleFriends.get(i).getFriendId();
                bottomLeft = possibleFriends.get(j).getUserId();
                if (topLeft == bottomRight &&
                        topRight == bottomLeft) {
                    if (topLeft == userId)
                        friends.add(userRepository.findById(topRight));
                    else friends.add(userRepository.findById(topLeft));
                    i = j;
                    break;
                }
            }
        }
        return friends;
    }*/

}
