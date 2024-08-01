package com.annette.spring.project.online_store.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.annette.spring.project.online_store.repository.UserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {
    
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return userRepository
            .findByLogin(username)
            .map(MyUserDetails::new)
            .orElseThrow(() -> 
                new UsernameNotFoundException("User's login " + username + " doesn't exist"));
        
    }

}
