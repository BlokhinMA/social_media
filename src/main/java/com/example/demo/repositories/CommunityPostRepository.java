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
        return jdbcTemplate.query("call save_community_post(?, ?, ?)", new BeanPropertyRowMapper<>(CommunityPost.class),
                        communityPost.getPostText(),
                        communityPost.getAuthorLogin(),
                        communityPost.getCommunityId())
                .stream().findAny().orElse(null);
    }

    public List<CommunityPost> findAllByCommunityId(int communityId) {
        return jdbcTemplate.query("call find_community_posts_by_community_id(?)", new BeanPropertyRowMapper<>(CommunityPost.class),
                communityId);
    }

    public CommunityPost deleteById(int id) {
        return jdbcTemplate.query("call delete_community_post_by_id(?)", new BeanPropertyRowMapper<>(CommunityPost.class),
                        id)
                .stream().findAny().orElse(null);
    }

}
