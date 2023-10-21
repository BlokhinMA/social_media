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

    public PhotoTag save(PhotoTag photoTag) {
        jdbcTemplate.update("INSERT INTO photo_tags(tag, photo_id) VALUES(?, ?)", photoTag.getTag(), photoTag.getPhotoId());
        return jdbcTemplate.query("SELECT * FROM photo_tags ORDER BY id DESC LIMIT 1", new BeanPropertyRowMapper<>(PhotoTag.class)).stream().findAny().orElse(null);
    }

    public List<PhotoTag> findAllByPhotoId(int photoId) {
        return jdbcTemplate.query("SELECT * FROM photo_tags WHERE photo_id=?", new BeanPropertyRowMapper<>(PhotoTag.class), photoId);
    }

}
