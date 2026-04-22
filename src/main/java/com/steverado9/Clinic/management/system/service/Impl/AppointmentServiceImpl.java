package com.steverado9.Clinic.management.system.service.Impl;

import com.steverado9.Clinic.management.system.entity.Appointment;
import com.steverado9.Clinic.management.system.entity.DoctorProfile;
import com.steverado9.Clinic.management.system.entity.PatientProfile;
import com.steverado9.Clinic.management.system.enums.Status;
import com.steverado9.Clinic.management.system.repository.AppointmentRepository;
import com.steverado9.Clinic.management.system.repository.DoctorRepository;
import com.steverado9.Clinic.management.system.repository.PatientRepository;
import com.steverado9.Clinic.management.system.service.AppointmentService;
import com.steverado9.Clinic.management.system.service.DoctorService;
import com.steverado9.Clinic.management.system.service.EmailService;
import com.steverado9.Clinic.management.system.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private PatientRepository patientRepository;

    @Override
    public void createAppointment(Long doctorId, Appointment appointment, UserDetails userDetails, String time, String date) {

        LocalDate localDate = LocalDate.parse(date);
        LocalTime localTime = LocalTime.parse(time);
        LocalDateTime dateTime = LocalDateTime.of(localDate, localTime);
        LocalDateTime now = LocalDateTime.now();

        if (dateTime.isBefore(now)) {
            throw new RuntimeException("You cannot book a past time");
        }

        PatientProfile patient = patientService.findByEmail(userDetails.getUsername()).orElseThrow(() -> new RuntimeException("Cannot create appointment for empty patient"));
        DoctorProfile doctor = doctorService.findById(doctorId);

        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setAppointmentDate(dateTime);
        appointment.setStatus(Status.PENDING);

        if (appointmentRepository.existsByDoctorAndAppointmentDate(doctor, appointment.getAppointmentDate())) {
            throw new RuntimeException("This time slot is already booked");
        }

        appointmentRepository.save(appointment);
    }

    @Override
    public List<Appointment> getDoctorAppointments(Long doctorId) {
        return appointmentRepository.findByDoctor_Id(doctorId);
    }

    @Override
    public List<Appointment> getPatientAppointments(Long patientId) {
        return appointmentRepository.findByPatientId(patientId);
    }

    @Override
    public void updateStatus(Long id, Status status) {
        Appointment appointment = appointmentRepository.findById(id).orElseThrow(() -> new RuntimeException("No Appointment Found"));
        appointment.setStatus(status);
        appointmentRepository.save(appointment);

        //send email to patient
        System.out.println("email " + appointment.getPatient().getEmail());
        String patientEmail = appointment.getPatient().getEmail();

        //make async
        emailService.sendAppointmentStatusEmail(patientEmail, status, appointment);
    }

    @Override
    public List<Appointment> getAppointmentsByPatientEmail(String email) {

        PatientProfile patient = patientRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Patient not found"));

        return appointmentRepository.findByPatientId(patient.getId());
    }

    @Override
    public Appointment findById(Long appointmentId) {
        return appointmentRepository.findById(appointmentId).orElseThrow(() -> new RuntimeException("Appointment not found"));
    }

    @Override
    public void deleteById(Long id) {
        appointmentRepository.deleteById(id);
    }
}
