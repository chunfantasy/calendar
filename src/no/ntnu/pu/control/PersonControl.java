package no.ntnu.pu.control;

import no.ntnu.pu.model.Appointment;
import no.ntnu.pu.model.Calendar;
import no.ntnu.pu.model.Person;
import no.ntnu.pu.model.Room;
import no.ntnu.pu.storage.ServerStorage;

import java.util.ArrayList;
import java.util.Date;

public class PersonControl {

    public static ServerStorage serverStorage = new ServerStorage();

    public static Person getPersonByEmail(String email){
        /**TEST TEST TEST**/
        Person test = new Person("Håkon");
        test.setEmail(email);
        test.setId(001);
        test.setPassword("test123");
        test.setTitle("Mr.");
        test.setCalendar(new Calendar());
        test.getCalendar().addAppointment(new Appointment(test));
        ArrayList<Appointment> appointments = test.getCalendar().getAppointments();
        appointments.get(0).setTitle("Syrefest");
        appointments.get(0).setId(001);
        appointments.get(0).setAddress("Syregata");
        appointments.get(0).setStartTime(new Date(114, 2, 19));
        appointments.get(0).setDescription("KJEMPEGØY");
        appointments.get(0).setEndTime(new Date());
        appointments.get(0).setMeetingRoom(new Room(""));
        return test;
        /**TEST TEST TEST**/
        //return serverStorage.getPersonByEmail(email);
    }

    public static Person getPersonById(int id){
        return serverStorage.getPersonById(id);
    }

    public static ArrayList<Person> getPersonByName(String name){
        return serverStorage.getPersonByName(name);
    }

    public static Person insertPerson(Person p){
        return serverStorage.insertPerson(p);
    }

    public static boolean deletePersonById(int id){
        return serverStorage.deletePersonById(id);
    }

    public static boolean deletePersonByEmail(String email){
        return serverStorage.deletePersonByEmail(email);
    }
}