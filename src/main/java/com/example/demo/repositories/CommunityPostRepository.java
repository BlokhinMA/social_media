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

    /*public void save(String userUsername, CommunityMember member) {
        jdbcTemplate.update("INSERT INTO members(member_id, community_id) VALUES((SELECT id FROM users WHERE username=?), ?)", userUsername, member.getCommunityId());
    }*/

    public List<CommunityPost> findAllByCommunityId(int communityId) {
        return jdbcTemplate.query("SELECT * FROM community_posts WHERE community_id=?", new BeanPropertyRowMapper<>(CommunityPost.class), communityId);
    }

    //public List<>

}
