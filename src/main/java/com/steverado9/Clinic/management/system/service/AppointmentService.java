package com.steverado9.Clinic.management.system.service;

import com.steverado9.Clinic.management.system.entity.Appointment;
import com.steverado9.Clinic.management.system.enums.Status;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AppointmentService {

    void createAppointment(Long doctorId, Appointment appointment, UserDetails userDetails, String time, String date);

    Optional<List<Appointment>> getDoctorAppointments(Long doctorId);

    Optional<List<Appointment>> getPatientAppointments(Long patientId);

    void updateStatus(Long id, Status status);

    Optional<List<Appointment>> getAppointmentsByPatientEmail(String email);

    Optional<Appointment> findById(Long appointmentId);

    void deleteById(Long id);
}
