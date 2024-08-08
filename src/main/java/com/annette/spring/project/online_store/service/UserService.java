package com.annette.spring.project.online_store.service;

import java.util.List;

import com.annette.spring.project.online_store.entity.Settings;
import com.annette.spring.project.online_store.entity.User;

public interface UserService {

    public List<User> getAllUsers();

    public User getUser(int id);

    public User getUserByLogin(String login);

    public Settings getUserSettings(int id);

    public User saveUser(User user);

    public User updateUser(String fields, int id);

    public User updateUserSettings(String fields, int id);

    public void deleteUser(int id);
    

}
