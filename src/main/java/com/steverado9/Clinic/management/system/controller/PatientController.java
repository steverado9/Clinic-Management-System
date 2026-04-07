package com.steverado9.Clinic.management.system.controller;

import com.steverado9.Clinic.management.system.entity.*;
import com.steverado9.Clinic.management.system.enums.Role;
import com.steverado9.Clinic.management.system.repository.PatientRepository;
import com.steverado9.Clinic.management.system.repository.RegistrationTokenRepository;
import com.steverado9.Clinic.management.system.repository.UserRepository;
import com.steverado9.Clinic.management.system.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@Controller
@RequestMapping("/patient")
public class PatientController {

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private UserService userService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private RegistrationTokenService registrationTokenService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/dashboard")
    public String dashboard() {
        return "patient/dashboard";
    }

    @GetMapping("/register")
    public String ShowRegisterationForm(@RequestParam("token") String token, Model model) {

        RegistrationToken regToken = registrationTokenService.findByToken(token);

        if(regToken == null) {
            throw new RuntimeException("Invalid token");
        }

        if (regToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Token expired");
        }

        model.addAttribute("email", regToken.getEmail());
        model.addAttribute("token", token);

        return "patient/register";
    }

    @PostMapping("/register")
    public String completeRegistration(@RequestParam String token, @RequestParam String fullName, @RequestParam String password, @RequestParam String phoneNumber) {

        RegistrationToken regToken = registrationTokenService.findByToken(token);

        if(regToken == null) {
                throw new RuntimeException("Invalid token");
        }

        PatientProfile patient = new PatientProfile();
        patient.setEmail(regToken.getEmail());
        patient.setFullName(fullName);
        patient.setPassword(passwordEncoder.encode(password));
        patient.setPhoneNumber(phoneNumber);
        patient.setEnable(true);

        patientService.savePatient(patient);

        // 🔥 ALSO CREATE USER FOR LOGIN
        User user = new User();
        user.setEmail(regToken.getEmail());
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(Role.PATIENT);
        user.setEnabled(true);

        userService.saveUser(user);

        return "redirect:/login";
    }

    @GetMapping("/book")
    public String showBookingForm(Model model) {
        model.addAttribute("appointment", new Appointment());
        model.addAttribute("doctors", doctorService.getAllDoctors());
        return "patient/book";
    }

    @PostMapping("/book")
    public String bookAppointment(@ModelAttribute("appointment") Appointment appointment, @AuthenticationPrincipal UserDetails userDetails) {


        PatientProfile patient = patientService.findByEmail(userDetails.getUsername());
        DoctorProfile doctor = doctorService.findById(appointment.getDoctor().getId());

        System.out.println("doctor " + doctor);

        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setAppointmentDate(appointment.getAppointmentDate());

        appointmentService.createAppointment(appointment);

        return "redirect:/patient/dashboard";

    }
}


