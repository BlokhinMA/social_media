package com.example.demo.services;

import com.example.demo.models.enums.Role;
import com.example.demo.repositories.UserRepository;
import com.example.demo.models.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public boolean create(User user) {
        if (userRepository.findByLogin(user.getLogin()) != null || userRepository.findByEmail(user.getEmail()) != null)
            return false;
        user.getRoles().add(Role.ROLE_USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User createdUser = userRepository.save(user);
        log.info("Новый пользователь {} зарегистрировался",
                createdUser);
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
