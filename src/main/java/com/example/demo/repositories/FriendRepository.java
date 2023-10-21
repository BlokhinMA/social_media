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

    public List<User> findByLoginOrFirstNameOrLastNameExceptThisUser(String thisUserLogin, String word) {
        return jdbcTemplate.query("SELECT * FROM users WHERE login!=? AND (login LIKE ? OR first_name LIKE ? OR last_name LIKE ?)", new BeanPropertyRowMapper<>(User.class),
                thisUserLogin,
                word,
                word,
                word);
    }

    public Friend save(Friend friend) {
        jdbcTemplate.update("INSERT INTO friends(first_user_login, second_user_login) VALUES(?, ?)",
                friend.getFirstUserLogin(),
                friend.getSecondUserLogin());
        return jdbcTemplate.query("SELECT * FROM friends ORDER BY id DESC LIMIT 1", new BeanPropertyRowMapper<>(Friend.class)).stream().findAny().orElse(null);
    }

    public List<Friend> findAllByFirstUserLoginOrSecondUserLogin(String userLogin) {
        return jdbcTemplate.query("SELECT * FROM friends WHERE first_user_login=? or second_user_login=?", new BeanPropertyRowMapper<>(Friend.class),
                userLogin,
                userLogin);
    }

    public List<Friend> findAllAcceptedByFirstUserLoginOrSecondUserLogin(String userLogin) {
        return jdbcTemplate.query("SELECT * FROM friends WHERE (first_user_login=? or second_user_login=?) AND accepted=1", new BeanPropertyRowMapper<>(Friend.class),
                userLogin,
                userLogin);
    }

    public List<User> findIncomingRequestsByUserLogin(String userLogin) {
        return jdbcTemplate.query("SELECT users.* FROM users JOIN friends ON login = first_user_login WHERE second_user_login=? AND accepted=0", new BeanPropertyRowMapper<>(User.class), userLogin);
    }

    public List<User> findOutgoingRequestsByUserLogin(String userLogin) {
        return jdbcTemplate.query("SELECT users.* FROM users JOIN friends ON login = second_user_login WHERE first_user_login=? AND accepted=0", new BeanPropertyRowMapper<>(User.class), userLogin);
    }

    public void accept(Friend friend) {
        jdbcTemplate.update("UPDATE friends SET accepted=true WHERE first_user_login=? AND second_user_login=?",
                friend.getFirstUserLogin(),
                friend.getSecondUserLogin());
    }

}
