package com.steverado9.Clinic.management.system.controller;

import com.steverado9.Clinic.management.system.customUserDetails.CustomUserDetails;
import com.steverado9.Clinic.management.system.entity.User;
import com.steverado9.Clinic.management.system.service.UserService;

import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collections;

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
    public String loginPage(Model model, Authentication authentication, HttpSession session) {
        User user = new User();
        model.addAttribute("user", user);

        if (authentication != null && !(authentication.getPrincipal() instanceof String)) {

            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

            for (GrantedAuthority role : userDetails.getAuthorities()) {
                if (role.getAuthority().equalsIgnoreCase("ROLE_ADMIN")) {
                    return "redirect:/admin/dashboard";
                } else if (role.getAuthority().equalsIgnoreCase("ROLE_RECEPTIONIST")) {
                    return "redirect:/reception/dashboard";
                } else if (role.getAuthority().equalsIgnoreCase("ROLE_DOCTOR")) {
                    return "redirect:/doctor/dashboard";
                }
            }
        }

        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
