package no.ntnu.pu.model;

import java.util.Date;
import java.util.ArrayList;

public class Appointment {
	private String title;
	private Date startTime;
	private Date endTime;
	private String adress;
	private Room meetingRoom;
	private String description;
	private ArrayList<Participant> participants;

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
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
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

	public void setParticipants(ArrayList<Participant> participants) {
		this.participants = participants;
	}

}
