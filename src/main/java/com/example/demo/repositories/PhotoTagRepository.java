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
        return jdbcTemplate.query("call save_photo_tag(?, ?)", new BeanPropertyRowMapper<>(PhotoTag.class),
                        photoTag.getTag(),
                        photoTag.getPhotoId())
                .stream().findAny().orElse(null);
    }

    public List<PhotoTag> findAllByPhotoId(int photoId) {
        return jdbcTemplate.query("call find_photo_tags_by_photo_id(?)", new BeanPropertyRowMapper<>(PhotoTag.class),
                photoId);
    }

    public PhotoTag findByTagAndPhotoId(PhotoTag photoTag) {
        return jdbcTemplate.query("call find_photo_tag_by_tag_and_photo_id(?, ?)", new BeanPropertyRowMapper<>(PhotoTag.class),
                        photoTag.getTag(),
                        photoTag.getPhotoId())
                .stream().findAny().orElse(null);
    }

    public PhotoTag deleteById(int id) {
        return jdbcTemplate.query("call delete_photo_tag_by_id(?)", new BeanPropertyRowMapper<>(PhotoTag.class),
                        id)
                .stream().findAny().orElse(null);
    }

}
