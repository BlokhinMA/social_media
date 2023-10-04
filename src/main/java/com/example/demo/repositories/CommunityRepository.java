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

    public void save(Community community, String creatorUsername) {
        jdbcTemplate.update("INSERT INTO communities(name, creator_id) VALUES(?, (SELECT id FROM users WHERE username=?))",
                community.getName(),
                creatorUsername);
    }

    public int findLastId() {
        return jdbcTemplate.queryForObject("SELECT id FROM communities ORDER BY id DESC LIMIT 1", Integer.class);
    }

    public List<Community> findAllByCreatorUsername(String creatorUsername) {
        return jdbcTemplate.query("SELECT * FROM communities WHERE creator_id=(SELECT id FROM users WHERE username=?)", new BeanPropertyRowMapper<>(Community.class), creatorUsername);
    }

    public Community findById(int id) {
        return jdbcTemplate.query("SELECT * FROM communities WHERE id=?", new BeanPropertyRowMapper<>(Community.class), id).stream().findAny().orElse(null);
    }

}
