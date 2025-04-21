package com.oj_timer.server.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {

    @Value("${GOOGLE_EMAIL}")
    private String username;

    @Value("${GOOGLE_PASSWORD}")
    private String password;

    @Bean
    public JavaMailSender javaMailService() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

        javaMailSender.setHost("smtp.gmail.com"); // ✅ 'google.com' -> 'gmail.com' 수정
        javaMailSender.setPort(587);
        javaMailSender.setUsername(username);
        javaMailSender.setPassword(password);

        javaMailSender.setJavaMailProperties(getMailProperties());

        return javaMailSender;
    }

    private Properties getMailProperties() {
        Properties properties = new Properties();

        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.starttls.enable", "true");
        properties.setProperty("mail.smtp.starttls.required", "true"); // ✅ 추가
        properties.setProperty("mail.smtp.connectiontimeout", "20000"); // ✅ 추가
        properties.setProperty("mail.smtp.timeout", "20000"); // ✅ 추가
        properties.setProperty("mail.smtp.writetimeout", "20000"); // ✅ 추가
        properties.setProperty("mail.transport.protocol", "smtp");

        return properties;
    }
}