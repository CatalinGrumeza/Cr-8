package com.Cr_8.servicies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {
    @Autowired
    private JavaMailSender javaMailSender;

    private final String fromEmailId = "educacciademo@gmail.com";

    public void sendEmail(String recipient, String body, String subject,String name,String surname,String type) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(fromEmailId);
        simpleMailMessage.setTo(recipient);
        simpleMailMessage.setText(
            String.format(
                "Gentile %s %s ,la sua richiesta di %s è stata inviata con successo:\n\n%s\n\nCordiali saluti,\nLo staff di Educaccia",
                surname,name,type,body
            )
        );
        simpleMailMessage.setSubject(subject);
        javaMailSender.send(simpleMailMessage);
    }

    public void sendEmailToAdmin(String user, String bodyMessage, String subject, String name, String surname) {
        String baseText = String.format(
            "Richiesta di informazioni da parte di:\n" +
            "Nome: %s %s\n" +
            "Email: %s\n\n" +
            "Messaggio ricevuto:\n" +
            "%s\n\n",
            name, surname, user, bodyMessage
        );

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(fromEmailId);
        simpleMailMessage.setTo("educacciademo@gmail.com");
        simpleMailMessage.setText(baseText);
        simpleMailMessage.setSubject(subject);
        javaMailSender.send(simpleMailMessage);
    }
}
