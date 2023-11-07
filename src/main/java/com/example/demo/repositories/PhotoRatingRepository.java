package com.example.demo.repositories;

import com.example.demo.models.PhotoRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PhotoRatingRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PhotoRatingRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public PhotoRating findByRatingUserLoginAndPhotoId(String ratingUserLogin, int photoId) {
        return jdbcTemplate.query("SELECT * FROM Photo_rating WHERE rating_user_login=? AND photo_id=?", new BeanPropertyRowMapper<>(PhotoRating.class),
                        ratingUserLogin,
                        photoId)
                .stream().findAny().orElse(null);
    }

    public Double rating(int photoId) {
        return jdbcTemplate.queryForObject("SELECT AVG(rating) FROM photo_rating WHERE photo_id=?", Double.class,
                photoId);
    }

    public PhotoRating findById(int id) {
        return jdbcTemplate.query("SELECT * FROM Photo_rating WHERE id=?", new BeanPropertyRowMapper<>(PhotoRating.class),
                        id).
                stream().findAny().orElse(null);
    }

    public PhotoRating save(PhotoRating photoRating) {
        jdbcTemplate.update("INSERT INTO Photo_rating(rating, rating_user_login, photo_id) values(?, ?, ?)",
                photoRating.isRating(),
                photoRating.getRatingUserLogin(),
                photoRating.getPhotoId());
        return jdbcTemplate.query("SELECT * FROM Photo_rating ORDER BY id DESC LIMIT 1", new BeanPropertyRowMapper<>(PhotoRating.class))
                .stream().findAny().orElse(null);
    }

    public PhotoRating update(PhotoRating photoRating) {
        jdbcTemplate.update("UPDATE Photo_rating SET rating=? WHERE id=?",
                photoRating.isRating(),
                photoRating.getId());
        return jdbcTemplate.query("SELECT * FROM Photo_rating WHERE id=?", new BeanPropertyRowMapper<>(PhotoRating.class),
                        photoRating.getId())
                .stream().findAny().orElse(null);
    }

    public PhotoRating delete(PhotoRating photoRating) {
        PhotoRating deletedPhotoRating = findById(photoRating.getId());
        jdbcTemplate.update("DELETE FROM Photo_rating WHERE id=?", photoRating.getId());
        return deletedPhotoRating;
    }

}
