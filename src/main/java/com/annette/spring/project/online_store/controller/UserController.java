package com.annette.spring.project.online_store.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.annette.spring.project.online_store.entity.User;
import com.annette.spring.project.online_store.service.UserService;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public List<User> getAllUsers() {

        return userService.getAllUsers();

    }

    @GetMapping("users/{id}")
    public User getUser(@PathVariable int id) { 

        return userService.getUser(id);

    }

    @GetMapping("users/{login}")
    public User getUserByLogin(@PathVariable String login) {

        return userService.getUserByLogin(login);

    }

    @PostMapping("/users")
    public User saveUser(@RequestBody User user) {

        userService.saveUser(user);

        return user;

    }

    @PutMapping("/users")
    public User updateUser(@RequestBody User user) {

        userService.saveUser(user);

        return user;

    }

    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable int id) {

        userService.deleteUser(id);

        return "User with id " + id + " was deleted";

    }

}
