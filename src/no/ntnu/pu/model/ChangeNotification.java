package no.ntnu.pu.model;

import java.util.List;

public class ChangeNotification extends Notification {
	private List<String> changedProperties;

    public ChangeNotification(List<String> changedProperties, Person recipient, Appointment appointment){
        super(recipient, appointment);
        this.changedProperties = changedProperties;
    }

    public void setChangedProperties(List<String> changedProperties) {
        this.changedProperties = changedProperties;
    }

    public List<String> getChangedProperties() {
        return changedProperties;
    }
}
