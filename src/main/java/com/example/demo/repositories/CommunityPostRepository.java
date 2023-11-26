package com.example.demo.repositories;

import com.example.demo.models.CommunityPost;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommunityPostRepository {

    private final JdbcTemplate jdbcTemplate;

    public CommunityPost save(CommunityPost communityPost) {
        /*jdbcTemplate.update("INSERT INTO Community_post(post_text, author_login, community_id) VALUES(?, ?, ?)",
                communityPost.getPostText(),
                communityPost.getAuthorLogin(),
                communityPost.getCommunityId());
        return jdbcTemplate.query("SELECT * FROM Community_post ORDER BY id DESC LIMIT 1", new BeanPropertyRowMapper<>(CommunityPost.class))
                .stream().findAny().orElse(null);*/
        return jdbcTemplate.query("call save_community_post(?, ?, ?)", new BeanPropertyRowMapper<>(CommunityPost.class),
                        communityPost.getPostText(),
                        communityPost.getAuthorLogin(),
                        communityPost.getCommunityId())
                .stream().findAny().orElse(null);
    }

    public List<CommunityPost> findAllByCommunityId(int communityId) {
        /*return jdbcTemplate.query("SELECT * FROM Community_post WHERE community_id=? ORDER BY creation_time_stamp DESC", new BeanPropertyRowMapper<>(CommunityPost.class),
                communityId);*/
        return jdbcTemplate.query("call find_community_posts_by_community_id(?)", new BeanPropertyRowMapper<>(CommunityPost.class),
                communityId);
    }

    public CommunityPost findById(int id) {
        /*return jdbcTemplate.query("SELECT * FROM Community_post WHERE id=?", new BeanPropertyRowMapper<>(CommunityPost.class),
                        id)
                .stream().findAny().orElse(null);*/
        return jdbcTemplate.query("call find_community_post_by_id(?)", new BeanPropertyRowMapper<>(CommunityPost.class),
                        id)
                .stream().findAny().orElse(null);
    }

    public CommunityPost deleteById(int id) {
        /*CommunityPost deletedCommunityPost = findById(communityPost.getId());
        jdbcTemplate.update("DELETE FROM Community_post WHERE id=?",
                communityPost.getId());
        return deletedCommunityPost;*/
        return jdbcTemplate.query("call delete_community_post_by_id(?)", new BeanPropertyRowMapper<>(CommunityPost.class),
                        id)
                .stream().findAny().orElse(null);
    }

}
