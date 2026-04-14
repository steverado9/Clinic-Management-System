package com.steverado9.Clinic.management.system.service;

import com.steverado9.Clinic.management.system.entity.PatientProfile;

import java.util.List;
import java.util.Optional;

public interface PatientService {
    PatientProfile savePatient(PatientProfile patient);

    PatientProfile findByEmail(String email);

    PatientProfile findById(Long patientId);

    void deleteByPatientId(Long id);

    List<PatientProfile> getAllPatients();
}
