package com.Cr_8.servicies;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.Cr_8.entities.Admin;
import com.Cr_8.entities.BookingRequest;
import com.Cr_8.repositories.AdminRepo;



@Service
public class MailService {
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private AdminRepo adminRepo;

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
    public void sendEmailConfirmBooked(BookingRequest bookingRequest,LocalDate data) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(fromEmailId);
        simpleMailMessage.setTo(bookingRequest.getReference().getEmail());
        simpleMailMessage.setText(
            String.format(
                "Gentile %s %s ,la sua richiesta di prenotazione è stata confermata con successo in data \n\n%s\n\nCordiali saluti,\nLo staff di Educaccia",
                bookingRequest.getReference().getLastName(),bookingRequest.getReference().getFirstName(),data
            )
        );
        simpleMailMessage.setSubject("Prenotazione Confermata");
        javaMailSender.send(simpleMailMessage);
    }
    public void sendEmailCancelledBooked(BookingRequest bookingRequest) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(fromEmailId);
        simpleMailMessage.setTo(bookingRequest.getReference().getEmail());
        simpleMailMessage.setText(
            String.format(
                "Gentile %s %s ,la sua richiesta di prenotazione è stata annullata \n\n\n\nCordiali saluti,\nLo staff di Educaccia",
                bookingRequest.getReference().getLastName(),bookingRequest.getReference().getFirstName()
            )
        );
        simpleMailMessage.setSubject("Prenotazione Confermata");
        javaMailSender.send(simpleMailMessage);
    }

    public void sendEmailToAdmin(String user, String bodyMessage, String subject, String name, String surname,String type,String phone) {
        String baseText = String.format(
            "Richiesta di %s da parte di:\n" +
            "Nome: %s %s\n" +
            "Email: %s\n\n" +
            "Phone: %s\n\n" +
            "Messaggio ricevuto:\n" +
            "%s\n\n",
            type,name, surname, user,phone, bodyMessage
        );

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(fromEmailId);
        simpleMailMessage.setText(baseText);
        simpleMailMessage.setSubject(subject);
        List<Admin> admins=adminRepo.findAll();
        for (Admin admin : admins) {
        	simpleMailMessage.setTo(admin.getEmail());
        	javaMailSender.send(simpleMailMessage);
		}
    }
    public void sendPasswordEmailRest(String email, String code ) {
    
    	String baseText = String.format(
    			"Code verification for password is "
    			+ "%s",code
    			);
    	SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
    	simpleMailMessage.setFrom(fromEmailId);
    	simpleMailMessage.setTo(email);
    	simpleMailMessage.setText(baseText);
    	simpleMailMessage.setSubject("Code Verification");
    	javaMailSender.send(simpleMailMessage);
    	
    	
    }
}
