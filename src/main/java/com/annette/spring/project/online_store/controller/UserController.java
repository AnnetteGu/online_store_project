package com.annette.spring.project.online_store.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.annette.spring.project.online_store.entity.Settings;
import com.annette.spring.project.online_store.entity.User;
import com.annette.spring.project.online_store.service.UserService;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/users")
    public List<User> getAllUsers() {

        return userService.getAllUsers();

    }

    @PreAuthorize("hasAnyRole('ROLE_CUSTOMER', 'ROLE_SELLER', 'ROLE_ADMIN')")
    @GetMapping("/users/{id}")
    public User getUser(@PathVariable int id, Authentication authentication) { 

        User currentUser = userService.getUserByLogin(authentication.getName());

        if (currentUser.getId() == id) 
            return userService.getUser(id);
        else {
            System.out.println("Вы не можете получить данные этого пользователя");
            return currentUser;
        }

    }

    @PreAuthorize("hasAnyRole('ROLE_CUSTOMER', 'ROLE_SELLER', 'ROLE_ADMIN')")
    @GetMapping("/users/settings/{id}")
    public Settings getUserSettings(@PathVariable(name = "id") int userId, Authentication authentication) {

        User currentUser = userService.getUserByLogin(authentication.getName());

        if (currentUser.getId() == userId) {
            return userService.getUserSettings(userId);
        }
        else {
            System.out.println("You cannot get settings of this user");
            return userService.getUserSettings(currentUser.getId());
        }

    }

    @PostMapping("/users")
    public User saveUser(@RequestBody User user) {

        userService.saveUser(user);

        return user;

    }

    @PreAuthorize("hasAnyRole('ROLE_CUSTOMER', 'ROLE_SELLER', 'ROLE_ADMIN')")
    @PutMapping("/users/id")
    public String updateUser(@RequestBody String fields, @RequestParam int id, 
        Authentication authentication) {

        User currentUser = userService.getUserByLogin(authentication.getName());

        if (currentUser.getRole().equals("ROLE_ADMIN")) {
            userService.updateUser(fields, id);
            return "User with id = " + id + " was updated";
        } 
        else {
            if (currentUser.getId() == id) {
                userService.updateUser(fields, id);
                return "User with id = " + id + " was updated";
            } 
            else {
                return "You cannot update this user";
            }
        }

    }

    @PreAuthorize("hasAnyRole('ROLE_CUSTOMER', 'ROLE_SELLER', 'ROLE_ADMIN')")
    @PutMapping("/users/settings/id")
    public String updateUserSettings(@RequestBody String fields, @RequestParam int userId, 
        Authentication authentication) {

            User currentUser = userService.getUserByLogin(authentication.getName());

            if (currentUser.getId() == userId) {
                userService.updateUserSettings(fields, userId);
                return "Settings of user with id = " + userId + " was updated";
            }
            else {
                return "You cannot update this user";
            }

    }

    @PreAuthorize("hasAnyRole('ROLE_CUSTOMER', 'ROLE_SELLER', 'ROLE_ADMIN')")
    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable int id, Authentication authentication) {

        User currentUser = userService.getUserByLogin(authentication.getName());

        if (currentUser.getRole().equals("ROLE_ADMIN")) {
            userService.deleteUser(id);
            return "User with id " + id + " was deleted";
        }
        else {
            if (currentUser.getId() == id) {
                userService.deleteUser(id);
                return "User with id " + id + " was deleted";
            }
            else {
                return "You cannot delete this user";
            }
        }

    }

}
