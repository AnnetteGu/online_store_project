package com.annette.spring.project.online_store.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.annette.spring.project.online_store.entity.User;
import com.annette.spring.project.online_store.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {

        return userRepository.findAll();

    }

    @Override
    public User getUser(int id) {

        Optional<User> optional = userRepository.findById(id);

        User user = null;

        if (optional.isPresent()) user = optional.get();

        return user;
       
    }

    @Override
    public User getUserByLogin(String login) {

        Optional<User> optional = userRepository.findByLogin(login);

        User user = null;

        if (optional.isPresent()) user = optional.get();

        return user;
        
    }

    @Override
    public User saveUser(User user) {

        userRepository.save(user);

        return user;
        
    }

    @SuppressWarnings("unchecked")
    @Override
    public User updateUser(String fields, int id) {

        ObjectMapper objectMapper = new ObjectMapper();

        Map<String, String> resultMap = new LinkedHashMap<>();

        try {
            resultMap = objectMapper.readValue(fields, LinkedHashMap.class);
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());  
        }

        User currentUser = userRepository.findById(id).get();

        for (Map.Entry<String, String> field : resultMap.entrySet()) {
            switch (field.getKey()) {
                case "name":
                    currentUser.setName(field.getValue());    
                    break;
                case "login":
                    currentUser.setLogin(field.getValue());     
                    break;
                case "password":
                    currentUser.setPassword(field.getValue());     
                    break;
                case "balance":
                    currentUser.setBalance(Double.parseDouble(field.getValue()));  
                    break;
                case "role":
                    currentUser.setRole(field.getValue());   
                    break;
                case "accessMode":
                    currentUser.setAccessMode(Boolean.parseBoolean(field.getValue()));     
                    break;
                default:
                    System.out.println("Такого поля нет");
                    break;
            }
        }

        userRepository.save(currentUser);

        return currentUser;
        
    }

    @Override
    public void deleteUser(int id) {

        userRepository.deleteById(id);
        
    }

}
