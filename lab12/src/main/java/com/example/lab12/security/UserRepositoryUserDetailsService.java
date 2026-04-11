package com.example.lab12.security;

import com.example.lab12.model.User;
import com.example.lab12.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserRepositoryUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserRepositoryUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("=== LOADING USER: '{}'", username);

        User user = userRepository.findByUsername(username).orElse(null);

        if (user == null) {
            log.error("=== USER '{}' NOT FOUND IN DATABASE", username);
            throw new UsernameNotFoundException("User '" + username + "' not found");
        }

        log.info("=== USER FOUND: '{}'", user.getUsername());
        log.info("=== STORED PASSWORD HASH: '{}'", user.getPassword());
        return user;
    }
}
