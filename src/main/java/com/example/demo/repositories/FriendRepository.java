package com.example.demo.repositories;

import com.example.demo.models.Friend;
import com.example.demo.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FriendRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public FriendRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(Friend friend) {
        jdbcTemplate.update("INSERT INTO friends(first_user_id, second_user_id) VALUES(?, ?)",
                friend.getFirstUserId(),
                friend.getSecondUserId());
    }

    /*public List<User> findFriendsByUserId(int userId) {
        return jdbcTemplate.query("SELECT username FROM users JOIN (SELECT friend_id FROM friends WHERE user_id=?) AS F ON users.user_id = F.friend_id", new BeanPropertyRowMapper<>(User.class), userId);
    }

    public List<User> findFriendsByFriendId(int friendId) {
        return jdbcTemplate.query("SELECT username FROM users JOIN (SELECT user_id FROM friends WHERE friend_id=?) AS F ON users.user_id = F.friend_id", new BeanPropertyRowMapper<>(User.class), friendId);
    }*/

    /*public List<Friend> friends(int userId, int friendId) {
        return jdbcTemplate.query("select * from friends where user_id=? or friend_id=?", new BeanPropertyRowMapper<>(Friend.class), userId, friendId);
    }*/

    public List<Friend> findAllByUserId(int userId) {
        return jdbcTemplate.query("SELECT * FROM friends WHERE first_user_id=? or second_user_id=?", new BeanPropertyRowMapper<>(Friend.class), userId, userId);
    }

    public List<Friend> findAllAcceptedByUserId(int userId) {
        return jdbcTemplate.query("SELECT * FROM friends WHERE (first_user_id=? or second_user_id=?) AND accepted=1", new BeanPropertyRowMapper<>(Friend.class), userId, userId);
    }

    /*public List<Friend> findIncomingRequestsByUserId(int userId) {
        return jdbcTemplate.query("SELECT * FROM friends WHERE second_user_id=? AND accepted=0", new BeanPropertyRowMapper<>(Friend.class), userId);
    }*/

    /*public List<User> findIncomingRequestsByUserId(int userId) {
        return jdbcTemplate.query("SELECT * FROM users WHERE user_id=(SELECT first_user_id FROM friends WHERE second_user_id=?)", new BeanPropertyRowMapper<>(User.class), userId);
    }*/

    public List<User> findIncomingRequestsByUsername(String username) {
        return jdbcTemplate.query("SELECT id, username, first_name, last_name FROM users WHERE id=(SELECT first_user_id FROM friends WHERE second_user_id=(SELECT id FROM users WHERE username=?) AND accepted=0)", new BeanPropertyRowMapper<>(User.class), username);
    }

    public List<User> findOutgoingRequestsByUsername(String username) {
        return jdbcTemplate.query("SELECT id, username, first_name, last_name FROM users WHERE id=(SELECT second_user_id FROM friends WHERE first_user_id=(SELECT id FROM users WHERE username=?) AND accepted=0)", new BeanPropertyRowMapper<>(User.class), username);
    }

    public void accept(Friend friend) {
        jdbcTemplate.update("UPDATE friends SET accepted=true WHERE first_user_id=? AND second_user_id=?", friend.getFirstUserId(), friend.getSecondUserId());
    }

}
