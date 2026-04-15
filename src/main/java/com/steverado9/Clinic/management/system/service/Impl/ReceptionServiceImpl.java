package com.steverado9.Clinic.management.system.service.Impl;

import com.steverado9.Clinic.management.system.entity.Appointment;
import com.steverado9.Clinic.management.system.entity.ReceptionProfile;
import com.steverado9.Clinic.management.system.repository.AppointmentRepository;
import com.steverado9.Clinic.management.system.repository.ReceptionRepository;
import com.steverado9.Clinic.management.system.service.ReceptionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReceptionServiceImpl implements ReceptionService {

    private ReceptionRepository receptionRepository;

    private AppointmentRepository appointmentRepository;

    public ReceptionServiceImpl(ReceptionRepository receptionRepository, AppointmentRepository appointmentRepository) {
        this.receptionRepository = receptionRepository;
        this.appointmentRepository = appointmentRepository;
    }


    @Override
    public ReceptionProfile saveReception(ReceptionProfile reception) {
        return receptionRepository.save(reception);
    }

    @Override
    public List<ReceptionProfile> getAllReceptions() {
        return receptionRepository.findAll();
    }

    @Override
    public void deleteByReceptionId(Long id) {
        receptionRepository.deleteById(id);
    }

    @Override
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }
}
