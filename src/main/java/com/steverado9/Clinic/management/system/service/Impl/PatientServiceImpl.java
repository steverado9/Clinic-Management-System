package com.steverado9.Clinic.management.system.service.Impl;

import com.steverado9.Clinic.management.system.entity.PatientProfile;
import com.steverado9.Clinic.management.system.repository.PatientRepository;
import com.steverado9.Clinic.management.system.service.PatientService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientServiceImpl implements PatientService {

    private PatientRepository patientRepository;

    public PatientServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public PatientProfile savePatient(PatientProfile patient) {
        return patientRepository.save(patient);
    }

    @Override
    public PatientProfile findByEmail(String email) {
        return patientRepository.findByEmail(email);
    }

    @Override
    public PatientProfile findById(Long patientId) {
        return patientRepository.findById(patientId).orElseThrow(() -> new RuntimeException("Patient not found"));
    }

    @Override
    public void deleteByPatientId(Long id) {
        patientRepository.deleteById(id);
    }

    @Override
    public List<PatientProfile> getAllPatients() {
        return patientRepository.findAll();
    }
}
