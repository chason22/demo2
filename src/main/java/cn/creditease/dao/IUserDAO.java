package cn.creditease.dao;

import cn.creditease.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;


public interface IUserDAO {
    List<User> getAllUsers();
    User getUserByUuid(String uuid);
    void addUser(User user);
}
