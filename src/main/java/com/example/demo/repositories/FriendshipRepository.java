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

    public List<User> findAllLikeLoginOrFirstNameOrLastName(String login, String word) {
        return jdbcTemplate.query("call find_friends_like_login_or_first_name_or_last_name(?, ?)", new BeanPropertyRowMapper<>(User.class),
                login,
                word);
    }

    public Friendship save(Friendship friendship) {
        return jdbcTemplate.query("call save_friendship(?, ?)", new BeanPropertyRowMapper<>(Friendship.class),
                        friendship.getFirstUserLogin(),
                        friendship.getSecondUserLogin())
                .stream().findAny().orElse(null);
    }

    public List<User> findAllAcceptedByUserLogin(String userLogin) {
        return jdbcTemplate.query("call find_accepted_friendships_by_user_login(?)", new BeanPropertyRowMapper<>(User.class),
                userLogin);
    }

    public List<User> findIncomingRequestsByUserLogin(String userLogin) {
        return jdbcTemplate.query("call find_incoming_requests_by_user_login(?)", new BeanPropertyRowMapper<>(User.class),
                userLogin);
    }

    public List<User> findOutgoingRequestsByUserLogin(String userLogin) {
        return jdbcTemplate.query("call find_outgoing_requests_by_user_login(?)", new BeanPropertyRowMapper<>(User.class),
                userLogin);
    }

    public Friendship accept(Friendship friendship) {
        return jdbcTemplate.query("call accept_friendship(?, ?)", new BeanPropertyRowMapper<>(Friendship.class),
                        friendship.getFirstUserLogin(),
                        friendship.getSecondUserLogin())
                .stream().findAny().orElse(null);
    }

    public Friendship findByFriendLoginAndUserLogin(String friendLogin, String userLogin) {
        return jdbcTemplate.query("call find_friendship_by_friend_login_and_user_login(?, ?)", new BeanPropertyRowMapper<>(Friendship.class),
                        friendLogin,
                        userLogin)
                .stream().findAny().orElse(null);
    }

    public Friendship deleteByFriendLoginAndUserLogin(String friendLogin, String userLogin) {
        return jdbcTemplate.query("call delete_friendship_by_friend_login_and_user_login(?, ?)", new BeanPropertyRowMapper<>(Friendship.class),
                        friendLogin,
                        userLogin)
                .stream().findAny().orElse(null);
    }

}
