package com.example.demo.services;

import com.example.demo.models.enums.Role;
import com.example.demo.repositories.UserRepository;
import com.example.demo.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean create(User user) {
        if (userRepository.findByLogin(user.getLogin()) != null || userRepository.findByEmail(user.getEmail()) != null)
            return false;
        user.getRoles().add(Role.ROLE_USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }

    public User getUserByPrincipal(Principal principal) {
        return userRepository.findByLogin(principal.getName());
    }

    public User getUserByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    public List<User> showAllUsers() {
        return userRepository.findAll();
    }

}
