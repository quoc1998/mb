package com.backend.bank.configurations;
import java.util.List;
import java.util.Properties;

import com.backend.bank.model.MailSettings;
import com.backend.bank.model.Settings;
import com.backend.bank.repository.SettingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfig {

    @Autowired
    SettingsRepository settingsRepository;

    @Bean
    public JavaMailSender getJavaMailSender() {
        List<Settings> settings = settingsRepository.findAll();
        MailSettings mailSetting = settings.get(0).getMailSettings();
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername(mailSetting.getMailUsername());
        mailSender.setPassword(mailSetting.getMailPassword());

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.connectiontimeout", 5000);
        props.put("mail.smtp.timeout", 5000);
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.writetimeout", 5000);
        return mailSender;
    }

}