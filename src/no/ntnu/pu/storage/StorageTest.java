package no.ntnu.pu.storage;

import java.util.ArrayList;
import java.util.Date;

import no.ntnu.pu.model.Appointment;
import no.ntnu.pu.model.Group;
import no.ntnu.pu.model.Participant;
import no.ntnu.pu.model.Person;
import no.ntnu.pu.model.Room;

public class StorageTest {
	public static void main(String[] args) {
		ServerStorage serverStorage = new ServerStorage();
		PersonStorage personStorage = new PersonStorage();
		GroupStorage groupStorage = new GroupStorage();
		RoomStorage roomStorage = new RoomStorage();
		AppointmentStorage appointmentStorage = new AppointmentStorage();

		serverStorage.initiate();

		Person p1 = new Person("a");
		p1.setEmail("email1");
		p1.setTitle("title1");
		p1.setPassword("test");
		p1 = personStorage.insertPerson(p1);

		Person p2 = new Person("b");
		p2.setEmail("email2");
		p2.setTitle("title2");
		p2.setPassword("test");
		p2 = personStorage.insertPerson(p2);

		Person p3 = new Person("c");
		p3.setEmail("email3");
		p3.setTitle("title3");
		p3.setPassword("test");
		p3 = personStorage.insertPerson(p3);

		Person p = new Person("");
		p = personStorage.getPersonByEmail("email2");
		System.out.println(p.getPassword());

		Group g = new Group("super group 12");
		g.addPerson(p1);
		g = groupStorage.insertGroup(g);
		g.addPerson(p3);
		groupStorage.updateGroup(g);

		g.removePerson(p1);
		g.removePerson(p1);
		g.removePerson(p1);
		groupStorage.updateGroup(g);

		g.addPerson(p2);
		groupStorage.updateGroup(g);

		g = groupStorage.getGroupById(1);

		p1.setEmail("email111");
		personStorage.updatePerson(p1);

		Room r = new Room("P15");
		r.setId(1);
		roomStorage.insertRoom(r);

		Appointment a = new Appointment();
		a.setTitle("gogogo");
		a.setStartTime(new Date());
		a.setEndTime(new Date());
		a.setMeetingRoom(r);
		a.addParticipant(p2);
		a.addParticipant(p3);
		a.addParticipant(g);
		appointmentStorage.insertAppointment(a);

		a.setTitle("comecomecome");
		a.addParticipant(g);
		a.addParticipant(g);
		a.addParticipant(g);
		a.addParticipant(p2);
		appointmentStorage.updateAppointment(a);

		appointmentStorage.updateAppointment(a);

		ArrayList<Appointment> appointmentByTime = appointmentStorage
				.getAppointmentByParticipant(p3);
		System.out.println(appointmentByTime);

	}

}
