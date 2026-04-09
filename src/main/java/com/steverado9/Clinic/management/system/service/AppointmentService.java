package com.steverado9.Clinic.management.system.service;

import com.steverado9.Clinic.management.system.entity.Appointment;
import com.steverado9.Clinic.management.system.enums.Status;

import java.util.List;

public interface AppointmentService {

    void createAppointment(Appointment appointment);

    List<Appointment> getDoctorAppointments(Long doctorId);

    List<Appointment> getPatientAppointments(Long patientId);

    void updateStatus(Long id, Status status);

    List<Appointment> getAppointmentsByPatientEmail(String email);
}
