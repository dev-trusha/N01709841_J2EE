package com.campus.service;

import jakarta.ejb.Stateless;
import java.util.Map;

@Stateless
public class UserServiceBean {

    private static final Map<String, String> PASSWORDS = Map.of(
            "admin", "adminpass",
            "student", "studentpass"
    );

    private static final Map<String, String> ROLES = Map.of(
            "admin", "admin",
            "student", "user"
    );

    public boolean isValidUser(String username, String password) {
        return username != null && password != null
                && PASSWORDS.containsKey(username)
                && PASSWORDS.get(username).equals(password);
    }

    public String getRole(String username) {
        return ROLES.get(username);
    }
}