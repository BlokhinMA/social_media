package com.example.demo.repositories;

import com.example.demo.models.PhotoTag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PhotoTagRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PhotoTagRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<PhotoTag> findAllByPhotoId(int photoId) {
        return jdbcTemplate.query("SELECT * FROM photo_tags WHERE photo_id=?", new BeanPropertyRowMapper<>(PhotoTag.class), photoId);
    }

}
