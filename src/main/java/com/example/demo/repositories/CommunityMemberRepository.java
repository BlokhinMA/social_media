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
        return jdbcTemplate.query("call save_community_member(?, ?)", new BeanPropertyRowMapper<>(CommunityMember.class),
                        communityMember.getMemberLogin(),
                        communityMember.getCommunityId())
                .stream().findAny().orElse(null);
    }

    public List<CommunityMember> findAllByCommunityId(int communityId) {
        return jdbcTemplate.query("call find_community_members_by_community_id(?)", new BeanPropertyRowMapper<>(CommunityMember.class),
                communityId);
    }

    public CommunityMember findByMemberLoginAndCommunityId(String memberLogin, int communityId) {
        return jdbcTemplate.query("call find_community_member_by_member_login_and_community_id(?, ?)", new BeanPropertyRowMapper<>(CommunityMember.class),
                        memberLogin,
                        communityId)
                .stream().findAny().orElse(null);
    }

    public CommunityMember deleteByMemberLoginAndCommunityId(CommunityMember communityMember) {
        return jdbcTemplate.query("call delete_community_member_by_member_login_and_community_id(?, ?)", new BeanPropertyRowMapper<>(CommunityMember.class),
                        communityMember.getMemberLogin(),
                        communityMember.getCommunityId())
                .stream().findAny().orElse(null);
    }

    public CommunityMember deleteById(int id) {
        return jdbcTemplate.query("call delete_community_member_by_id(?)", new BeanPropertyRowMapper<>(CommunityMember.class),
                        id)
                .stream().findAny().orElse(null);
    }

}
