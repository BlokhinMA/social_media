package com.example.demo.repositories;

import com.example.demo.models.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RoleRepository {

    private final JdbcTemplate jdbcTemplate;

    public String save(Role role, String userLogin) {
        return jdbcTemplate.queryForObject("call save_role(?, ?)", String.class,
                role.toString(),
                userLogin);
    }

}
