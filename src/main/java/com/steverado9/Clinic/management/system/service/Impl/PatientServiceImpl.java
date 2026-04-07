package com.steverado9.Clinic.management.system.service.Impl;

import com.steverado9.Clinic.management.system.entity.PatientProfile;
import com.steverado9.Clinic.management.system.repository.PatientRepository;
import com.steverado9.Clinic.management.system.service.PatientService;
import org.springframework.stereotype.Service;

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
}
