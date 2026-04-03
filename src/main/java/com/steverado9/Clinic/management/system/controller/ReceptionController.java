package com.steverado9.Clinic.management.system.controller;

import com.steverado9.Clinic.management.system.entity.RegistrationToken;
import com.steverado9.Clinic.management.system.repository.PatientRepository;
import com.steverado9.Clinic.management.system.service.RegistrationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.UUID;

@Controller
@RequestMapping("/reception")
public class ReceptionController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private RegistrationTokenService registrationTokenService;

    @GetMapping("/dashboard")
    public String dashboard() {
        return "reception/dashboard";
    }

    @GetMapping("/register_patient")
    public String showPatientForm() {
        return "reception/register_patient";
    }

    @PostMapping("/register_patient")
    public String registerPatient(@RequestParam String email) {

        //used to generate raandom token
        String token = UUID.randomUUID().toString();

        RegistrationToken regToken = new RegistrationToken();
        regToken.setToken(token);
        regToken.setEmail(email);
        regToken.setExpiryDate(LocalDateTime.now().plusHours(24));

        registrationTokenService.saveToken(regToken);

        //See the token(send it to email)
        System.out.println("Token: " + token);

        return "redirect:/reception/register_patient?success";
    }

}
