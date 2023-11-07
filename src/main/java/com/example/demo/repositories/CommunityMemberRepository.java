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
        jdbcTemplate.update("INSERT INTO Community_member(member_login, community_id) VALUES(?, ?)",
                communityMember.getMemberLogin(),
                communityMember.getCommunityId());
        return jdbcTemplate.query("SELECT * FROM Community_member ORDER BY id DESC LIMIT 1", new BeanPropertyRowMapper<>(CommunityMember.class)).stream().findAny().orElse(null);
    }

    public List<CommunityMember> findAllByCommunityId(int communityId) {
        return jdbcTemplate.query("SELECT * FROM Community_member WHERE community_id=?", new BeanPropertyRowMapper<>(CommunityMember.class), communityId);
    }

    public CommunityMember findByMemberLoginAndCommunityId(String memberLogin, int communityId) {
        return jdbcTemplate.query("SELECT * FROM Community_member WHERE member_login=? AND community_id=?", new BeanPropertyRowMapper<>(CommunityMember.class), memberLogin, communityId).stream().findAny().orElse(null);
    }

    public CommunityMember delete(CommunityMember communityMember) {
        CommunityMember deletedCommunityMember = findByMemberLoginAndCommunityId(communityMember.getMemberLogin(), communityMember.getCommunityId());
        jdbcTemplate.update("DELETE FROM Community_member WHERE member_login=? AND community_id=?",
                communityMember.getMemberLogin(),
                communityMember.getCommunityId());
        return deletedCommunityMember;
    }

}
