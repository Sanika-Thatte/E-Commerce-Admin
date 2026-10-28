package com.example.customer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.customer.model.User;
import com.example.customer.service.UserService;



@Controller
public class UserWelcomeController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    // SIGNUP
    @PostMapping("/userSignUp")
    public String userSignUp(@RequestParam String username,
                            @RequestParam String password,
                            Model model) {

        if (userService.findUsername(username) != null) {
            model.addAttribute("error", "Username already exists!");
            return "userWelcome";
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));

        userService.register(user);  

        model.addAttribute("message", "SignUp successful! Please Login");
        return "userWelcome";
    }

    // SHOW LOGIN PAGE
    @GetMapping("/userLogin")
    public String loginPage() {
        return "userWelcome";
    }
}