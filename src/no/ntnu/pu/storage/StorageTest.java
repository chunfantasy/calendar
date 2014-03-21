package no.ntnu.pu.storage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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


	}
}
