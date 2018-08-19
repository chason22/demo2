package cn.creditease.dao;

import cn.creditease.entity.CallLog;
import cn.creditease.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class UserDAO implements IUserDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<User> getAllUsers() {
        String sql = "SELECT * FROM user";
        RowMapper<User> rowMapper = new BeanPropertyRowMapper<User>(User.class);
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public User getUserByUuid(String uuid) {
        String sql = "SELECT * FROM user WHERE uuid = ?";
        RowMapper<User> rowMapper = new BeanPropertyRowMapper<User>(User.class);
        User user = jdbcTemplate.queryForObject(sql, rowMapper, uuid);
        return user;
    }

    @Override
    public void addUser(User user) {
        String sql = "INSERT INTO user (uuid, created_at, phone, name, id_no) values (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, user.getUuid(), user.getCreated_at(), user.getPhone(), user.getName(), user.getId_no());
    }
}
