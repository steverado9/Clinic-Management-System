package com.steverado9.Clinic.management.system.service.Impl;

import com.steverado9.Clinic.management.system.entity.DoctorProfile;
import com.steverado9.Clinic.management.system.entity.User;
import com.steverado9.Clinic.management.system.repository.DoctorRepository;
import com.steverado9.Clinic.management.system.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Override
    public DoctorProfile saveDoctor(DoctorProfile doctor) {
        return doctorRepository.save(doctor);
    }

    @Override
    public DoctorProfile findByUserId(Long userId) {
        return doctorRepository.findByUserId(userId);
    }

    @Override
    public List<DoctorProfile> getAllDoctors() {
        return doctorRepository.findAll();
    }

    @Override
    public DoctorProfile findById(Long id) {
        return doctorRepository.findById(id).orElseThrow(() -> new RuntimeException("doctor not found"));
    }

    @Override
    public DoctorProfile findByUserEmail(String email) {
        return doctorRepository.findByUserEmail(email);
    }
}
