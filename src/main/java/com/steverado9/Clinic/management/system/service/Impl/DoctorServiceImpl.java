package com.steverado9.Clinic.management.system.service.Impl;

import com.steverado9.Clinic.management.system.entity.DoctorProfile;
import com.steverado9.Clinic.management.system.repository.AppointmentRepository;
import com.steverado9.Clinic.management.system.repository.DoctorRepository;
import com.steverado9.Clinic.management.system.repository.MedicalReportRespository;
import com.steverado9.Clinic.management.system.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private MedicalReportRespository medicalReportRespository;

    @Override
    public DoctorProfile saveDoctor(DoctorProfile doctor) {
        return doctorRepository.save(doctor);
    }

    @Override
    public DoctorProfile findByUserId(Long userId) {
        return doctorRepository.findByUserId(userId).orElseThrow(() -> new RuntimeException("Doctor not found"));
    }

    @Override
    public List<DoctorProfile> getAllDoctors() {
        return doctorRepository.findAll();
    }

    @Override
    public DoctorProfile findById(Long id) {
        return doctorRepository.findById(id).orElseThrow(() -> new RuntimeException("Doctor not found"));
    }

    @Override
    public DoctorProfile findByUserEmail(String email) {
        return doctorRepository.findByUserEmail(email).orElseThrow(() -> new RuntimeException("Doctor not found"));
    }

    @Transactional
    @Override
    public void deleteByDoctorId(Long id) {
        doctorRepository.deleteById(id);
    }

}
