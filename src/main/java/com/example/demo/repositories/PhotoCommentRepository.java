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
        jdbcTemplate.update("INSERT INTO Photo_comment(comment, commenting_user_login, photo_id) VALUES(?, ?, ?)",
                photoComment.getComment(),
                photoComment.getCommentingUserLogin(),
                photoComment.getPhotoId());
        return jdbcTemplate.query("SELECT * FROM Photo_comment ORDER BY id DESC LIMIT 1", new BeanPropertyRowMapper<>(PhotoComment.class))
                .stream().findAny().orElse(null);
    }

    public List<PhotoComment> findAllByPhotoId(int photoId) {
        return jdbcTemplate.query("SELECT * FROM Photo_comment WHERE photo_id=? ORDER BY commenting_time_stamp DESC", new BeanPropertyRowMapper<>(PhotoComment.class),
                photoId);
    }

    public PhotoComment findById(int id) {
        return jdbcTemplate.query("SELECT * FROM Photo_comment WHERE id=?", new BeanPropertyRowMapper<>(PhotoComment.class),
                        id)
                .stream().findAny().orElse(null);
    }

    public PhotoComment deleteById(int id) {
        PhotoComment deletedPhotoComment = findById(id);
        jdbcTemplate.update("DELETE FROM Photo_comment WHERE id=?",
                id);
        return deletedPhotoComment;
    }

}
