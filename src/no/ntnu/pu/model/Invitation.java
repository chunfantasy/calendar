package no.ntnu.pu.model;

public class Invitation {
	private Person sender;
	private Participant[] recipient;

	public Person getSender() {
		return sender;
	}

	public void setSender(Person sender) {
		this.sender = sender;
	}

	public Participant[] getRecipient() {
		return recipient;
	}

	public void setRecipient(Participant[] recipient) {
		this.recipient = recipient;
	}

}
