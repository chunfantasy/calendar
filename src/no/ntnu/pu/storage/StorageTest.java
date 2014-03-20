package no.ntnu.pu.storage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import no.ntnu.pu.model.Alarm;
import no.ntnu.pu.model.Appointment;
import no.ntnu.pu.model.ChangeNotification;
import no.ntnu.pu.model.DeclineNotification;
import no.ntnu.pu.model.Group;
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
		DeclineNotificationStorage declineNotificationStorage = new DeclineNotificationStorage();
		AppointmentStorage appointmentStorage = new AppointmentStorage();

		serverStorage.initiate();

		Person p1;
		p1 = new Person("Anders");
		p1.setEmail("email1");
		p1.setTitle("title1");
		p1.setPassword("test");
		p1.addPhoneNumber("198739834275");
		p1.addPhoneNumber("547345809");
		String aaa = p1.getPhoneNumbers().toString();
		String aaaaa[] = aaa.substring(1, aaa.length() - 1).split(", ");
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

		Group g = new Group("super group 12");
		Group g2 = new Group("super group 13");
		Group g3 = new Group("super group 14");
		g.addPerson(p1);
		g = groupStorage.insertGroup(g);
		g = groupStorage.insertGroup(g2);
		g = groupStorage.insertGroup(g3);
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
		r.setCapacity(10);
		roomStorage.insertRoom(r);
		
		r.setCapacity(20);
		roomStorage.updateRoom(r);

		Appointment a = new Appointment();
		a.setTitle("gogogo");
		a.setStartTime(new Date());
		a.setEndTime(new Date());
		a.setMeetingRoom(r);
		a.addParticipant(p2);
		a.addParticipant(p3);
		a.addParticipant(g);
        a.setCreator(p);
		appointmentStorage.insertAppointment(a);

		a.setTitle("comecomecome");
		a.addParticipant(g);
		a.addParticipant(g);
		a.addParticipant(g);
		a.addParticipant(p2);
		appointmentStorage.updateAppointment(a);

		appointmentStorage.updateAppointment(a);
		a = appointmentStorage.getAppointmentById(a.getId());

		Alarm alarm = new Alarm(new Date(), p1, a);
		alarmStorage.insertAlarm(alarm);
		alarm.setRecipient(p2);
		alarmStorage.updateAlarm(alarm);

		List<String> properties = new ArrayList<String>();
		properties.add("asefgthklhj");
		properties.add("etyuhgjjkjdg");
		ChangeNotification change = new ChangeNotification(properties, p1, a);
		changeNotificationStorage.insertChangeNotification(change);

		DeclineNotification decline = new DeclineNotification(p1, p2, a);
		declineNotificationStorage.insertDeclineNotification(decline);
		System.out.println(declineNotificationStorage.getAll().get(0).getDecliner().getEmail());

	}
}
