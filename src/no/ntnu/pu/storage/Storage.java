package no.ntnu.pu.storage;

import java.util.ArrayList;
import java.util.Date;

import no.ntnu.pu.model.Appointment;
import no.ntnu.pu.model.Group;
import no.ntnu.pu.model.Participant;
import no.ntnu.pu.model.Person;

public interface Storage {

	// Person
	public Person getPersonByEmail(String email);

	public Person insertPerson(Person p);

	public boolean deletePerson(Person p);

	// Group
	public Group getGroupByEmail(String email);

	public Group insertGroup(Group g);

	public boolean deleteGroup(Group g);

	// Appointment
	public ArrayList<Appointment> getAppointmentByTime(Date startTime,
			Date endTime);

	public ArrayList<Appointment> getAppointmentByParticipant(Participant p);
}
