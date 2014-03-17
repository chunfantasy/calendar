package no.ntnu.pu.model;

/**
 * Created by Bakkan on 12.03.14.
 */

public class Calendar {
    Appointment[] appointments;
    Notification[] notifications;

    public Calendar(Appointment[] appointments, Notification[] notifications){
        this.appointments = appointments;
        this.notifications = notifications;
    }

    public Appointment[] getAppointments() {
        return appointments;
    }

    public void setAppointments(Appointment[] appointments) {
        this.appointments = appointments;
    }

    public Notification[] getNotifications() {
        return notifications;
    }

    public void setNotifications(Notification[] notifications) {
        this.notifications = notifications;
    }

}

