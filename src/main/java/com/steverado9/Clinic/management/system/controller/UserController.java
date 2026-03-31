package com.steverado9.Clinic.management.system.controller;

import com.steverado9.Clinic.management.system.entity.User;
import com.steverado9.Clinic.management.system.service.UserService;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.steverado9.Clinic.management.system.dto.UserDto;
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

    @GetMapping("/admin/adminDashboard")
    public String adminpage() {
        return "adminDashboard";
    }

    @GetMapping("/register.html")
    public String showRegisterationForm(Model model) {

        UserDto user = new UserDto();

        model.addAttribute("user", user);

        return "register.html";
    }

//    @PostMapping("/login")
//    public String getUser(@ModelAttribute("user") User user, HttpSession session) {
//        System.out.println("1 " + user);
//        Optional<User> existingUser = userService.getUserByEmail(user.getEmail());
//        System.out.println("2 " + existingUser);
//
//        if(existingUser.isEmpty()) {
//            System.out.println("user does not exist");
//            return "redirect:/login";
//        }
//
//        String existingPassword = existingUser.get().getPassword();
//        if (!passwordEncoder.matches(user.getPassword(), existingPassword)) {
//            System.out.println("Incorrect password");
//            return "redirect:/login";
//        }
//
//        session.setAttribute("loggedInUser", existingUser);
//
//        return "adminDashboard";
//    }

}
