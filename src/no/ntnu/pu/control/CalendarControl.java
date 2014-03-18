package no.ntnu.pu.control;

import no.ntnu.pu.model.Appointment;
import no.ntnu.pu.model.Calendar;
import no.ntnu.pu.model.Notification;
import no.ntnu.pu.storage.ServerStorage;

import java.util.List;

public class CalendarControl {
	private static ServerStorage storage;
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

    public void CalendarControl() {
		storage = new ServerStorage();
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
