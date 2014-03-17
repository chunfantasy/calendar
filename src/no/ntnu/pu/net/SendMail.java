package no.ntnu.pu.net;

/**
 * Created by Lima on 17.03.14.
 */
// File Name SendEmail.java

import no.ntnu.pu.model.Email;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

public class SendMail
{
    public SendMail(Email mail)
    {
        // Recipient's email ID needs to be mentioned.
        String to = mail.getRecipient();

        // Sender's email ID needs to be mentioned
        String from = mail.getSender();

        // Assuming you are sending email from localhost
        String host = "localhost";

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.setProperty("mail.smtp.host", host);

        // Get the default Session object.
        Session session = Session.getDefaultInstance(properties);

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
