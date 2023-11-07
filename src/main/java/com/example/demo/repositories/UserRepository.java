package com.example.demo.repositories;

import com.example.demo.models.User;
import com.example.demo.models.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashSet;

@Repository
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public User findById(int id) {
        return jdbcTemplate.query("SELECT * FROM User WHERE id=?", new BeanPropertyRowMapper<>(User.class),
                        id)
                .stream().findAny().orElse(null);
    }

    public User findByLogin(String login) {
        return jdbcTemplate.query("SELECT * FROM User WHERE login=?", new BeanPropertyRowMapper<>(User.class),
                        login)
                .stream().findAny().orElse(null);
    }

    public User findByEmail(String email) {
        return jdbcTemplate.query("SELECT * FROM User WHERE email=?", new BeanPropertyRowMapper<>(User.class),
                        email)
                .stream().findAny().orElse(null);
    }

    public User save(User user) {
        jdbcTemplate.update("INSERT INTO User(login, email, first_name, last_name, birth_date, password) VALUES(?, ?, ?, ?, ?, ?)",
                user.getLogin(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getBirthDate(),
                user.getPassword());

        for (Role role : user.getRoles()) {
            jdbcTemplate.update("INSERT INTO Role(user_login, role) VALUES(?, ?)",
                    user.getLogin(),
                    role.toString());
        }

        User user1 = jdbcTemplate.query("SELECT * FROM User ORDER BY id DESC LIMIT 1", new BeanPropertyRowMapper<>(User.class))
                .stream().findAny().orElse(null);
        /*assert user1 != null;
        user1.setRoles(new HashSet<>(jdbcTemplate.query("SELECT role FROM Role WHERE user_login=?", new BeanPropertyRowMapper<>(Role.class),
                user1.getLogin())));*/
        return user1;
    }

}
