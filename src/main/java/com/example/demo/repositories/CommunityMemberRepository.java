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

    public CommunityMember save(CommunityMember communityMember) {
        jdbcTemplate.update("INSERT INTO community_members(member_login, community_id) VALUES(?, ?)",
                communityMember.getMemberLogin(),
                communityMember.getCommunityId());
        return jdbcTemplate.query("SELECT * FROM community_members ORDER BY id DESC LIMIT 1", new BeanPropertyRowMapper<>(CommunityMember.class)).stream().findAny().orElse(null);
    }

    public List<User> findAllByCommunityId(int communityId) {
        return jdbcTemplate.query("SELECT users.* FROM users JOIN community_members ON login=member_login WHERE community_id=?", new BeanPropertyRowMapper<>(User.class), communityId);
    }

}
