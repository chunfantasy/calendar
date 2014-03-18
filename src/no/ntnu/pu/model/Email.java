package no.ntnu.pu.model;

/**
 * Created by Lima on 17.03.14.
 */
public class Email {

    protected String sender;
    protected String recipient;
    protected String subject;
    protected String message;

    public Email(String sender, String recipient, String subject, String message) {
        this.sender = sender;
        this.recipient = recipient;
        this.subject = subject;
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
