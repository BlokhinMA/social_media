package com.example.demo.repositories;

import com.example.demo.models.Photo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PhotoRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PhotoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Photo save(Photo photo) {
        jdbcTemplate.update("INSERT INTO Photo(name, original_file_name, size, content_type, bytes, album_id) VALUES(?, ?, ?, ?, ?, ?)",
                photo.getName(),
                photo.getOriginalFileName(),
                photo.getSize(),
                photo.getContentType(),
                photo.getBytes(),
                photo.getAlbumId());

        return jdbcTemplate.query("SELECT * FROM Photo ORDER BY id DESC LIMIT 1", new BeanPropertyRowMapper<>(Photo.class))
                .stream().findAny().orElse(null);
    }

    public List<Photo> findAllByAlbumId(int albumId) {
        return jdbcTemplate.query("SELECT * FROM Photo WHERE album_id=?", new BeanPropertyRowMapper<>(Photo.class),
                albumId);
    }

    public Photo findById(int id) {
        return jdbcTemplate.query("SELECT * FROM Photo WHERE id=?", new BeanPropertyRowMapper<>(Photo.class),
                        id)
                .stream().findAny().orElse(null);
    }

    public Photo delete(Photo photo) {
        Photo deletedPhoto = findById(photo.getId());
        jdbcTemplate.update("DELETE FROM Photo WHERE id=?",
                photo.getId());
        return deletedPhoto;
    }

    public List<Photo> findLikeCreationTimeStamp(String word) {
        return jdbcTemplate.query("SELECT * FROM Photo WHERE creation_time_stamp LIKE CONCAT('%', ?, '%')", new BeanPropertyRowMapper<>(Photo.class), word);
    }

    public List<Photo> findLikeTags(String word) {
        return jdbcTemplate.query("SELECT Photo.* FROM Photo JOIN Photo_tag ON Photo.id = photo_id WHERE tag LIKE CONCAT('%', ?, '%')", new BeanPropertyRowMapper<>(Photo.class), word);
    }

    public List<Photo> findLikeComments(String word) {
        return jdbcTemplate.query("SELECT Photo.* FROM Photo JOIN Photo_comment ON Photo.id = photo_id WHERE comment LIKE CONCAT('%', ?, '%')", new BeanPropertyRowMapper<>(Photo.class), word);
    }

}
