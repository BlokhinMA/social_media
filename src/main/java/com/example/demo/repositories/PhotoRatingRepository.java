package com.example.demo.repositories;

import com.example.demo.models.PhotoRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class PhotoRatingRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PhotoRatingRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<PhotoRating> findAllByPhotoId(int photoId) {
        return jdbcTemplate.query("SELECT * FROM photo_ratings WHERE photo_id=?", new BeanPropertyRowMapper<>(PhotoRating.class), photoId);
    }

}
