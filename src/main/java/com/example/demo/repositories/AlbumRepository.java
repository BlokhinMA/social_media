package com.example.demo.repositories;

import com.example.demo.models.Album;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AlbumRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AlbumRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(Album album) {
        jdbcTemplate.update("INSERT INTO albums(name, user_id) VALUES(?, ?)",
                album.getName(),
                album.getUserId());
    }

    public int findLastId() {
        return jdbcTemplate.queryForObject("SELECT id FROM albums ORDER BY id DESC LIMIT 1", Integer.class);
    }

    public List<Album> findAllByUserId(String username) {
        return jdbcTemplate.query("SELECT * FROM albums WHERE user_id=(SELECT id FROM users WHERE username=?)", new BeanPropertyRowMapper<>(Album.class), username);
    }

}
