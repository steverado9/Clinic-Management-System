package com.steverado9.Clinic.management.system.controller;

import com.steverado9.Clinic.management.system.entity.Appointment;
import com.steverado9.Clinic.management.system.entity.DoctorProfile;
import com.steverado9.Clinic.management.system.entity.MedicalReport;
import com.steverado9.Clinic.management.system.entity.User;
import com.steverado9.Clinic.management.system.enums.Status;
import com.steverado9.Clinic.management.system.service.AppointmentService;
import com.steverado9.Clinic.management.system.service.DoctorService;
import com.steverado9.Clinic.management.system.service.MedicalReportService;
import com.steverado9.Clinic.management.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/doctor")
public class DoctorController {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    MedicalReportService medicalReportService;

    @Autowired
    private UserService userService;

    @GetMapping("/dashboard")
    public String dashboard() {
        return "doctor/dashboard";
    }

    @GetMapping("/appointments")
    public String viewAppointments(Model  model, @AuthenticationPrincipal UserDetails userDetails) {

        User user = userService.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("email not found"));

        DoctorProfile doctor = doctorService.findByUserId(user.getId());

        model.addAttribute("appointments", appointmentService.getDoctorAppointments(doctor.getId()));

        return "doctor/appointments";
    }

    @PostMapping("/appointments/{id}/approve")
    public String approve(@PathVariable Long id) {
        appointmentService.updateStatus(id, Status.APPROVED);
        return "redirect:/doctor/appointments?success";
    }

    @PostMapping("/appointments/{id}/reject")
    public String reject(@PathVariable Long id) {
        appointmentService.updateStatus(id, Status.REJECTED);
        return "redirect:/doctor/appointments?success";
    }

    @GetMapping("/report/create/{appointmentId}")
    public String ShowCreateReport(@PathVariable Long appointmentId, Model model) {
        Appointment appointment = appointmentService.findById(appointmentId);

        if (appointment.getStatus() != Status.APPROVED) {
            throw new RuntimeException("Cannot create report for unapproved appointment");
        }

        if (appointment.getMedicalReport() != null) {
            throw new RuntimeException("Report already exists");
        }

        model.addAttribute("report", new MedicalReport());
        model.addAttribute("appointment", appointment);

        return "doctor/create_report";
    }

    @PostMapping("/report/save/{appointmentId}")
    public String saveReport(@PathVariable Long appointmentId, @ModelAttribute MedicalReport report, Principal principal) {

        Appointment appointment = appointmentService.findById(appointmentId);

        //double-checking
        if (appointment.getStatus() != Status.APPROVED) {
            throw new RuntimeException("Invalid Operation");
        }

        if (appointment.getMedicalReport() != null) {
            throw new RuntimeException("Report already exists");
        }

        DoctorProfile doctor = doctorService.findByUserEmail(principal.getName());

        report.setAppointment(appointment);
        report.setDoctor(doctor);
        report.setPatient(appointment.getPatient());
        report.setCreatedAt(LocalDateTime.now());

        medicalReportService.saveReport(report);

        return "redirect:/doctor/appointments?success";
    }
}
