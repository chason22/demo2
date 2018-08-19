package cn.creditease.service;

import cn.creditease.dao.IUserDAO;
import cn.creditease.entity.CallLog;
import cn.creditease.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService{

    @Autowired
    private IUserDAO iUserDAO;

    @Override
    public List<User> getAllUsers() {
        return iUserDAO.getAllUsers();
    }

    @Override
    public User getUserByUuid(String uuid) {
        return iUserDAO.getUserByUuid(uuid);
    }

    @Override
    public void addUser(User user) {
        iUserDAO.addUser(user);
    }
}
