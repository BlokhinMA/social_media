package com.example.demo.repositories;

import com.example.demo.models.Album;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class AlbumRepository {

    private final JdbcTemplate jdbcTemplate;

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
        return jdbcTemplate.query("SELECT * FROM Album WHERE name LIKE CONCAT('%', ?, '%')", new BeanPropertyRowMapper<>(Album.class), word);
    }

    public Album updateAccessTypeById(Album album) {
        jdbcTemplate.update("UPDATE Album SET access_type=? WHERE id=?",
                album.getAccessType(),
                album.getId());
        return findById(album.getId());
    }

}
