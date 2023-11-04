package com.example.demo.repositories;

import com.example.demo.models.Community;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CommunityRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CommunityRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Community save(Community community) {
        jdbcTemplate.update("INSERT INTO Community(name, creator_login) VALUES(?, ?)",
                community.getName(),
                community.getCreatorLogin());
        return jdbcTemplate.query("SELECT * FROM Community ORDER BY id DESC LIMIT 1", new BeanPropertyRowMapper<>(Community.class)).stream().findAny().orElse(null);
    }

    public List<Community> findAllByMemberLogin(String memberLogin) {
        return jdbcTemplate.query("SELECT Community.* FROM Community JOIN Community_member ON Community.id=community_id WHERE member_login=?", new BeanPropertyRowMapper<>(Community.class), memberLogin);
    }

    public List<Community> findAllByCreatorLogin(String creatorLogin) {
        return jdbcTemplate.query("SELECT * FROM Community WHERE creator_login=?", new BeanPropertyRowMapper<>(Community.class), creatorLogin);
    }

    public Community findById(int id) {
        return jdbcTemplate.query("SELECT * FROM Community WHERE id=?", new BeanPropertyRowMapper<>(Community.class), id).stream().findAny().orElse(null);
    }

}
