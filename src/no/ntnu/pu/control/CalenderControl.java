package no.ntnu.pu.control;

import no.ntnu.pu.model.Appointment;
import no.ntnu.pu.storage.ServerStorage;

public class CalenderControl {
	ServerStorage storage;
	
	public void CalendarControl() {
		storage = new ServerStorage();
	}
	
	public void addAppointment(Appointment appointment) {
		storage.insertAppointment(appointment);
	}
	public void deleteAppointment(Appointment appointment) {
		storage.deleteAppointmentById(appointment.getId());
	}
	public void updateAppointment(Appointment appointment) {
		storage.updateAppointment(appointment);
	}

}
