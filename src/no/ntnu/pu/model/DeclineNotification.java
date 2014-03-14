package no.ntnu.pu.model;

public class DeclineNotification extends Notification {
	private Person decliner;

    public DeclineNotification(Person decliner, Person recipient, Appointment appointment){
        super(recipient, appointment);
        this.decliner = decliner;
    }

    public Person getDecliner() {
        return decliner;
    }

    public void setDecliner(Person decliner) {
        this.decliner = decliner;
    }

}
