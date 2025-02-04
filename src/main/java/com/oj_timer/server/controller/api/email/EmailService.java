package com.oj_timer.server.controller.api.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailService {

    private final JavaMailSender javaMailSender;
    private static String senderEmail = "tcc8546@gmail.com";

    @Autowired
    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public MimeMessage createMail(String mail, int number) {
        MimeMessage message = javaMailSender.createMimeMessage();
        log.info("CREATE MAIL METHOD [{}][{}]", senderEmail, mail);

        try {
            log.info("CREATE MAIL METHOD START TRY CATCH");
            message.setFrom(senderEmail);
            message.setRecipients(MimeMessage.RecipientType.TO, mail);
            message.setSubject("이메일 인증");
            String body = "";
            body += "<h3>" + "요청하신 인증 번호입니다." + "</h3>";
            body += "<h1>" + number + "</h1>";
            body += "<h3>" + "감사합니다." + "</h3>";
            message.setText(body,"UTF-8", "html");
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return message;
    }

    public int sendMail(String mail) {
        int number = createNumber();
        MimeMessage message = createMail(mail, number);
        javaMailSender.send(message);

        return number;
    }

    private int createNumber() {
        return (int) (Math.random() * 90000) + 100000;
    }
}
