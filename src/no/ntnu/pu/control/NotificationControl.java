package no.ntnu.pu.control;

import no.ntnu.pu.model.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NotificationControl {


	
	public void sendNotification(){
		
	}	
	
	public void receiveNotification(){
		
	}

    public static Iterable<Notification> getNotifications(){
        //TODO: return all notifications in model

        //Testkode
        List<Notification> list = new ArrayList<Notification>();
        list.add(new Alarm(new Date(5, 4, 2014), new Person(""), new Appointment("Møte med mordi")));
        list.add(new Invitation(new Person("Send"), new Person("rec"), new Appointment("FIXA bILEN")));
        list.add(new ChangeNotification(new String[]{"Title", "Date"}, new Person("rec"), new Appointment("Møte med mordi")));
        list.add(new DeclineNotification(new Person("Kari"), new Person("rec"), new Appointment("FIXA N")));
    
        return list;
    }
}
