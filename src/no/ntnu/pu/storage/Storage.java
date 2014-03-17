package no.ntnu.pu.storage;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import no.ntnu.pu.model.Appointment;
import no.ntnu.pu.model.Group;
import no.ntnu.pu.model.Participant;
import no.ntnu.pu.model.Person;

public interface Storage {

	// Person
	public Person getPersonByEmail(String email) throws SQLException;

	public ArrayList<Person> getPersonByName(String email) throws SQLException;

	public Person insertPerson(Person p) throws SQLException;

	public boolean deletePersonByEmail(String email) throws SQLException;

	// Group
	public Group getGroupByEmail(String email) throws SQLException;

	public Group insertGroup(Group g) throws SQLException;

	public boolean deleteGroupByEmail(Group g) throws SQLException;

	// Appointment
	public ArrayList<Appointment> getAppointmentByTime(Date startTime,
			Date endTime) throws SQLException;

	public ArrayList<Appointment> getAppointmentByParticipant(Participant p)
			throws SQLException;
}
