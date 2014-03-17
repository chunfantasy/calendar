package no.ntnu.pu.control;

import no.ntnu.pu.model.Appointment;

import java.util.ArrayList;
import java.util.List;

public class AppointmentControl {

    public static void createAppointment(){

    }

    public static void deleteAppointment(Appointment appointment){

    }

    public static Iterable<Appointment> getAppointments(){
        //TODO: return all appointments in model

        //Testkode
        List<Appointment> list = new ArrayList<Appointment>();
        list.add(new Appointment("Morgenmøte"));
        list.add(new Appointment("LØNSJ!"));
        list.add(new Appointment("Begravelse"));

        return list;
    }
}
