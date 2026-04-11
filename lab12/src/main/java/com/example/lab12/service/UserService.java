package com.example.lab12.service;

import com.example.lab12.model.User;
import com.example.lab12.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void addUser(User user) {
        String rawPassword = user.getPassword();
        String encodedPassword = passwordEncoder.encode(rawPassword);
        log.info("=== REGISTERING USER: '{}'", user.getUsername());
        log.info("=== RAW PASSWORD: '{}'", rawPassword);
        log.info("=== ENCODED PASSWORD: '{}'", encodedPassword);
        user.setPassword(encodedPassword);
        userRepository.save(user);
        log.info("=== USER SAVED SUCCESSFULLY");
    }
}
