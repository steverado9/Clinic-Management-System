package com.steverado9.Clinic.management.system.controller;

import com.steverado9.Clinic.management.system.entity.User;
import com.steverado9.Clinic.management.system.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class UserController {


//    private PasswordEncoder passwordEncoder;
    private UserService userService;

    public UserController( UserService userService) {
//        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginPage(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "login";
    }

    @PostMapping("/login")
    public String getUser(@ModelAttribute("user") User user, HttpSession session) {
        System.out.println("1 " + user);
        Optional<User> existingUser = userService.getUserByEmail(user.getEmail());
        System.out.println("2 " + existingUser);

        if(existingUser.isEmpty()) {
            System.out.println("user does not exist");
            return "redirect:/login";
        }

//        String existingPassword = existingUser.get().getPassword();
//        if (!passwordEncoder.matches(user.getPassword(), existingPassword)) {
//            System.out.println("Incorrect password");
//            return "redirect:/login";
//        }

        session.setAttribute("loggedInUser", existingUser);

        return "adminDashboard";
    }

}
