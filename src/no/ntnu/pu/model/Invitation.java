package no.ntnu.pu.model;

public class Invitation extends Notification {

    public Invitation(Person[] recipient, Appointment appointment){
        super(recipient, appointment);
    }
}
