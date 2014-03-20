package no.ntnu.pu.gui.view;

import no.ntnu.pu.control.CalendarControl;
import no.ntnu.pu.model.Appointment;
import no.ntnu.pu.model.Calendar;
import no.ntnu.pu.model.Notification;

import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.beans.PropertyChangeEvent;

public class MeetingsView extends SidePanel{

    private static Border meetingsBorder = new TitledBorder(LineBorder.createGrayLineBorder(),"Møter");

    public MeetingsView(){
        super();
        CalendarControl.getModel().addPropertyChangeListener(this);
        this.setBorder(meetingsBorder);

        for(Appointment a: CalendarControl.getAppointments()){
            addMeeting(a);
        }
   }

    public void addMeeting(Appointment meeting){
        System.out.print(meeting);
        model.addElement(meeting);
    }

    public void removeMeeting(Appointment meeting){
        model.removeElement(meeting);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(evt.getPropertyName()== Calendar.APPOINTMENT_PROPERTY){
            if(evt.getNewValue() instanceof Appointment){
                addMeeting((Appointment) evt.getNewValue());
            }else{
                removeMeeting((Appointment) evt.getOldValue());
            }

        }
    }
}
