package cn.creditease.entity;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet row, int rowNum) throws SQLException {
            User user = new User();
            user.setUuid(row.getString("uuid"));
            user.setCreated_at(row.getTimestamp("created_at"));
            user.setPhone(row.getString("phone"));
            user.setName(row.getString("name"));
            user.setId_no(row.getString("id_no"));
            return user;
    }
}
