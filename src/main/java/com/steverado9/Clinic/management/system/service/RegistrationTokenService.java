package com.steverado9.Clinic.management.system.service;

import com.steverado9.Clinic.management.system.entity.RegistrationToken;

public interface RegistrationTokenService {

    RegistrationToken saveToken(RegistrationToken token);

    RegistrationToken findByToken(String token);
}
