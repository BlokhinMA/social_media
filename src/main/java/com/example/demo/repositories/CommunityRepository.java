package com.example.demo.repositories;

import com.example.demo.models.Community;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommunityRepository {

    private final JdbcTemplate jdbcTemplate;

    public Community save(Community community) {
        return jdbcTemplate.query("call save_community(?, ?)", new BeanPropertyRowMapper<>(Community.class),
                        community.getName(),
                        community.getCreatorLogin())
                .stream().findAny().orElse(null);
    }

    public List<Community> findAllByMemberLogin(String memberLogin) {
        return jdbcTemplate.query("call find_communities_by_member_login(?)", new BeanPropertyRowMapper<>(Community.class),
                memberLogin);
    }

    public List<Community> findAllByCreatorLogin(String creatorLogin) {
        return jdbcTemplate.query("call find_communities_by_creator_login(?)", new BeanPropertyRowMapper<>(Community.class),
                creatorLogin);
    }

    public Community findById(int id) {
        return jdbcTemplate.query("call find_community_by_id(?)", new BeanPropertyRowMapper<>(Community.class),
                        id)
                .stream().findAny().orElse(null);
    }

    public Community deleteById(int id) {
        return jdbcTemplate.query("call delete_community_by_id(?)", new BeanPropertyRowMapper<>(Community.class),
                        id)
                .stream().findAny().orElse(null);
    }

    public List<Community> findAllLikeName(String word) {
        return jdbcTemplate.query("call find_communities_like_name(?)", new BeanPropertyRowMapper<>(Community.class),
                word);
    }

}
