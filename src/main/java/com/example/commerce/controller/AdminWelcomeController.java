package com.example.commerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.commerce.model.Admin;
import com.example.commerce.service.AdminService;

@Controller
public class AdminWelcomeController {

    @Autowired
    private AdminService adminService;

    // SIGNUP
    @PostMapping("/adminSignUp")
    public String userSignUp(@RequestParam String username,
                            @RequestParam String password,
                            Model model) {

        if (adminService.findByUsername(username).isPresent()) {
            model.addAttribute("error", "Username already exists!");
            return "index";
        }

        Admin admin = new Admin();
        admin.setUsername(username);
        admin.setPassword(password); 

        adminService.register(admin);

        model.addAttribute("message", "SignUp successful! Please Login");
        return "index";
    }

    @GetMapping("/adminLogin")
    public String loginPage(@RequestParam(value = "error", required = false) String error,
                            Model model) {

        if (error != null) {
            model.addAttribute("errorMessage", "Invalid username or password!");
        }

      
        return "index";
    }
}