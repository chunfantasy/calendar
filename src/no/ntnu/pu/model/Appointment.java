package no.ntnu.pu.model;

import java.util.Date;
import java.util.ArrayList;

public class Appointment {

	private int id;
	private String title;
	private Date startTime;
	private Date endTime;
	private String address;
	private Room meetingRoom;
	private String description;
	private ArrayList<Participant> participants;
    private final Person creator;

	public Appointment(Person creator) {
        this.creator = creator;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
        this.title = title;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
        Date oldVal = getStartTime();
		this.startTime = startTime;

	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Room getMeetingRoom() {
		return meetingRoom;
	}

	public void setMeetingRoom(Room meetingRoom) {
		this.meetingRoom = meetingRoom;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ArrayList<Participant> getParticipants() {
		return participants;
	}

	public void addParticipant(Participant participant) {
        this.participants.add(participant);
	}

    public Person getCreator() {
        return creator;
    }

}
