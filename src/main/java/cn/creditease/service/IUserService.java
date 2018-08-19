package cn.creditease.service;

import cn.creditease.entity.User;

import java.util.List;

public interface IUserService {
    List<User> getAllUsers();
    User getUserByUuid(String uuid);
    void addUser(User user);
}
