package com.steverado9.Clinic.management.system.service.Impl;

import com.steverado9.Clinic.management.system.entity.Appointment;
import com.steverado9.Clinic.management.system.enums.Status;
import com.steverado9.Clinic.management.system.repository.AppointmentRepository;
import com.steverado9.Clinic.management.system.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Override
    public void createAppointment(Appointment appointment) {
        appointmentRepository.save(appointment);
    }

    @Override
    public List<Appointment> getDoctorAppointments(Long doctorId) {
        return appointmentRepository.findByDoctorId(doctorId);
    }

    @Override
    public List<Appointment> getPatientAppointments(Long patientId) {
        return appointmentRepository.findByPatientId(patientId);
    }

    @Override
    public void updateStatus(Long id, Status status) {
        Appointment appointment = appointmentRepository.findById(id).orElseThrow();
        appointment.setStatus(status);
        appointmentRepository.save(appointment);
    }
}
