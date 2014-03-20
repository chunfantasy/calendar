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

}
