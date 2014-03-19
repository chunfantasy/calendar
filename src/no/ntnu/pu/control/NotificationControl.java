package no.ntnu.pu.control;

import no.ntnu.pu.model.*;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NotificationControl{


    public static void sendInvitation(Appointment a){
        for(Participant participant : a.getParticipants()){
            Person p = (Person) participant;
            p.getCalendar().addNotification(new Invitation(a.getCreator(), p, a));
        }
    }

	public void sendChangeNotification(Appointment a, ArrayList changedProp){
        for(Participant participant : a.getParticipants()){
            Person p = (Person) participant;
            p.getCalendar().addNotification(new ChangeNotification(changedProp,p, a));
        }
	}

    public void sendDeclineNotification(Appointment a, Person decliner){
        for(Participant participant : a.getParticipants()){
            Person p = (Person) participant;
            p.getCalendar().addNotification(new DeclineNotification(decliner, p, a));
        }
    }
	

}
