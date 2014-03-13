package no.ntnu.pu.storage;

import java.util.ArrayList;
import java.util.Date;

import no.ntnu.pu.model.Appointment;
import no.ntnu.pu.model.Group;
import no.ntnu.pu.model.Participant;
import no.ntnu.pu.model.Person;

public class ClientStorage {

	// Person
	public Person getPersonByEmail(String email) {
		Person p = new Person("");
		return p;
	}

	public Person insertPerson(Person p) {
		return p;
	}

	public boolean deletePerson(Person p) {
		return true;
	}

	// Group
	public Group getGroupByEmail(String email) {
		Group g = new Group("");
		return g;
	}

	public Group insertGroup(Group g) {
		return g;
	}

	public boolean deleteGroup(Group g) {
		return true;
	}

	// Appointment
	public ArrayList<Appointment> getAppointmentByTime(Date startTime,
			Date endTime) {
		ArrayList<Appointment> list = new ArrayList<>();
		return list;
	}

	public ArrayList<Appointment> getAppointmentByParticipant(Participant p) {
		ArrayList<Appointment> list = new ArrayList<>();
		return list;
	}

}
