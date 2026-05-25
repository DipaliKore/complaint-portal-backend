package com.complaint.complaint_portal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public void sendStatusUpdateEmail(String toEmail, String userName, String complaintTitle, String newStatus) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(toEmail);
            message.setSubject("Complaint Status Updated - Complaint Portal");
            message.setText(
                "Dear " + userName + ",\n\n" +
                "Your complaint '" + complaintTitle + "' status has been updated to: " + newStatus + "\n\n" +
                "Thank you for using Complaint Portal.\n\n" +
                "Regards,\nComplaint Portal Team"
            );
            mailSender.send(message);
            System.out.println("Email sent successfully to: " + toEmail);
        } catch (Exception e) {
            System.out.println("Email send failed: " + e.getMessage());
        }
    }

    public void sendPasswordResetEmail(String toEmail, String userName, String resetLink) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(toEmail);
            message.setSubject("Password Reset - Complaint Portal");
            message.setText(
                "Dear " + userName + ",\n\n" +
                "You requested to reset your password.\n\n" +
                "Click the link below to reset your password:\n" +
                resetLink + "\n\n" +
                "This link will expire in 30 minutes.\n\n" +
                "If you did not request this, please ignore this email.\n\n" +
                "Regards,\nComplaint Portal Team"
            );
            mailSender.send(message);
            System.out.println("Reset email sent to: " + toEmail);
        } catch (Exception e) {
            System.out.println("Reset email failed: " + e.getMessage());
        }
    }
}