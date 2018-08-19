package cn.creditease.controller;

import cn.creditease.entity.User;
import cn.creditease.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("db")
public class UserController {
    @Autowired
    private IUserService iUserService;

    @GetMapping("user/{uuid}")
    public ResponseEntity<User> getUserByUuid(@PathVariable("uuid") String uuid) {
        User user = iUserService.getUserByUuid(uuid);
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @GetMapping("user")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> list = iUserService.getAllUsers();
        return new ResponseEntity<List<User>>(list, HttpStatus.OK);
    }

    @PostMapping("user")
    public ResponseEntity<Void> addUser(@RequestBody User user) {
        iUserService.addUser(user);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

}