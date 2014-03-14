package no.ntnu.pu.model;

public class Invitation extends Notification {
    private Person sender;

    public Invitation(Person sender, Person recipient, Appointment appointment){
        super(recipient, appointment);
        this.sender = sender;
    }

    public void setSender(Person sender) {
        this.sender = sender;
    }

    public Person getSender() {
        return sender;
    }
}
