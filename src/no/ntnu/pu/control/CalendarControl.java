package no.ntnu.pu.control;

import no.ntnu.pu.model.Appointment;
import no.ntnu.pu.model.Calendar;
import no.ntnu.pu.model.Notification;
import no.ntnu.pu.model.Person;
import no.ntnu.pu.storage.AppointmentStorage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CalendarControl {
	private static AppointmentStorage storage = new AppointmentStorage();

    private static Calendar model;

    public static ArrayList<Appointment> getAll(){
        return storage.getAll();
    }

    public static List<Appointment> getAppointments(){
        return model.getAppointments();
    }

    public static List<Notification> getNotifications(){
        return model.getNotifications();
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
    public static void setModel(Calendar model) {
        CalendarControl.model = model;
        refresh();
    }

    public static Calendar getModel() {
        return model;
    }

    public static Calendar getCalendarByPerson(Person person){
        Calendar calendar = new Calendar();
        for(Appointment a : storage.getAppointmentByParticipant(person)){
            calendar.addAppointment(a);
        }
        for(Notification n : NotificationControl.getNotificationsByParticipant(person)){
            calendar.addNotification(n);
        }
        return calendar;
    }

    public static void refresh(){
        System.out.println("Refreshing");
        for(int i = model.getAppointments().size()-1;i >= 0;i--){
            model.removeAppointment(model.getAppointments().get(i));
        }

        for(int i = model.getNotifications().size()-1;i >= 0; i--){
            model.removeNotification(model.getNotifications().get(i));
        }
        System.out.println(storage.getAppointmentByParticipant(PersonControl.getModel()));

        for(Appointment a : storage.getAppointmentByParticipant(PersonControl.getModel())){
            System.out.println("Added" + a.getTitle());
            model.addAppointment(a);
        }
        for(Notification n : NotificationControl.getNotificationsByParticipant(PersonControl.getModel())){
            System.out.println("Added" + n.getAppointment().getTitle());
            model.addNotification(n);
        }
    }
}
