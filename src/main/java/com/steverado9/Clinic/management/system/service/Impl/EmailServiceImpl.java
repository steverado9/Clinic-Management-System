package com.steverado9.Clinic.management.system.service.Impl;

import com.steverado9.Clinic.management.system.service.EmailService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class EmailServiceImpl implements EmailService {

    private JavaMailSender mailSender;

    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendRegistrationEmail(String toEmail, String token) {

        String link = "http://localhost:9090/patient/register?token=" + token;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("isaacsteverado9@gmail.com");
        message.setSubject("Complete your registration");
        message.setText("CLick the link to register:/n" + link);

        mailSender.send(message);
    }
}
