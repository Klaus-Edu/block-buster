package com.example.blockbuster.blockbuster.services;

import com.example.blockbuster.blockbuster.enums.StatusEmail;
import com.example.blockbuster.blockbuster.models.MailModel;
import com.example.blockbuster.blockbuster.repositories.MailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.time.LocalDateTime;
import java.util.List;

public class MailService {

    MailRepository mailRepository;

    @Autowired
    private JavaMailSender emailSender;

    public MailService(MailRepository mailRepository){
        this.mailRepository = mailRepository;
    }

    public MailModel sendEmail(MailModel mailModel) {
        mailModel.setSendDateEmail(LocalDateTime.now());

        try{
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(mailModel.getEmailFrom());
            message.setTo(mailModel.getEmailTo());
            message.setSubject(mailModel.getSubject());
            message.setText(mailModel.getText());

            emailSender.send(message);

            mailModel.setStatusEmail(StatusEmail.SENT);
        } catch (MailException e){
            mailModel.setStatusEmail(StatusEmail.ERROR);
        } finally {
            return mailRepository.save(mailModel);
        }
    }

    public List<MailModel> findAll() {
        return mailRepository.findAll();
    }
}
