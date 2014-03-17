package no.ntnu.pu.model;


import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

public class Calendar {
    public final static String  APPOINTMENT_PROPERTY = "appointments",
                                NOTIFICATION_PROPERTY = "notifications";
    private ArrayList<Appointment> appointments;
    private ArrayList<Notification> notifications;
    private PropertyChangeSupport pcs;

    public Calendar(ArrayList<Appointment> appointments, ArrayList<Notification> notifications){
        this.appointments = appointments;
        this.notifications = notifications;
        pcs = new PropertyChangeSupport(this);
    }

    public ArrayList<Appointment> getAppointments() {
        return appointments;
    }

    public void addAppointment(Appointment appointment) {
        ArrayList<Appointment> oldValue = this.appointments;
        this.appointments.add(appointment);
        pcs.firePropertyChange(APPOINTMENT_PROPERTY, oldValue, appointment);
    }

    public void removeAppointment(Appointment appointment){
        if(this.appointments.contains(appointment)){
            Appointment oldValue = appointment;
            this.appointments.remove(appointment);
            pcs.firePropertyChange(APPOINTMENT_PROPERTY, oldValue, this.appointments);
        }
    }

    public ArrayList<Notification> getNotifications() {
        return notifications;
    }

    public void addNotification(Notification notification) {
        ArrayList<Notification> oldValue = this.notifications;
        this.notifications.add(notification);
        pcs.firePropertyChange(NOTIFICATION_PROPERTY, oldValue, notification);
    }

    public void removeNotification(Notification notification){
        if(this.notifications.contains(notification)){
            Notification oldValue = notification;
            this.notifications.remove(notification);
            pcs.firePropertyChange(NOTIFICATION_PROPERTY, oldValue, this.notifications);
        }
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

}

