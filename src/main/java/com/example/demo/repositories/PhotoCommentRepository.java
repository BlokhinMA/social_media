package com.example.demo.repositories;

import com.example.demo.models.PhotoComment;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PhotoCommentRepository {

    private final JdbcTemplate jdbcTemplate;

    public PhotoComment save(PhotoComment photoComment) {
        return jdbcTemplate.query("call save_photo_comment(?, ?, ?)", new BeanPropertyRowMapper<>(PhotoComment.class),
                        photoComment.getComment(),
                        photoComment.getCommentingUserLogin(),
                        photoComment.getPhotoId())
                .stream().findAny().orElse(null);
    }

    public List<PhotoComment> findAllByPhotoId(int photoId) {
        return jdbcTemplate.query("call find_photo_comments_by_photo_id(?)", new BeanPropertyRowMapper<>(PhotoComment.class),
                photoId);
    }

    public PhotoComment deleteById(int id) {
        return jdbcTemplate.query("call delete_photo_comment_by_id(?)", new BeanPropertyRowMapper<>(PhotoComment.class),
                        id)
                .stream().findAny().orElse(null);
    }

}
