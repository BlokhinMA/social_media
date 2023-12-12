package com.example.demo.repositories;

import com.example.demo.models.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MessageRepository {

    private final JdbcTemplate jdbcTemplate;

    public Message save(Message message) {
        return jdbcTemplate.query("call save_message(?, ?, ?)", new BeanPropertyRowMapper<>(Message.class),
                        message.getFromUserLogin(),
                        message.getToUserLogin(),
                        message.getMessage())
                .stream().findAny().orElse(null);
    }

    public List<Message> findAllByFromUserLoginAndToUserLogin(String fromUserLogin, String toUserLogin) {
        return jdbcTemplate.query("call find_messages_by_from_user_login_and_to_user_login(?, ?)", new BeanPropertyRowMapper<>(Message.class),
                fromUserLogin,
                toUserLogin);
    }

    public Message deleteById(int id) {
        return jdbcTemplate.query("call delete_message_by_id(?)", new BeanPropertyRowMapper<>(Message.class),
                        id)
                .stream().findAny().orElse(null);
    }

}
