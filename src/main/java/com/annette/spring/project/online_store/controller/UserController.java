package com.annette.spring.project.online_store.controller;

import java.util.List;
import java.util.Map;

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
import com.annette.spring.project.online_store.exception_handling.UserBadAuthoritiesException;
import com.annette.spring.project.online_store.service.UserService;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/users")
    public List<Map<String, Object>> getAllUsers() {

        return userService.getAllUsers();

    }

    @PreAuthorize("hasAnyRole('ROLE_CUSTOMER', 'ROLE_SELLER', 'ROLE_ADMIN')")
    @GetMapping("/users/{id}")
    public Map<String, Object> getUser(@PathVariable int id, Authentication authentication) { 

        User currentUser = userService.getUserByLogin(authentication.getName());

        if (currentUser.getId() == id) 
            return userService.getUser(id);
        else 
            throw new UserBadAuthoritiesException(
                "Вы не можете получить данные этого пользователя");

    }

    @PreAuthorize("hasAnyRole('ROLE_CUSTOMER', 'ROLE_SELLER', 'ROLE_ADMIN')")
    @GetMapping("/users/settings/{id}")
    public Settings getUserSettings(@PathVariable(name = "id") int userId, Authentication authentication) {

        User currentUser = userService.getUserByLogin(authentication.getName());

        if (currentUser.getId() == userId) {
            return userService.getUserSettings(userId);
        }
        else 
            throw new UserBadAuthoritiesException(
                "Вы не можете получить данные о настройках этого пользователя");

    }

    @PostMapping("/users")
    public User addUser(@RequestBody User user) {

        userService.addUser(user);

        return user;

    }

    @PreAuthorize("hasAnyRole('ROLE_CUSTOMER', 'ROLE_SELLER', 'ROLE_ADMIN')")
    @PutMapping("/users")
    public String updateUser(@RequestBody String fields, @RequestParam(name = "id") int id, 
        Authentication authentication) {

        User currentUser = userService.getUserByLogin(authentication.getName());

        if (currentUser.getRole().equals("ROLE_ADMIN")) {
            userService.updateUser(fields, id);
            return "Пользователь с id = " + id + " был обновлён";
        } 
        else {
            if (currentUser.getId() == id) {
                userService.updateUser(fields, id);
                return "Пользователь с id = " + id + " был обновлён";
            } 
            else 
                throw new UserBadAuthoritiesException(
                    "Вы не можете обновить этого пользователя");
        }

    }

    @PreAuthorize("hasAnyRole('ROLE_CUSTOMER', 'ROLE_SELLER', 'ROLE_ADMIN')")
    @PutMapping("/users/settings/id")
    public String updateUserSettings(@RequestBody String fields, @RequestParam int userId, 
        Authentication authentication) {

            User currentUser = userService.getUserByLogin(authentication.getName());

            if (currentUser.getId() == userId) {
                userService.updateUserSettings(fields, userId);
                return "Настройки пользователя с id = " + userId + " были обновлены";
            }
            else throw new UserBadAuthoritiesException(
                "Вы не можете обновить настройки этого пользователя");

    }

    @PreAuthorize("hasAnyRole('ROLE_CUSTOMER', 'ROLE_SELLER', 'ROLE_ADMIN')")
    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable int id, Authentication authentication) {

        User currentUser = userService.getUserByLogin(authentication.getName());

        if (currentUser.getRole().equals("ROLE_ADMIN")) {
            userService.deleteUser(id);
            return "Пользователь с id " + id + " был удалён";
        }
        else {
            if (currentUser.getId() == id) {
                userService.deleteUser(id);
                return "Пользователь с id " + id + " был удалён";
            }
            else 
                throw new UserBadAuthoritiesException(
                    "Вы не можете удалить этого пользователя");
        }

    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/users/balance")
    public String refillBalance(@RequestBody String userData) {

        return userService.refillBalance(userData);

    }

    @PreAuthorize("hasAnyRole('ROLE_CUSTOMER', 'ROLE_SELLER', 'ROLE_ADMIN')")
    @PostMapping("/users/purchase/{id}")
    public String purchaseProducts(@PathVariable(name = "id") int userId, Authentication authentication) {

        User user = userService.getUserByLogin(authentication.getName());

        if (user.getId() == userId) 
            return userService.purchaseProducts(userId);
        else
            throw new UserBadAuthoritiesException(
                "Вы не можете совершить покупку от имени этого пользователя");

    }

}
