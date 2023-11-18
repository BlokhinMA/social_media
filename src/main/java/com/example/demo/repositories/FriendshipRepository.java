package com.example.demo.repositories;

import com.example.demo.models.Friendship;
import com.example.demo.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class FriendshipRepository {

    private final JdbcTemplate jdbcTemplate;

    public List<User> findLikeLoginOrFirstNameOrLastNameExceptThisUser(String thisUserLogin, String word) {
        return jdbcTemplate.query("SELECT * FROM User WHERE login!=? AND (login LIKE CONCAT('%', ?, '%') OR first_name LIKE CONCAT('%', ?, '%') OR last_name LIKE CONCAT('%', ?, '%')) EXCEPT (SELECT User.* FROM User JOIN Friendship ON login = second_user_login WHERE first_user_login=? UNION SELECT User.* FROM User JOIN Friendship ON login = first_user_login WHERE second_user_login=?)", new BeanPropertyRowMapper<>(User.class),
                thisUserLogin,
                word,
                word,
                word,
                thisUserLogin,
                thisUserLogin);
    }

    public Friendship save(Friendship friendship) {
        jdbcTemplate.update("INSERT INTO Friendship(first_user_login, second_user_login) VALUES(?, ?)",
                friendship.getFirstUserLogin(),
                friendship.getSecondUserLogin());
        return jdbcTemplate.query("SELECT * FROM Friendship ORDER BY id DESC LIMIT 1", new BeanPropertyRowMapper<>(Friendship.class))
                .stream().findAny().orElse(null);
    }

    public List<User> findAllAcceptedByFirstUserLoginOrSecondUserLogin(String userLogin) {
        return jdbcTemplate.query("SELECT User.* FROM User JOIN Friendship ON login = second_user_login WHERE first_user_login=? AND accepted=1 UNION SELECT User.* FROM User JOIN Friendship ON login = first_user_login WHERE second_user_login=? AND accepted=1", new BeanPropertyRowMapper<>(User.class),
                userLogin,
                userLogin);
    }

    public List<User> findIncomingRequestsByUserLogin(String userLogin) {
        return jdbcTemplate.query("SELECT User.* FROM User JOIN Friendship ON login = first_user_login WHERE second_user_login=? AND accepted=0", new BeanPropertyRowMapper<>(User.class),
                userLogin);
    }

    public List<User> findOutgoingRequestsByUserLogin(String userLogin) {
        return jdbcTemplate.query("SELECT User.* FROM User JOIN Friendship ON login = second_user_login WHERE first_user_login=? AND accepted=0", new BeanPropertyRowMapper<>(User.class),
                userLogin);
    }

    public Friendship accept(Friendship friendship) {
        jdbcTemplate.update("UPDATE Friendship SET accepted=true WHERE first_user_login=? AND second_user_login=?",
                friendship.getFirstUserLogin(),
                friendship.getSecondUserLogin());
        return jdbcTemplate.query("SELECT * FROM Friendship WHERE first_user_login=? AND second_user_login=?", new BeanPropertyRowMapper<>(Friendship.class),
                        friendship.getFirstUserLogin(),
                        friendship.getSecondUserLogin())
                .stream().findAny().orElse(null);
    }

    public Friendship findByFriendLoginAndLogin(String friendLogin, String login) {
        return jdbcTemplate.query("SELECT * FROM Friendship WHERE first_user_login IN (?, ?) AND second_user_login IN (?, ?)", new BeanPropertyRowMapper<>(Friendship.class),
                        friendLogin,
                        login,
                        friendLogin,
                        login)
                .stream().findAny().orElse(null);
    }

    public Friendship deleteByFriendLoginAndLogin(String friendLogin, String login) {
        Friendship friendship = findByFriendLoginAndLogin(friendLogin, login);
        if (friendship != null)
            jdbcTemplate.update("DELETE FROM Friendship WHERE id=?",
                    friendship.getId());
        return friendship;
    }

}
