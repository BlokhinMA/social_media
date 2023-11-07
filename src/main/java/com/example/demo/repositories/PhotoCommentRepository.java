package com.example.demo.repositories;

import com.example.demo.models.PhotoComment;
import com.example.demo.models.PhotoTag;
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

    public PhotoComment save(PhotoComment photoComment) {
        jdbcTemplate.update("INSERT INTO Photo_comment(comment, commenting_user_login, photo_id) VALUES(?, ?, ?)",
                photoComment.getComment(),
                photoComment.getCommentingUserLogin(),
                photoComment.getPhotoId());
        return jdbcTemplate.query("SELECT * FROM Photo_comment ORDER BY id DESC LIMIT 1", new BeanPropertyRowMapper<>(PhotoComment.class))
                .stream().findAny().orElse(null);
    }

    public List<PhotoComment> findAllByPhotoId(int photoId) {
        return jdbcTemplate.query("SELECT * FROM Photo_comment WHERE photo_id=?", new BeanPropertyRowMapper<>(PhotoComment.class),
                photoId);
    }

    public PhotoComment findById(int id) {
        return jdbcTemplate.query("SELECT * FROM Photo_comment WHERE id=?", new BeanPropertyRowMapper<>(PhotoComment.class),
                        id)
                .stream().findAny().orElse(null);
    }

    public PhotoComment delete(PhotoComment photoComment) {
        PhotoComment deletedPhotoComment = findById(photoComment.getId());
        jdbcTemplate.update("DELETE FROM Photo_comment WHERE id=?",
                photoComment.getId());
        return deletedPhotoComment;
    }

}
