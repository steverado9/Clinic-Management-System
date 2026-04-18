package com.steverado9.Clinic.management.system.controller;

import com.steverado9.Clinic.management.system.dto.CreateUserDto;
import com.steverado9.Clinic.management.system.entity.*;
import com.steverado9.Clinic.management.system.enums.Role;
import com.steverado9.Clinic.management.system.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private MedicalReportService medicalReportService;

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private PatientService patientService;

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
        return "/dashboard";
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

    //Doctors
    @GetMapping("/doctors")
    public String getAllDoctors(Model model) {
        List<DoctorProfile> doctors = doctorService.getAllDoctors();

        model.addAttribute("doctors", doctors);
        return "admin/doctors";
    }

    @DeleteMapping("doctors/delete/{id}")
    public String deleteDoctor(@PathVariable Long id) {
        doctorService.deleteByDoctorId(id);
        return "redirect:/admin/doctors";
    }

    @GetMapping("/doctors/{id}/appointments")
    public String getDoctorAppointments(@PathVariable("id") Long doctorId, Model model) {
        DoctorProfile doctor = doctorService.findById(doctorId);
        List<Appointment> appointments = appointmentService.getDoctorAppointments(doctorId);

        model.addAttribute("doctor", doctor);
        model.addAttribute("appointments", appointments);
        return "admin/doctor_appointments";
    }

    //Receptionists
    @GetMapping("/receptionist")
    public String getAllReception(Model model) {
        List<ReceptionProfile> receptions = receptionService.getAllReceptions();

        model.addAttribute("receptions", receptions);
        return "admin/receptions";
    }

    @DeleteMapping("receptionist/delete/{id}")
    public String deleteReceptionist(@PathVariable Long id) {
        receptionService.deleteByReceptionId(id);
        return "redirect:/admin/receptionist";
    }

    //Patients
    @GetMapping("/patients")
    public String getPatients(Model model) {
        List<PatientProfile> patients = patientService.getAllPatients();

        model.addAttribute("patients", patients);
        return "admin/patients";
    }

    @GetMapping("/patients/{id}/reports")
    public String getPatientReports(@PathVariable("id") Long patientId, Model model) {
        PatientProfile patient = patientService.findById(patientId);
        List<MedicalReport> reports = medicalReportService.getMedicalReportByPatientId(patientId);

        model.addAttribute("patient", patient);
        model.addAttribute("reports", reports);
        return "admin/reports";
    }

    @DeleteMapping("patients/delete/{id}")
    public String deletePatient(@PathVariable Long id) {
        patientService.deleteByPatientId(id);
        return "redirect:/admin/dashboard";
    }
}
