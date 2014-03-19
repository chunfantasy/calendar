package no.ntnu.pu.net;

/**
 * Created by Lima on 17.03.14.
 */

import no.ntnu.pu.model.Email;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

public class SendMail
{
    public SendMail(Email mail)
    {
        final String username = "gigakalender@gmail.com";
        final String password = "kalendergiga";

        // Recipient's email ID needs to be mentioned.
        String to = mail.getRecipient();

        // Sender's email ID needs to be mentioned
        String from = mail.getSender();

        // Assuming you are sending email from localhost
        String host = "localhost";

        // Get system properties
        Properties props = System.getProperties();

        // Setup mail server
        props.setProperty("mail.smtp.host", host);
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");


        // Get the default Session object.
        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(username, password);
        }});

        try{
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(to));

            // Set Subject: header field
            message.setSubject(mail.getSubject());

            // Now set the actual message
            message.setText(mail.getMessage());

            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
        }catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}
