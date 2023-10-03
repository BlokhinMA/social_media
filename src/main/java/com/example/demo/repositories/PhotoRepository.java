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

    public void save(List<Photo> photos) {
        for (Photo photo : photos) {
            jdbcTemplate.update("INSERT INTO photos(name, original_file_name, size, content_type, bytes, creation_date, user_id) VALUES(?, ?, ?, ?, ?, ?, ?)",
                    photo.getName(),
                    photo.getOriginalFileName(),
                    photo.getSize(),
                    photo.getContentType(),
                    photo.getBytes(),
                    photo.getCreationDate(),
                    photo.getUserId());
        }
    }

    public List<Photo> findAllByUserId(int userId) {
        return jdbcTemplate.query("SELECT * FROM photos WHERE user_id=?", new BeanPropertyRowMapper<>(Photo.class), userId);
    }

    public Photo findById(int photoId) {
        return jdbcTemplate.query("SELECT * FROM photos WHERE photo_id=?", new BeanPropertyRowMapper<>(Photo.class), photoId).stream().findAny().orElse(null);
    }

}
