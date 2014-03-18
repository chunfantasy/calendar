package no.ntnu.pu.control;

import no.ntnu.pu.model.Appointment;
import no.ntnu.pu.model.Calendar;
import no.ntnu.pu.model.Notification;
import no.ntnu.pu.model.Person;
import no.ntnu.pu.storage.ServerStorage;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CalendarControl {
	private static ServerStorage storage;
    private static Calendar model;

    public static Calendar getModel() {
        return model;
    }

    public static ArrayList<Appointment> getAppointments(){
        return model.getAppointments();
    }

    public static List<Notification> getNotifications(){
        return model.getNotifications();
    }

    public static void setModel(Calendar model) {
        CalendarControl.model = model;
    }

    public void CalendarControl() throws SQLException {
		storage = new ServerStorage();
        storage.connect();
        Person test = new Person("test");
        test.setEmail("haakon.t@gmail.com");
        test.setPassword("test123");
        test.setCalendar(new Calendar());
        test.setId(123);
        storage.insertPerson(test);
	}
	
	public static void addAppointment(Appointment appointment) {
		storage.insertAppointment(appointment);
	}
	public static void deleteAppointment(Appointment appointment) {
		storage.deleteAppointmentById(appointment.getId());
	}
	public static void updateAppointment(Appointment appointment) {
		storage.updateAppointment(appointment);
	}

}
