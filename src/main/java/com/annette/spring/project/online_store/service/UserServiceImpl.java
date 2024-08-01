package com.annette.spring.project.online_store.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.annette.spring.project.online_store.entity.User;
import com.annette.spring.project.online_store.repository.UserRepository;

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

    @Override
    public void deleteUser(int id) {

        userRepository.deleteById(id);
        
    }

}
