package no.ntnu.pu.control;

import no.ntnu.pu.model.Appointment;
import no.ntnu.pu.storage.ServerStorage;

public class CalenderControl {
	static ServerStorage storage;
	
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
