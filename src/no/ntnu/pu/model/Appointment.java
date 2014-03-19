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

	public Appointment(String title) {
		this.title = title;
		this.participants = new ArrayList();
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
	public Long getStartTimeLong() {
		return startTime.getTime();
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public void setStartTimeByLong(Long inputStartTime) {
		this.startTime = new Date(inputStartTime);
	}

	public Date getEndTime() {
		return endTime;
	}
	public Long getEndTimeLong() {
		return endTime.getTime();
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public void setEndTimeByLong(Long inputEndTime) {
		this.endTime = new Date(inputEndTime);
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

	public void setParticipants(ArrayList<Participant> participants) {
		this.participants = participants;
	}

}
