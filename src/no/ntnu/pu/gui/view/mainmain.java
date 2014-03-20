package no.ntnu.pu.gui.view;

import no.ntnu.pu.control.CalendarControl;
import no.ntnu.pu.model.*;
import no.ntnu.pu.storage.AppointmentStorage;
import no.ntnu.pu.storage.PersonStorage;

import java.util.Date;

/**
 * Created by Lima on 20.03.14.
 */
public class mainmain {

    public static void main(String args[]){

        AppointmentStorage appointmentStorage = new AppointmentStorage();
        PersonStorage personStorage = new PersonStorage();

        Appointment a = appointmentStorage.getAll().get(0);
        a.addParticipant(personStorage.getPersonById(1));
        appointmentStorage.updateAppointment(a);


    }
}
