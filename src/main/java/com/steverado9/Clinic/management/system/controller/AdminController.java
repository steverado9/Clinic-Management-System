package com.steverado9.Clinic.management.system.controller;

import com.steverado9.Clinic.management.system.dto.CreateUserDto;
import com.steverado9.Clinic.management.system.entity.DoctorProfile;
import com.steverado9.Clinic.management.system.entity.ReceptionProfile;
import com.steverado9.Clinic.management.system.entity.User;
import com.steverado9.Clinic.management.system.enums.Role;
import com.steverado9.Clinic.management.system.service.DoctorService;
import com.steverado9.Clinic.management.system.service.ReceptionService;
import com.steverado9.Clinic.management.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private ReceptionService receptionService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/dashboard")
    public String dashboard() {
        return "admin/dashboard";
    }

    @GetMapping("/create_user")
    public String showCreateUserForm(Model model) {
        model.addAttribute("userDto", new CreateUserDto());
        return "admin/create_user";
    }

    @PostMapping("/create_user")
    public String createUser(@ModelAttribute("userDto") CreateUserDto dto) {

        Optional<User> existingUser = userService.findByEmail(dto.getEmail());

        if (existingUser.isPresent()) {
            //catch error in front end
            return "redirect:/admin/create_user?error";
        }

        User user = new User();
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(dto.getRole());
        user.setEnabled(true);

        userService.saveUser(user);

        //DoctorProfile
        if (dto.getRole() == Role.DOCTOR) {

            DoctorProfile doctor = new DoctorProfile();
            doctor.setUser(user);
            doctor.setFullName(dto.getFullName());
            doctor.setSpecialization(dto.getSpecialization());

            doctorService.saveDoctor(doctor);
        }

        //ReceptionProfile
        if (dto.getRole() == Role.RECEPTIONIST) {

            ReceptionProfile reception = new ReceptionProfile();
            reception.setUser(user);
            reception.setFullName(dto.getFullName());

            receptionService.saveReception(reception);
        }

        return "redirect:/admin/create_user?success";

    }

}
