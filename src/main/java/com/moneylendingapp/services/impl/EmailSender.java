package com.moneylendingapp.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;


@Service
@Slf4j
@RequiredArgsConstructor
public class EmailSender {

    private final JavaMailSender mailSender;

    @Async
    public void send(String to, String email) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper
                    (mimeMessage, "utf-8");
            helper.setText(email, true);
            helper.setTo(to);
            helper.setSubject("Confirm your email");
            helper.setFrom("topekint@gmail.com");
            mailSender.send(mimeMessage);

        } catch (MessagingException me) {
            log.error("Failed to send email, ", me);
            throw new IllegalStateException("Failed to send email");
        }
    }
}
