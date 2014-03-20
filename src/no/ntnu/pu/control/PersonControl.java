package no.ntnu.pu.control;

import no.ntnu.pu.model.Appointment;
import no.ntnu.pu.model.Calendar;
import no.ntnu.pu.model.Person;
import no.ntnu.pu.model.Room;
import no.ntnu.pu.storage.PersonStorage;

import java.util.ArrayList;
import java.util.Date;

public class PersonControl {

    public static PersonStorage storage = new PersonStorage();

    public static Person model;

    public static ArrayList<Person> getAll(){
        return storage.getAll();
    }

    public static Person getPersonByEmail(String email){
        return storage.getPersonByEmail(email);
    }

    public static Person getPersonById(int id){
        return storage.getPersonById(id);
    }

    public static ArrayList<Person> getPersonByName(String name){
        return storage.getPersonByName(name);
    }

    public static Person insertPerson(Person p){
        return storage.insertPerson(p);
    }

    public static boolean deletePersonById(int id){
        return storage.deletePersonById(id);
    }

    public static boolean deletePersonByEmail(String email){
        return storage.deletePersonByEmail(email);
    }

    public static Person getModel() {
        return model;
    }

    public static void setModel(Person model) {
        PersonControl.model = model;
        CalendarControl.setModel(model.getCalendar());
    }
}