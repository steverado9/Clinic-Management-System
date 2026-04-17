package com.steverado9.Clinic.management.system.repository;

import com.steverado9.Clinic.management.system.entity.DoctorProfile;
import com.steverado9.Clinic.management.system.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DoctorRepository extends JpaRepository<DoctorProfile, Long> {
    Optional<DoctorProfile> findByUserId(Long userId);

    Optional<DoctorProfile> findByUserEmail(String email);

}
