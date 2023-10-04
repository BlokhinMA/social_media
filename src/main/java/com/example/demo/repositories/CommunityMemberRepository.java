package com.example.demo.repositories;

import com.example.demo.models.CommunityMember;
import com.example.demo.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CommunityMemberRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CommunityMemberRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(String userUsername, CommunityMember communityMember) {
        jdbcTemplate.update("INSERT INTO community_members(member_id, community_id) VALUES((SELECT id FROM users WHERE username=?), ?)", userUsername, communityMember.getCommunityId());
    }

    public List<User> findAllByCommunityId(int communityId) {
        return jdbcTemplate.query("SELECT id, username, first_name, last_name FROM users JOIN (SELECT member_id FROM community_members WHERE community_id=?) AS M ON users.id = M.member_id", new BeanPropertyRowMapper<>(User.class), communityId);
    }

}
