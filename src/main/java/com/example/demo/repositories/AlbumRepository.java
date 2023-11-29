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
        return jdbcTemplate.query("call save_album(?, ?, ?)", new BeanPropertyRowMapper<>(Album.class),
                        album.getName(),
                        album.getAccessType(),
                        album.getUserLogin())
                .stream().findAny().orElse(null);
    }

    public List<Album> findAllByUserLogin(String userLogin) {
        return jdbcTemplate.query("call find_albums_by_user_login(?)", new BeanPropertyRowMapper<>(Album.class),
                userLogin);
    }

    public Album findById(int id) {
        return jdbcTemplate.query("call find_album_by_id(?)", new BeanPropertyRowMapper<>(Album.class),
                        id)
                .stream().findAny().orElse(null);
    }

    public Album deleteById(int id) {
        return jdbcTemplate.query("call delete_album_by_id(?)", new BeanPropertyRowMapper<>(Album.class),
                        id)
                .stream().findAny().orElse(null);
    }

    public List<Album> findAllLikeName(String word) {
        return jdbcTemplate.query("call find_albums_like_name(?)", new BeanPropertyRowMapper<>(Album.class),
                word);
    }

    public Album updateAccessTypeById(Album album) {
        return jdbcTemplate.query("call update_album_access_type_by_id(?, ?)", new BeanPropertyRowMapper<>(Album.class),
                        album.getAccessType(),
                        album.getId())
                .stream().findAny().orElse(null);
    }

}
