package com.steverado9.Clinic.management.system.service;

import com.steverado9.Clinic.management.system.entity.Appointment;
import com.steverado9.Clinic.management.system.enums.Status;

public interface EmailService {
    void sendRegistrationEmail(String toEmail, String token);

    void sendAppointmentStatusEmail(String toEmail, Status status, Appointment appointment);
}
