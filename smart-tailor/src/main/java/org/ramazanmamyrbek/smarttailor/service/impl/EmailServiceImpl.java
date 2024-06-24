package org.ramazanmamyrbek.smarttailor.service.impl;

import lombok.RequiredArgsConstructor;
import org.ramazanmamyrbek.smarttailor.service.EmailService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    final JavaMailSender mailSender;

    @Override
    public void sendCode(String toEmail, String code) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("Email verification");
        message.setTo(toEmail);
        message.setText("Verification code: " + code);
        mailSender.send(message);
    }
}
