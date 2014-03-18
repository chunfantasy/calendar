package no.ntnu.pu.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Date;
import java.util.ArrayList;

public class Appointment {

    public final static String  TITLE_PROPERTY = "title",
                                STARTTIME_PROPERTY ="startTime",
                                ENDTIME_PROPERTY = "endTime",
                                ADDRESS_PROPERTY = "address",
                                MEETINGROOM_PROPERTY = "meetingRoom",
                                PARTICIPANTS_PROPERTY = "participants";

	private int id;
	private String title;
	private Date startTime;
	private Date endTime;
	private String address;
	private Room meetingRoom;
	private String description;
	private ArrayList<Participant> participants;
    private PropertyChangeSupport pcs;

	public Appointment(String title) {
		this.title = title;
        pcs = new PropertyChangeSupport(this);
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
		String oldVal = getTitle();
        this.title = title;
        pcs.firePropertyChange(TITLE_PROPERTY,oldVal,title);
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
        Date oldVal = getStartTime();
		this.startTime = startTime;
        pcs.firePropertyChange(STARTTIME_PROPERTY,oldVal,startTime);

	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
        Date oldVal = getEndTime();
		this.endTime = endTime;
        pcs.firePropertyChange(ENDTIME_PROPERTY,oldVal,endTime);
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
        String oldVal = getAddress();
		this.address = address;
        pcs.firePropertyChange(ADDRESS_PROPERTY,oldVal,address);
	}

	public Room getMeetingRoom() {
		return meetingRoom;
	}

	public void setMeetingRoom(Room meetingRoom) {
        Room oldVal= getMeetingRoom();
		this.meetingRoom = meetingRoom;
        pcs.firePropertyChange(MEETINGROOM_PROPERTY,oldVal,meetingRoom);
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
		ArrayList<Participant> oldVal = getParticipants();
        this.participants.add(participant);
        pcs.firePropertyChange(PARTICIPANTS_PROPERTY,oldVal,participant);
	}

}
