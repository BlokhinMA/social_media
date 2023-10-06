package com.example.demo.repositories;

import com.example.demo.models.PhotoComment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PhotoCommentRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PhotoCommentRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<PhotoComment> findAllByPhotoId(int photoId) {
        return jdbcTemplate.query("SELECT * FROM photo_comments WHERE photo_id=?", new BeanPropertyRowMapper<>(PhotoComment.class), photoId);
    }

}
