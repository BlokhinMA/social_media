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

    public Album save(Album album) {
        jdbcTemplate.update("INSERT INTO Album(name, user_login) VALUES(?, ?)",
                album.getName(),
                album.getUserLogin());
        return jdbcTemplate.query("SELECT * FROM Album ORDER BY id DESC LIMIT 1", new BeanPropertyRowMapper<>(Album.class))
                .stream().findAny().orElse(null);
    }

    public List<Album> findAllByUserLogin(String userLogin) {
        return jdbcTemplate.query("SELECT * FROM Album WHERE user_login=?", new BeanPropertyRowMapper<>(Album.class), userLogin);
    }

    public Album findById(int id) {
        return jdbcTemplate.query("SELECT * FROM Album WHERE id=?", new BeanPropertyRowMapper<>(Album.class), id).
                stream().findAny().orElse(null);
    }

    public Album deleteById(int id) {
        Album deletedAlbum = findById(id);
        jdbcTemplate.update("DELETE FROM Album WHERE id=?", id);
        return deletedAlbum;
    }

    public List<Album> findLikeName(String word) {
        return jdbcTemplate.query("SELECT * FROM Album WHERE name LIKE ?", new BeanPropertyRowMapper<>(Album.class), word);
    }

}
