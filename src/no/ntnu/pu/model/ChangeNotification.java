package no.ntnu.pu.model;

public class ChangeNotification extends Notification {
	private String[] changedProperties;

    public ChangeNotification(String[] changedProperties, Person[] recipient, Appointment appointment){
        super(recipient, appointment);
        this.changedProperties = changedProperties;
    }

    public void setChangedProperties(String[] changedProperties) {
        this.changedProperties = changedProperties;
    }

    public String[] getChangedProperties() {
        return changedProperties;
    }
}
