package com.steverado9.Clinic.management.system.service;

public interface EmailService {
    void sendRegistrationEmail(String toEmail, String token);
}
