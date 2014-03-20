package no.ntnu.pu.storage;

import no.ntnu.pu.model.Person;

/**
 * Created by Lima on 19.03.14.
 */
public class StorageInitiate {
    public static void main(String args[]){

        ServerStorage serverStorage = new ServerStorage();
        PersonStorage personStorage = new PersonStorage();
        GroupStorage groupStorage = new GroupStorage();
        RoomStorage roomStorage = new RoomStorage();
        AppointmentStorage appointmentStorage = new AppointmentStorage();

        serverStorage.initiate();

        Person p1;
        p1 = new Person("Anders Lima");
        p1.setEmail("anders92lima@yahoo.no");
        p1.setTitle("GITMASTER");
        p1.setPassword("test");
        p1.addPhoneNumber("92424995");
        p1.addPhoneNumber("547345809");
        String aaa = p1.getPhoneNumbers().toString();
        String aaaaa[] = aaa.substring(1, aaa.length() - 1).split(", ");
        p1 = personStorage.insertPerson(p1);

        Person p2 = new Person("b");
        p2.setEmail("email2");
        p2.setTitle("title2");
        p2.setPassword("test");
        p2 = personStorage.insertPerson(p2);

        Person p3 = new Person("c");
        p3.setEmail("email3");
        p3.setTitle("title3");
        p3.setPassword("test");
        p3 = personStorage.insertPerson(p3);

    }
}
