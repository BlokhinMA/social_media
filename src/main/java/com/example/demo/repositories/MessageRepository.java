package com.example.demo.repositories;

import com.example.demo.models.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MessageRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MessageRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Message save(Message message) {
        jdbcTemplate.update("INSERT INTO Message(from_user_login, to_user_login, message) VALUES(?, ?, ?)",
                message.getFromUserLogin(),
                message.getToUserLogin(),
                message.getMessage());
        return jdbcTemplate.query("SELECT * FROM Message ORDER BY id DESC LIMIT 1", new BeanPropertyRowMapper<>(Message.class))
                .stream().findAny().orElse(null);
    }

    public List<Message> findAllByUserLogin(String userLogin) {
        return jdbcTemplate.query("SELECT DISTINCT from_user_login, to_user_login FROM Message WHERE from_user_login=? OR to_user_login=?", new BeanPropertyRowMapper<>(Message.class),
                userLogin,
                userLogin);
    }

    public List<Message> findAllByFromUserLoginAndToUserLogin(String fromUserLogin, String toUserLogin) {
        return jdbcTemplate.query("SELECT * FROM Message WHERE from_user_login IN (?, ?) AND to_user_login IN (?, ?) ORDER BY writing_time_stamp DESC", new BeanPropertyRowMapper<>(Message.class),
                fromUserLogin,
                toUserLogin,
                fromUserLogin,
                toUserLogin);
    }

    public Message findById(int id) {
        return jdbcTemplate.query("SELECT * FROM Message WHERE id=?", new BeanPropertyRowMapper<>(Message.class),
                        id)
                .stream().findAny().orElse(null);
    }

    public Message delete(Message message) {
        Message deletedMessage = findById(message.getId());
        jdbcTemplate.update("DELETE FROM Message WHERE id=?", message.getId());
        return deletedMessage;
    }

}
