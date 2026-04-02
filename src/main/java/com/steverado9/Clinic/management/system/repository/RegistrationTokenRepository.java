package com.steverado9.Clinic.management.system.repository;

import com.steverado9.Clinic.management.system.entity.RegistrationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistrationTokenRepository extends JpaRepository<RegistrationToken, Long> {
    RegistrationToken findByToken(String token);
}
