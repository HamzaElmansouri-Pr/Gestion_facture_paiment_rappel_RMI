package com.example.sessions;

import jakarta.annotation.Resource;
import jakarta.ejb.Stateless;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import jakarta.mail.Session;

@Stateless
public class EmailService {

    @Resource(lookup = "mail/smtp") // Must match GlassFish config
    private Session mailSession;

    public void sendEmail(String to, String subject, String body) {
        try {
            System.out.println("Preparing to send email to " + to);

            MimeMessage message = new MimeMessage(mailSession);
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);
            System.out.println("Email sent successfully to " + to);
        } catch (MessagingException ex) {
            System.out.println("Failed to send email to " + to);
            ex.printStackTrace();
        }
    }
}