package com.example.lab12.controller;

import com.example.lab12.model.User;
import com.example.lab12.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    // Redirect root to home (Spring Security will redirect to /login if not authenticated)
    @GetMapping("/")
    public String root() {
        return "redirect:/home";
    }

    // Show login page — Spring Security handles the POST /login submission automatically
    @GetMapping("/login")
    public String showLoginPage(@RequestParam(required = false) String logout, Model model) {
        if (logout != null) {
            model.addAttribute("successMessage", "You have been logged out successfully.");
        }
        return "login";
    }

    // Show home page — @AuthenticationPrincipal injects the currently logged-in user
    @GetMapping("/home")
    public String showHomePage(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("firstName", user.getFirstName());
        model.addAttribute("lastName", user.getLastName());
        return "home";
    }

    // Show signup page
    @GetMapping("/signup")
    public String showSignupPage(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    // Handle signup — encodes password and saves user, then redirects to login
    @PostMapping("/signup")
    public String handleSignup(@ModelAttribute User user) {
        log.info("Registering new user: {}", user.getUsername());
        userService.addUser(user);
        return "redirect:/login?registered=true";
    }
}
