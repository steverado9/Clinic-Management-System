package com.steverado9.Clinic.management.system.controller;

import com.steverado9.Clinic.management.system.entity.Appointment;
import com.steverado9.Clinic.management.system.entity.RegistrationToken;
import com.steverado9.Clinic.management.system.enums.Status;
import com.steverado9.Clinic.management.system.repository.PatientRepository;
import com.steverado9.Clinic.management.system.service.AppointmentService;
import com.steverado9.Clinic.management.system.service.EmailService;
import com.steverado9.Clinic.management.system.service.ReceptionService;
import com.steverado9.Clinic.management.system.service.RegistrationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/reception")
public class ReceptionController {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private RegistrationTokenService registrationTokenService;

    @Autowired
    private ReceptionService receptionService;

    @GetMapping("/dashboard")
    public String dashboard() {
        return "/dashboard";
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
        emailService.sendRegistrationEmail(email, token);

        return "redirect:/reception/register_patient?success";
    }

    @GetMapping("/appointments")
    public String allAppointments(Model model) {
        List<Appointment> appointments = receptionService.getAllAppointments();

        model.addAttribute("appointments", appointments);

        return "reception/appointments";
     }

    @PostMapping("/appointments/{id}/approve")
    public String approve(@PathVariable Long id) {
        appointmentService.updateStatus(id, Status.APPROVED);
        return "redirect:/reception/appointments?success";
    }

    @PostMapping("/appointments/{id}/reject")
    public String reject(@PathVariable Long id) {
        appointmentService.updateStatus(id, Status.REJECTED);
        return "redirect:/reception/appointments?success";
    }

    @DeleteMapping("appointments/delete/{id}")
    public String deleteAppointment(@PathVariable Long id) {
        appointmentService.deleteById(id);
        return "redirect:/reception/appointments";
    }

}
