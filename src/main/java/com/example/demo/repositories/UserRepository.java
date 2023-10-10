package com.example.demo.repositories;

import com.example.demo.models.User;
import com.example.demo.models.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public User findById(int id) {
        return jdbcTemplate.query("SELECT * FROM users WHERE id=?", new BeanPropertyRowMapper<>(User.class), id)
                .stream().findAny().orElse(null);
    }

    public User findByUsername(String username) {
        return jdbcTemplate.query("SELECT * FROM users WHERE username=?", new BeanPropertyRowMapper<>(User.class), username)
                .stream().findAny().orElse(null);
    }

    public User findByEmail(String email) {
        return jdbcTemplate.query("SELECT * FROM users WHERE email=?", new BeanPropertyRowMapper<>(User.class), email)
                .stream().findAny().orElse(null);
    }

    public void save(User user) {
        jdbcTemplate.update("INSERT INTO users(username, first_name, last_name, password, birth_date, email) VALUES(?, ?, ?, ?, ?, ?)",
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getPassword(),
                user.getBirthDate(),
                user.getEmail());

        int lastUserId = jdbcTemplate.queryForObject("SELECT id FROM users ORDER BY id DESC LIMIT 1", Integer.class);
        for (Role role : user.getRoles()) {
            jdbcTemplate.update("INSERT INTO roles(user_id, role) VALUES(?, ?)",
                    lastUserId,
                    role.toString());
        }
    }

    public List<User> findByFirstNameAndLastName(String firstName, String lastName) {
        return jdbcTemplate.query("SELECT * FROM users WHERE first_name LIKE ? OR LIKE ?", new BeanPropertyRowMapper<>(User.class), firstName, lastName);
    }

}
