package no.ntnu.pu.control;

import no.ntnu.pu.model.Person;
import no.ntnu.pu.storage.PersonStorage;

import java.util.ArrayList;

public class PersonControl {

    public static PersonStorage serverStorage = new PersonStorage();

    public static Person getPersonByEmail(String email){
        return serverStorage.getPersonByEmail(email);
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