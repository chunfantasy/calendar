package no.ntnu.pu.model;

import java.util.Date;


public class Alarm extends Notification {
	private Date time;

    public Alarm(Date time, Person[] recipient, Appointment appointment){
        super(recipient, appointment);
        this.time = time;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
