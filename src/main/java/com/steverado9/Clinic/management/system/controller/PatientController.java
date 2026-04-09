package com.steverado9.Clinic.management.system.controller;

import com.steverado9.Clinic.management.system.entity.*;
import com.steverado9.Clinic.management.system.enums.Role;
import com.steverado9.Clinic.management.system.enums.Status;
import com.steverado9.Clinic.management.system.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

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

        Appointment appointment = new Appointment();
        appointment.setDoctor(new DoctorProfile());

        List<DoctorProfile> doctors = doctorService.getAllDoctors();

        model.addAttribute("appointment", appointment);
        model.addAttribute("doctors", doctors);
        return "patient/book";
    }

    @PostMapping("/book")
    public String bookAppointment( @RequestParam("doc.id") Long doctorId, @ModelAttribute Appointment appointment, @AuthenticationPrincipal UserDetails userDetails) {

        System.out.println("doctorId " +  doctorId);
        System.out.println("doctor " + doctorService.findById(doctorId));

        PatientProfile patient = patientService.findByEmail(userDetails.getUsername());
        DoctorProfile doctor = doctorService.findById(doctorId);


        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setAppointmentDate(appointment.getAppointmentDate());
        appointment.setStatus(Status.PENDING);

        appointmentService.createAppointment(appointment);

        return "redirect:/patient/dashboard";
    }

    @GetMapping("/appointments")
    public String viewAppointments(Model model, Principal principal) {

        String email = principal.getName(); //principal is from spring security, and it is used to get the authenticated username.

        List<Appointment> appointments = appointmentService.getAppointmentsByPatientEmail(email);

        model.addAttribute("appointments", appointments);

        return "patient/appointments";

    }
}


