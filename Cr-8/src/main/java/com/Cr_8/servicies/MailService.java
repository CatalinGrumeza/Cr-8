package com.Cr_8.servicies;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.Cr_8.dto.BookingFormRequest;
import com.Cr_8.entities.Admin;
import com.Cr_8.entities.BookingRequest;
import com.Cr_8.repositories.AdminRepo;


@Service
public class MailService {
    @Autowired
    private JavaMailSender javaMailSender; // Handles sending emails
    @Autowired
    private AdminRepo adminRepo; // Repository for retrieving admin email addresses

    private final String fromEmailId = "educacciademo@gmail.com"; // Sender's email address

    /**
     * Sends a generic email to a recipient with a personalized message.
     * 
     * @param recipient Recipient's email address
     * @param body      Email body content
     * @param subject   Email subject
     * @param name      Recipient's first name
     * @param surname   Recipient's last name
     * @param type      Type of request (e.g., booking, info)
     */
    public void sendEmail(String recipient, String body, String subject, String name, String surname, String type) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(fromEmailId);
        simpleMailMessage.setTo(recipient);
        simpleMailMessage.setText(
            String.format(
                "Gentile %s %s, la sua richiesta di %s è stata inviata con successo:\n\n%s\n\nCordiali saluti,\nLo staff di Educaccia",
                surname, name, type, body
            )
        );
        simpleMailMessage.setSubject(subject);
        javaMailSender.send(simpleMailMessage);
    }

    /**
     * Sends an email confirming the receipt of a booking request.
     * 
     * @param bookRequest Details of the booking request
     * @param type        Type of booking
     */
    public void sendEmailBooking(BookingFormRequest bookRequest, String type) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(fromEmailId);
        simpleMailMessage.setTo(bookRequest.getEmail());
        String emailBody = String.format(
                "Gentile %s %s,\n\n" +
                "La ringraziamo per averci contattato e per aver scelto Cascina Caccia. Siamo lieti di confermare che abbiamo ricevuto la sua richiesta di prenotazione relativa a %s.\n\n" +
                "Di seguito, i dettagli forniti nella sua richiesta:\n\n" +
                "- Tipo di Visitatore: %s\n" +
                "- Numero di Partecipanti: %d\n" +
                "- Data di Inizio: %s\n" +
                "- Data di Fine: %s\n" +
                "- Durata del Soggiorno: %d giorni\n" +
                "- Laboratori Selezionati: %s\n" +
                "- Dettagli Aggiuntivi: %s\n\n" +
                "Il nostro staff procederà con la revisione della sua richiesta e sarà presto in contatto per fornirle ulteriori informazioni o per confermare la prenotazione.\n\n" +
                "Per qualsiasi necessità, non esiti a contattarci all'indirizzo email %s o al numero %s.\n\n" +
                "Cordiali saluti,\n" +
                "Lo Staff di Cascina Caccia\n\n" +
                "Via Serra Alta, 6, 10020 San Sebastiano da Po TO\n" +
                "+39 348 642 5023\n" +
                "cascina.caccia@acmos.net\n" +
                "https://cascinacaccia.net/",
                bookRequest.getName(),
                bookRequest.getSurname(),
                type,
                bookRequest.getVisitorType(),
                bookRequest.getParticipantNumber(),
                bookRequest.getDataFrom() != null ? bookRequest.getDataFrom().toString() : "Non specificata",
                bookRequest.getDataTo() != null ? bookRequest.getDataTo().toString() : "Non specificata",
                bookRequest.getNumberOfDays(),
                bookRequest.getLabs() != null ? String.join(", ", bookRequest.getLabs()) : "Nessuno",
                bookRequest.getAdditionalDetails() != null ? bookRequest.getAdditionalDetails() : "Nessuno",
                "cascina.caccia@acmos.net",
                "+39 348 642 5023" // Replace with actual contact number
            );

            simpleMailMessage.setText(emailBody);
            simpleMailMessage.setSubject("Conferma ricezione richiesta di prenotazione");
            javaMailSender.send(simpleMailMessage);
    }
    public void sendEmailConfirmBooked(BookingRequest bookingRequest) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(fromEmailId);
        simpleMailMessage.setTo(bookingRequest.getReference().getEmail());
        String fraction="";
        if(bookingRequest.getBookedDate().getDayFractions().isFullDay()) {
        	fraction="Tutto il giorno";
        }else if (bookingRequest.getBookedDate().getDayFractions().isMorning()) {
        	fraction="Mattina";
		}
        String emailBody = String.format(
            "Gentile %s %s,\n\n" +
            "Siamo lieti di confermare che la sua richiesta di prenotazione è stata accettata con successo. Di seguito i dettagli della sua prenotazione:\n\n" +
            "- Ore inizio: %s"+
            "- Data di Inizio: %s\n" +
            "- Data di Fine: %s\n\n" +
            "La ringraziamo per aver scelto Cascina Caccia e restiamo a disposizione per qualsiasi necessità.\n\n" +
            "Cordiali saluti,\n" +
            "Lo Staff di Cascina Caccia\n\n" +
            "Via Serra Alta, 6, 10020 San Sebastiano da Po TO\n" +
            "+39 348 642 5023\n" +
            "cascina.caccia@acmos.net\n" +
            "cascinacaccia.net/",
            bookingRequest.getReference().getFirstName(),
            bookingRequest.getReference().getLastName(),
            fraction,
            bookingRequest.getBookedDate().getDate() != null ? bookingRequest.getBookedDate().getDate().toString() : "Non specificata",
            		bookingRequest.getBookedDate().getToDate() != null ? bookingRequest.getBookedDate().getToDate().toString() : "Non specificata");

        simpleMailMessage.setText(emailBody);
        simpleMailMessage.setSubject("Prenotazione Confermata");
        javaMailSender.send(simpleMailMessage);
    }
    /**
     * Sends an email notifying the cancellation of a booking.
     * 
     * @param bookingRequest Booking details
     */
    public void sendEmailCancelledBooked(BookingRequest bookingRequest) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(fromEmailId);
        simpleMailMessage.setTo(bookingRequest.getReference().getEmail());
        String emailBody = String.format(
            "Gentile %s %s,\n\n" +
            "La informiamo che la sua richiesta di prenotazione è stata annullata. Di seguito i dettagli della prenotazione annullata:\n\n" +
            "- Data di Inizio: %s\n" +
            "- Data di Fine: %s\n\n" +
            "Se desidera ulteriori informazioni o assistenza, non esiti a contattarci.\n\n" +
            "Cordiali saluti,\n" +
            "Lo Staff di Cascina Caccia\n\n" +
            "Via Serra Alta, 6, 10020 San Sebastiano da Po TO\n" +
            "+39 348 642 5023\n" +
            "cascina.caccia@acmos.net\n" +
            "https://cascinacaccia.net/",
            bookingRequest.getReference().getFirstName(),
            bookingRequest.getReference().getLastName(),
            bookingRequest.getBookedDate().getDate() != null ? bookingRequest.getBookedDate().getDate().toString() : "Non specificata",
            bookingRequest.getBookedDate().getToDate() != null ? bookingRequest.getBookedDate().getToDate().toString() : "Non specificata"
        );

        simpleMailMessage.setText(emailBody);

        simpleMailMessage.setSubject("Prenotazione Annullata");
        javaMailSender.send(simpleMailMessage);
    }

    /**
     * Sends an email to all administrators with details of a user request.
     * 
     * @param user         User's email
     * @param bodyMessage  Content of the user's request
     * @param subject      Subject of the email
     * @param name         User's first name
     * @param surname      User's last name
     * @param type         Type of request
     * @param phone        User's phone number
     */
    public void sendEmailToAdmin(String user, String bodyMessage, String subject, String name, String surname, String type, String phone) {
        String baseText = String.format(
            "Richiesta di %s da parte di:\n" +
            "Nome: %s %s\n" +
            "Email: %s\n\n" +
            "Phone: %s\n\n" +
            "Messaggio ricevuto:\n%s\n\n",
            type, name, surname, user, phone, bodyMessage
        );

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(fromEmailId);
        simpleMailMessage.setText(baseText);
        simpleMailMessage.setSubject(subject);

        // Send the email to all admins
        List<Admin> admins = adminRepo.findAll();
        for (Admin admin : admins) {
            simpleMailMessage.setTo(admin.getEmail());
            javaMailSender.send(simpleMailMessage);
        }
    }
    /**
     * Sends a verification code to reset a user's password.
     *
     * @param email User's email address
     * @param code  Verification code
     */
    public void sendPasswordEmailRest(String email, String code) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(fromEmailId);
        simpleMailMessage.setTo(email);

        String emailBody = String.format(
            "Gentile Utente,\n\n" +
            "Abbiamo ricevuto una richiesta per reimpostare la password del suo account. Di seguito il codice di verifica necessario per completare l'operazione:\n\n" +
            "- Codice di Verifica: %s\n\n" +
            "Se non ha richiesto questa operazione, ignori questa email.\n\n" +
            "Cordiali saluti,\n" +
            "Lo Staff di Cascina Caccia\n\n" +
            "Via Serra Alta, 6, 10020 San Sebastiano da Po TO\n" +
            "+39 348 642 5023\n" +
            "cascina.caccia@acmos.net\n" +
            "https://cascinacaccia.net/",
            code
        );

        simpleMailMessage.setText(emailBody);
        simpleMailMessage.setSubject("Codice di Verifica per Reimpostare la Password");
        javaMailSender.send(simpleMailMessage);
    }

}
