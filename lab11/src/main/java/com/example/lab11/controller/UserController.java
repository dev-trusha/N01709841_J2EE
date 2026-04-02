package com.example.lab11.controller;

import com.example.lab11.model.User;
import com.example.lab11.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
public class UserController {

    @Autowired
    UserService userService;

    // Redirect root to login
    @GetMapping("/")
    public String root() {
        return "redirect:/login";
    }

    // Show login page
    @GetMapping("/login")
    public String showLoginPage(@RequestParam(required = false) String registered, Model model) {
        if ("true".equals(registered)) {
            model.addAttribute("successMessage", "Please login now !!");
        }
        return "login";
    }

    // Handle login form submission
    @PostMapping("/login")
    public String handleLogin(@RequestParam String username,
                              @RequestParam String password,
                              HttpSession session,
                              Model model) {
        log.info("Login attempt for username: {}", username);
        User user = userService.userLogin(username, password);
        if (user != null) {
            session.setAttribute("loggedInUser", user);
            return "redirect:/home";
        } else {
            model.addAttribute("errorMessage", "Invalid Username/Password");
            return "login";
        }
    }

    // Show home page after successful login
    @GetMapping("/home")
    public String showHomePage(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/login";
        }
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

    // Handle signup form submission
    @PostMapping("/signup")
    public String handleSignup(@ModelAttribute User user, RedirectAttributes redirectAttributes) {
        log.info("Registering new user: {}", user.getUsername());
        userService.addUser(user);
        return "redirect:/login?registered=true";
    }

    // Logout
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
