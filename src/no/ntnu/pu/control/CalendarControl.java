package no.ntnu.pu.control;

import no.ntnu.pu.model.Appointment;
import no.ntnu.pu.model.Calendar;
import no.ntnu.pu.model.Notification;
import no.ntnu.pu.model.Person;
import no.ntnu.pu.storage.AppointmentStorage;
import no.ntnu.pu.storage.ServerStorage;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CalendarControl {
	private static AppointmentStorage storage = new AppointmentStorage();
    private static Calendar model;

    public static Calendar getModel() {
        return model;
    }

    public static List<Appointment> getAppointments(){
        return model.getAppointments();
    }

    public static List<Notification> getNotifications(){
        return model.getNotifications();
    }

    public static void setModel(Calendar model) {
        CalendarControl.model = model;
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
