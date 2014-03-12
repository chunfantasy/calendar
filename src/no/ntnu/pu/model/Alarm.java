package no.ntnu.pu.model;

import java.util.Date;

public class Alarm {
	private Date time;
	private Participant[] recipient;

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Participant[] getRecipient() {
		return recipient;
	}

	public void setRecipient(Participant[] recipient) {
		this.recipient = recipient;
	}

}
