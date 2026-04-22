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
import java.util.Optional;

@Controller
@RequestMapping("/patient")
public class PatientController {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private MedicalReportService medicalReportService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private RegistrationTokenService registrationTokenService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @GetMapping("/dashboard")
    public String dashboard() {
        return "/dashboard";
    }

    @GetMapping("/register")
    public String ShowRegistrationForm(@RequestParam("token") String token, Model model) {

        try {
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
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "patient/register";
        }
    }

    @PostMapping("/register")
    public String completeRegistration(@RequestParam String token, @RequestParam String fullName, @RequestParam String password, @RequestParam String phoneNumber, Model model) {

        try {
            RegistrationToken regToken = registrationTokenService.findByToken(token);

            if (regToken == null) {
                throw new RuntimeException("Invalid token");
            }

            Optional<PatientProfile> existingPatient = patientService.findByEmail(regToken.getEmail());
            if (existingPatient.isEmpty()) {

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
            } else {
                throw new RuntimeException("Token has already been used");
            }

        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
        }

       return "patient/register";
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
    public String bookAppointment( @ModelAttribute Appointment appointment,
                                   @AuthenticationPrincipal UserDetails userDetails, Model model, @RequestParam("time") String time, @RequestParam("date") String date) {
        try {
            appointmentService.createAppointment(appointment.getDoctorId(), appointment, userDetails, time, date);
            return "redirect:/patient/appointments?success";
        } catch (RuntimeException e) {
            model.addAttribute("appointment", new Appointment());
            model.addAttribute("doctors", doctorService.getAllDoctors());
            model.addAttribute("error", e.getMessage());
            return "patient/book";
        }
    }

    @GetMapping("/appointments")
    public String viewAppointments(Model model, Principal principal) {

        String email = principal.getName(); //principal is from spring security, and it is used to get the authenticated username.

        List<Appointment> appointments = appointmentService.getAppointmentsByPatientEmail(email);

        model.addAttribute("appointments", appointments);

        return "patient/appointments";
    }

    @GetMapping("/reports")
    public String viewReports(Model model, Principal principal) {

       try {
           String email = principal.getName();

           PatientProfile patient = patientService.findByEmail(email).orElseThrow(() -> new RuntimeException("Patient not found"));

           List<MedicalReport> reports = medicalReportService.findByPatient(patient);

           model.addAttribute("reports", reports);

           return "patient/reports";
       } catch (RuntimeException e) {
           model.addAttribute("error", e.getMessage());
       }
        return "patient/reports";
    }
}


