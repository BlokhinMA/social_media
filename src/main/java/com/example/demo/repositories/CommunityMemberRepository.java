package com.example.demo.repositories;

import com.example.demo.models.CommunityMember;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommunityMemberRepository {

    private final JdbcTemplate jdbcTemplate;

    public CommunityMember save(CommunityMember communityMember) {
        /*jdbcTemplate.update("INSERT INTO Community_member(member_login, community_id) VALUES(?, ?)",
                communityMember.getMemberLogin(),
                communityMember.getCommunityId());
        return jdbcTemplate.query("SELECT * FROM Community_member ORDER BY id DESC LIMIT 1", new BeanPropertyRowMapper<>(CommunityMember.class))
                .stream().findAny().orElse(null);*/
        return jdbcTemplate.query("call save_community_member(?, ?)", new BeanPropertyRowMapper<>(CommunityMember.class),
                        communityMember.getMemberLogin(),
                        communityMember.getCommunityId())
                .stream().findAny().orElse(null);
    }

    public List<CommunityMember> findAllByCommunityId(int communityId) {
        /*return jdbcTemplate.query("SELECT * FROM Community_member WHERE community_id=?", new BeanPropertyRowMapper<>(CommunityMember.class),
                communityId);*/
        return jdbcTemplate.query("call find_community_members_by_community_id(?)", new BeanPropertyRowMapper<>(CommunityMember.class),
                communityId);
    }

    public CommunityMember findByMemberLoginAndCommunityId(String memberLogin, int communityId) {
        /*return jdbcTemplate.query("SELECT * FROM Community_member WHERE member_login=? AND community_id=?", new BeanPropertyRowMapper<>(CommunityMember.class),
                        memberLogin,
                        communityId)
                .stream().findAny().orElse(null);*/
        return jdbcTemplate.query("call find_community_member_by_member_login_and_community_id(?, ?)", new BeanPropertyRowMapper<>(CommunityMember.class),
                        memberLogin,
                        communityId)
                .stream().findAny().orElse(null);
    }

    public CommunityMember findById(int id) {
        /*return jdbcTemplate.query("SELECT * FROM Community_member WHERE id=?", new BeanPropertyRowMapper<>(CommunityMember.class),
                        id)
                .stream().findAny().orElse(null);*/
        return jdbcTemplate.query("call find_community_member_by_id(?)", new BeanPropertyRowMapper<>(CommunityMember.class),
                        id)
                .stream().findAny().orElse(null);
    }

    public CommunityMember deleteByMemberLoginAndCommunityId(CommunityMember communityMember) {
        /*CommunityMember deletedCommunityMember = findByMemberLoginAndCommunityId(communityMember.getMemberLogin(), communityMember.getCommunityId());
        jdbcTemplate.update("DELETE FROM Community_member WHERE member_login=? AND community_id=?",
                communityMember.getMemberLogin(),
                communityMember.getCommunityId());
        return deletedCommunityMember;*/
        return jdbcTemplate.query("call delete_community_member_by_member_login_and_community_id(?, ?)", new BeanPropertyRowMapper<>(CommunityMember.class),
                        communityMember.getMemberLogin(),
                        communityMember.getCommunityId())
                .stream().findAny().orElse(null);
    }

    public CommunityMember deleteById(int id) {
        /*CommunityMember deletedCommunityMember = findById(id);
        jdbcTemplate.update("DELETE FROM Community_member WHERE id=?",
                id);
        return deletedCommunityMember;*/
        return jdbcTemplate.query("call delete_community_member_by_id(?)", new BeanPropertyRowMapper<>(CommunityMember.class),
                        id)
                .stream().findAny().orElse(null);
    }

}
