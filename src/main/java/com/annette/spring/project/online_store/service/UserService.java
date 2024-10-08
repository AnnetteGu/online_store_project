package com.annette.spring.project.online_store.service;

import java.util.List;
import java.util.Map;

import com.annette.spring.project.online_store.entity.Settings;
import com.annette.spring.project.online_store.entity.User;

public interface UserService {

    public List<Map<String, Object>> getAllUsers();

    public Map<String, Object> getUser(int id);

    public User getUserByLogin(String login);

    public Settings getUserSettings(int id);

    public User addUser(User user);

    public User updateUser(String fields, int id);

    public User updateUserSettings(String fields, int id);

    public void deleteUser(int id);

    public String refillBalance(String data);

    public String purchaseProducts(int userId);

}
