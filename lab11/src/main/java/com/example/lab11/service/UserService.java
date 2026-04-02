package com.example.lab11.service;

import com.example.lab11.model.User;
import com.example.lab11.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public void addUser(User user) {
        log.info("Adding a new user: {}", user.getUsername());
        userRepository.save(user);
    }

    public User userLogin(String username, String password) {
        log.info("Fetching user by name from the database");
        List<User> userList = userRepository.findByUsername(username);
        return userList.stream()
                .filter(user -> user.getPassword().equals(password))
                .findFirst()
                .orElse(null);
    }
}
