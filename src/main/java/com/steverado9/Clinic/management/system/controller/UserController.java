package com.steverado9.Clinic.management.system.controller;

import com.steverado9.Clinic.management.system.entity.User;
import com.steverado9.Clinic.management.system.service.UserService;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    private PasswordEncoder passwordEncoder;
    private UserService userService;

    public UserController( UserService userService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginPage(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "login";
    }
}
