package no.ntnu.pu.control;

import no.ntnu.pu.model.*;
import no.ntnu.pu.storage.AlarmStorage;
import no.ntnu.pu.storage.ChangeNotificationStorage;
import no.ntnu.pu.storage.DeclineNotificationStorage;
import no.ntnu.pu.storage.InvitationStorage;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NotificationControl{

    private static AlarmStorage alarmStorage = new AlarmStorage();
    private static ChangeNotificationStorage changeNotificationStorage = new ChangeNotificationStorage();
    private static DeclineNotificationStorage declineNotificationStorage = new DeclineNotificationStorage();
    private static InvitationStorage invitationStorage = new InvitationStorage();


    public static void addAlarm(Alarm a){
        alarmStorage.insertAlarm(a);
    }

    public static void sendInvitation(Appointment a){
        for(Participant participant : a.getParticipants()){
            Person p = (Person) participant;
            if(!p.getCalendar().getAppointments().contains(a)){
                Invitation in = new Invitation(a.getCreator(), p, a);
                invitationStorage.insertInvitation(in);
            }
        }
    }

	public static void sendChangeNotification(Appointment a, ArrayList changedProp){
        for(Participant participant : a.getParticipants()){
            Person p = (Person) participant;
            changeNotificationStorage.insertChangeNotification(new ChangeNotification(changedProp, p, a));
        }
	}

    public static void sendDeclineNotification(Appointment a, Person decliner){
        for(Participant participant : a.getParticipants()){
            Person p = (Person) participant;
            declineNotificationStorage.insertDeclineNotification(new DeclineNotification(decliner, p, a));
        }
    }

    public static List<Notification> getNotificationsByParticipant(Person p){
        ArrayList<Notification> notifications = new ArrayList<Notification>();
        for(Alarm alarm : alarmStorage.getAlarmByRecipient(p)){
            notifications.add(alarm);
        }
        for(ChangeNotification changeNotification : changeNotificationStorage.getChangeNotificationByRecipient(p)){
            notifications.add(changeNotification);
        }
        for(DeclineNotification declineNotification : declineNotificationStorage.getDeclineNotificationByRecipient(p)){
            notifications.add(declineNotification);
        }
        for(Invitation invitation : invitationStorage.getInvitationByRecipient(p)){
            notifications.add(invitation);
        }
        return notifications;
    } 
    public static void removeNotification(Notification n){
        if(n instanceof ChangeNotification)
            changeNotificationStorage.deleteChangeNotificationById(n.getId());
        if(n instanceof Alarm)
            alarmStorage.deleteAlarmById(n.getId());
        if(n instanceof Invitation){
            invitationStorage.deleteInvitationById(n.getId());
        if(n instanceof DeclineNotification)
            declineNotificationStorage.deleteDeclineNotificationById(n.getId());
    }
}

}
