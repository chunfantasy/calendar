package no.ntnu.pu.storage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import no.ntnu.pu.model.Alarm;
import no.ntnu.pu.model.Appointment;
import no.ntnu.pu.model.ChangeNotification;
import no.ntnu.pu.model.DeclineNotification;
import no.ntnu.pu.model.Group;
import no.ntnu.pu.model.Invitation;
import no.ntnu.pu.model.Person;
import no.ntnu.pu.model.Room;

public class StorageTest {
	public static void main(String[] args) {
		ServerStorage serverStorage = new ServerStorage();
		PersonStorage personStorage = new PersonStorage();
		GroupStorage groupStorage = new GroupStorage();
		RoomStorage roomStorage = new RoomStorage();
		AlarmStorage alarmStorage = new AlarmStorage();
		ChangeNotificationStorage changeNotificationStorage = new ChangeNotificationStorage();
		InvitationStorage invitationStorage = new InvitationStorage();
		DeclineNotificationStorage declineNotificationStorage = new DeclineNotificationStorage();
		AppointmentStorage appointmentStorage = new AppointmentStorage();

		serverStorage.initiate();

		Person p1, p2, p3, p4, p5, p6;
		p1 = new Person("Anders");
		p1.setEmail("anders@email.com");
		p1.setTitle("tittle");
		p1.setPassword("test");
		p1.addPhoneNumber("4712345678");
		p1.addPhoneNumber("4712345670");
		p1 = personStorage.insertPerson(p1);

		p2 = new Person("Chun");
		p2.setEmail("chun@email.com");
		p2.setTitle("title");
		p2.setPassword("test");
		p2.addPhoneNumber("4712345678");
		p2.addPhoneNumber("4712345670");
		p2 = personStorage.insertPerson(p2);

		p3 = new Person("Petter");
		p3.setEmail("anders@email.com");
		p3.setTitle("title");
		p3.setPassword("test");
		p3.addPhoneNumber("4712345678");
		p3.addPhoneNumber("4712345670");
		p3 = personStorage.insertPerson(p3);

		p4 = new Person("Håkon");
		p4.setEmail("haakon@email.com");
		p4.setTitle("title");
		p4.setPassword("test");
		p4.addPhoneNumber("4712345678");
		p4.addPhoneNumber("4712345670");
		p4 = personStorage.insertPerson(p4);

		p5 = new Person("Said");
		p5.setEmail("said@email.com");
		p5.setTitle("title");
		p5.setPassword("test");
		p5.addPhoneNumber("4712345678");
		p5.addPhoneNumber("4712345670");
		p5 = personStorage.insertPerson(p5);

		p6 = new Person("Nils");
		p6.setEmail("nils@email.com");
		p6.setTitle("title");
		p6.setPassword("test");
		p6.addPhoneNumber("4712345678");
		p6.addPhoneNumber("4712345670");
		p6 = personStorage.insertPerson(p6);

		Group g = new Group("Group12");
		g.addPerson(p1);
		g.addPerson(p1);
		g.addPerson(p3);
		g.addPerson(p4);
		g.addPerson(p5);
		g.addPerson(p6);
		groupStorage.insertGroup(g);

		Room room;
		for (int i = 0; i < 10; i++) {
			room = new Room("G" + i);
			Random random = new Random();
			room.setCapacity((random.nextInt(10) + 1) * 3);
			roomStorage.insertRoom(room);
		}

		room = roomStorage.getRoomById(5);
		Appointment a = new Appointment();
		a.setCreator(p1);
		a.addParticipant(p2);
		a.addParticipant(p3);
		a.setStartTime(new Date(114, 2, 19, 10, 0, 0));
		a.setEndTime(new Date(114, 2, 19, 16, 0, 0));
		a.setMeetingRoom(room);
		appointmentStorage.insertAppointment(a);

		room = roomStorage.getRoomById(6);
		a.setCreator(p1);
		a.addParticipant(p3);
		a.addParticipant(p4);
		a.setStartTime(new Date(114, 2, 20, 10, 0, 0));
		a.setEndTime(new Date(114, 2, 20, 16, 0, 0));
		a.setMeetingRoom(room);
		appointmentStorage.insertAppointment(a);

		room = roomStorage.getRoomById(7);
		a.setCreator(p2);
		a.addParticipant(g);
		a.setStartTime(new Date(114, 2, 20, 9, 0, 0));
		a.setEndTime(new Date(114, 2, 20, 3, 0, 0));
		a.setMeetingRoom(room);
		appointmentStorage.insertAppointment(a);

	}
}
