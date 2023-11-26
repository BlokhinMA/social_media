package com.example.demo.repositories;

import com.example.demo.models.PhotoTag;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor

public class PhotoTagRepository {

    private final JdbcTemplate jdbcTemplate;

    public PhotoTag save(PhotoTag photoTag) {
        jdbcTemplate.update("INSERT INTO Photo_tag(tag, photo_id) VALUES(?, ?)",
                photoTag.getTag(),
                photoTag.getPhotoId());
        return jdbcTemplate.query("SELECT * FROM Photo_tag ORDER BY id DESC LIMIT 1", new BeanPropertyRowMapper<>(PhotoTag.class))
                .stream().findAny().orElse(null);
    }

    public List<PhotoTag> findAllByPhotoId(int photoId) {
        return jdbcTemplate.query("SELECT * FROM Photo_tag WHERE photo_id=?", new BeanPropertyRowMapper<>(PhotoTag.class),
                photoId);
    }

    public PhotoTag findById(int id) {
        return jdbcTemplate.query("SELECT * FROM Photo_tag WHERE id=?", new BeanPropertyRowMapper<>(PhotoTag.class),
                        id)
                .stream().findAny().orElse(null);
    }

    public PhotoTag findByTagAndPhotoId(PhotoTag photoTag) {
        return jdbcTemplate.query("SELECT * FROM Photo_tag WHERE tag=? AND photo_id=?", new BeanPropertyRowMapper<>(PhotoTag.class),
                        photoTag.getTag(),
                        photoTag.getPhotoId())
                .stream().findAny().orElse(null);
    }

    public PhotoTag deleteById(int id) {
        PhotoTag deletedPhotoTag = findById(id);
        jdbcTemplate.update("DELETE FROM Photo_tag WHERE id=?",
                id);
        return deletedPhotoTag;
    }

}
