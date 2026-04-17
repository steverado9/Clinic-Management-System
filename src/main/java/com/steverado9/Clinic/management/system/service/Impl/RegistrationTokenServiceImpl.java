package com.steverado9.Clinic.management.system.service.Impl;

import com.steverado9.Clinic.management.system.entity.RegistrationToken;
import com.steverado9.Clinic.management.system.repository.RegistrationTokenRepository;
import com.steverado9.Clinic.management.system.service.RegistrationTokenService;
import org.springframework.stereotype.Service;

@Service
public class RegistrationTokenServiceImpl implements RegistrationTokenService {

    private RegistrationTokenRepository registrationTokenRepository;

    public RegistrationTokenServiceImpl(RegistrationTokenRepository registrationTokenRepository) {
        this.registrationTokenRepository = registrationTokenRepository;
    }

    @Override
    public RegistrationToken saveToken(RegistrationToken token) {
        return registrationTokenRepository.save(token);
    }

    @Override
    public RegistrationToken findByToken(String token) {
        return registrationTokenRepository.findByToken(token).orElseThrow(() -> new RuntimeException("Token not found"));
    }
}
