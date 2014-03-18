package no.ntnu.pu.storage;

import java.util.ArrayList;
import java.util.Date;

import no.ntnu.pu.model.Appointment;
import no.ntnu.pu.model.Group;
import no.ntnu.pu.model.Participant;
import no.ntnu.pu.model.Person;
import no.ntnu.pu.model.Room;

public interface Storage {

	// Person
	public Person insertPerson(Person p);

	public boolean updatePerson(Person p);

	public boolean deletePersonById(int id);

	public boolean deletePersonByEmail(String email);

	public Person getPersonById(int id);

	public Person getPersonByEmail(String email);

	public ArrayList<Person> getPersonByName(String name);

	// Group
	public Group insertGroup(Group g);

	public boolean updateGroup(Group g);

	public boolean deleteGroupById(int id);

	public boolean deleteGroupByEmail(String email);

	public Group getGroupById(int id);

	public Group getGroupByEmail(String email);

	public ArrayList<Group> getGroupByName(String name);

	// Room
	public Room insertRoom(Room r);

	public boolean updateRoom(Room r);

	public boolean deleteRoomById(int id);

	public Room getRoomById(int id);

	// Appointment
	public Appointment insertAppointment(Appointment a);

	public boolean updateAppointment(Appointment a);

	public boolean deleteAppointmentById(int id);

	public ArrayList<Appointment> getAppointmentByTime(Date startTime,
			Date endTime);

	public ArrayList<Appointment> getAppointmentByParticipant(Participant p);
}
