package com.steverado9.Clinic.management.system.controller;

import com.steverado9.Clinic.management.system.entity.DoctorProfile;
import com.steverado9.Clinic.management.system.entity.User;
import com.steverado9.Clinic.management.system.enums.Status;
import com.steverado9.Clinic.management.system.service.AppointmentService;
import com.steverado9.Clinic.management.system.service.DoctorService;
import com.steverado9.Clinic.management.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/doctor")
public class DoctorController {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private UserService userService;

    @GetMapping("/dashboard")
    public String dashboard() {
        return "doctor/dashboard";
    }

    @GetMapping("/appointments")
    public String viewAppointments(Model  model, @AuthenticationPrincipal UserDetails userDetails) {

        System.out.println("email: " + userDetails.getUsername());
        User user = userService.findByEmail(userDetails.getUsername()).orElseThrow(() -> new UsernameNotFoundException("email not found"));

        DoctorProfile doctor = doctorService.findByUserId(user.getId());

        model.addAttribute("appointments", appointmentService.getDoctorAppointments(doctor.getId()));

        return "doctor/appointments";
    }

    @PostMapping("/appointments/{id}/approve")
    public String approve(@PathVariable Long id) {
        appointmentService.updateStatus(id, Status.APPROVED);
        return "redirect:/doctor/appointments";
    }

    @PostMapping("/appointments/{id}/reject")
    public String reject(@PathVariable Long id) {
        appointmentService.updateStatus(id, Status.REJECTED);
        return "redirect:/doctor/appointments";
    }
}
