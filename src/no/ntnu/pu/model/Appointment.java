package no.ntnu.pu.model;

import java.sql.Time;
import java.util.ArrayList;

public class Appointment {
	private String title;
	private Time startTime;
	private Time endTime;
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
	public Time getStartTime() {
		return startTime;
	}
	public void setStartTime(Time startTime) {
		this.startTime = startTime;
	}
	public Time getEndTime() {
		return endTime;
	}
	public void setEndTime(Time endTime) {
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
