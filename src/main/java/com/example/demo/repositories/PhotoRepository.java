package com.example.demo.repositories;

import com.example.demo.models.Photo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PhotoRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PhotoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Photo> save(List<Photo> photos) {
        for (Photo photo : photos) {
            jdbcTemplate.update("INSERT INTO photos(name, original_file_name, size, content_type, bytes, album_id) VALUES(?, ?, ?, ?, ?, ?)",
                    photo.getName(),
                    photo.getOriginalFileName(),
                    photo.getSize(),
                    photo.getContentType(),
                    photo.getBytes(),
                    photo.getAlbumId());
        }
        return jdbcTemplate.query("SELECT * FROM community_members ORDER BY id DESC LIMIT ?", new BeanPropertyRowMapper<>(Photo.class), photos.size());
    }

    public List<Photo> findAllByAlbumId(int albumId) {
        return jdbcTemplate.query("SELECT * FROM photos WHERE album_id=?", new BeanPropertyRowMapper<>(Photo.class), albumId);
    }

    public Photo findById(int id) {
        return jdbcTemplate.query("SELECT * FROM photos WHERE id=?", new BeanPropertyRowMapper<>(Photo.class), id)
                .stream().findAny().orElse(null);
    }

}
