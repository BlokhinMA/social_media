package com.example.demo.repositories;

import com.example.demo.models.User;
import com.example.demo.models.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public User findByLogin(String login) {

        User user = jdbcTemplate.query("call find_user_by_login(?)", new BeanPropertyRowMapper<>(User.class),
                        login)
                .stream().findAny().orElse(null);

        if (user != null) {
            List<String> roles = jdbcTemplate.queryForList("call find_roles_by_user_login(?)", String.class,
                    user.getLogin());

            for (String role : roles) {
                user.getRoles().add(Role.valueOf(role));
            }

        }

        return user;
    }

    public User findByEmail(String email) {
        return jdbcTemplate.query("call find_user_by_email(?)", new BeanPropertyRowMapper<>(User.class),
                        email)
                .stream().findAny().orElse(null);
    }

    public User save(User user) {
        return jdbcTemplate.query("call save_user(?, ?, ?, ?, ?, ?)", new BeanPropertyRowMapper<>(User.class),
                        user.getLogin(),
                        user.getEmail(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getBirthDate(),
                        user.getPassword())
                .stream().findAny().orElse(null);
    }

    public List<User> findAll() {
        return jdbcTemplate.query("call find_users()", new BeanPropertyRowMapper<>(User.class));
    }

}
