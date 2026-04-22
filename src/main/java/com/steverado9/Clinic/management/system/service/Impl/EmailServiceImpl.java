package com.steverado9.Clinic.management.system.service.Impl;

import com.steverado9.Clinic.management.system.entity.Appointment;
import com.steverado9.Clinic.management.system.enums.Status;
import com.steverado9.Clinic.management.system.service.EmailService;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;

@Service
public class EmailServiceImpl implements EmailService {

    private JavaMailSender mailSender;

    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendRegistrationEmail(String toEmail, String token) {

        try {

            String link = "http://localhost:9090/patient/register?token=" + token;

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(toEmail);
            helper.setSubject("Complete your registration");

            String htmlContent = "<p>Click the link below to register:</p>" + "<a href=\"" + link + "\">Complete Registration </a>";

            helper.setText(htmlContent, true);
            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendAppointmentStatusEmail(String toEmail, Status status, Appointment appointment) {
        try {

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy hh:mm a");
            String formattedDate = appointment.getAppointmentDate().format(formatter);

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(toEmail);
            helper.setSubject("Appointment Update");

            String htmlContent;

            if (status.equals(Status.APPROVED)) {
                htmlContent = "<p>Your appointment on <b> " + formattedDate + "</b> has been <b style='color:green;'>" + status + "</b>.</p>";
            } else {
                htmlContent = "<p>Your appointment on <b> " + formattedDate + "</b> has been <b style='color:red;'>" + status + "</b>.</p>";
            }

            helper.setText(htmlContent, true);

            mailSender.send(message);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
