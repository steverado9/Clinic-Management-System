package com.steverado9.Clinic.management.system.repository;

import com.steverado9.Clinic.management.system.entity.PatientProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PatientRepository extends JpaRepository<PatientProfile, Long> {
    Optional<PatientProfile> findByEmail(String email);
}
