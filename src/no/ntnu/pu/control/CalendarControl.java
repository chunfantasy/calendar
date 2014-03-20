package no.ntnu.pu.control;

import no.ntnu.pu.model.Appointment;
import no.ntnu.pu.model.Calendar;
import no.ntnu.pu.model.Notification;
import no.ntnu.pu.model.Person;
import no.ntnu.pu.storage.AppointmentStorage;

import java.util.ArrayList;
import java.util.List;

public class CalendarControl {
	private static AppointmentStorage storage = new AppointmentStorage();
    private static Calendar model;

    public static Calendar getModel() {
        return model;
    }

    public static ArrayList<Appointment> getAll(){
        return storage.getAll();
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

    public static void getCalendarByPerson(Person person){
        Calendar calendar = new Calendar();
        for(Appointment a : storage.getAppointmentByParticipant(person)){
            calendar.addAppointment(a);
        }
        for(Notification n : NotificationControl.getNotificationsByParticipant(person)){
            calendar.addNotification(n);
        }
        model = calendar;
    }

    public static void refresh(){
        for(Appointment a: model.getAppointments())
            model.removeAppointment(a);
        for(Notification n: model.getNotifications())
            model.removeNotification(n);
        for(Appointment a : getAll())
            model.addAppointment(a);
        for(Notification n : NotificationControl.getNotificationsByParticipant())
            model.addNotification(n);
    }
}
