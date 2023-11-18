package com.example.demo.repositories;

import com.example.demo.models.CommunityPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CommunityPostRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CommunityPostRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public CommunityPost save(CommunityPost communityPost) {
        jdbcTemplate.update("INSERT INTO Community_post(post_text, author_login, community_id) VALUES(?, ?, ?)",
                communityPost.getPostText(),
                communityPost.getAuthorLogin(),
                communityPost.getCommunityId());
        return jdbcTemplate.query("SELECT * FROM Community_post ORDER BY id DESC LIMIT 1", new BeanPropertyRowMapper<>(CommunityPost.class))
                .stream().findAny().orElse(null);
    }

    public List<CommunityPost> findAllByCommunityId(int communityId) {
        return jdbcTemplate.query("SELECT * FROM Community_post WHERE community_id=? ORDER BY creation_time_stamp DESC", new BeanPropertyRowMapper<>(CommunityPost.class),
                communityId);
    }

    public CommunityPost findById(int id) {
        return jdbcTemplate.query("SELECT * FROM Community_post WHERE id=?", new BeanPropertyRowMapper<>(CommunityPost.class), id)
                .stream().findAny().orElse(null);
    }

    public CommunityPost delete(CommunityPost communityPost) {
        CommunityPost deletedCommunityPost = findById(communityPost.getId());
        jdbcTemplate.update("DELETE FROM Community_post WHERE id=?",
                communityPost.getId());
        return deletedCommunityPost;
    }

}
