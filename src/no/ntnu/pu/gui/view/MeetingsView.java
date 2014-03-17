package no.ntnu.pu.gui.view;

import no.ntnu.pu.control.AppointmentControl;
import no.ntnu.pu.model.Appointment;
import no.ntnu.pu.model.Notification;

import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.beans.PropertyChangeEvent;

public class MeetingsView extends SidePanel{

    private static Border meetingsBorder = new TitledBorder(LineBorder.createGrayLineBorder(),"Møter");

    public MeetingsView(){
        super();
        this.setBorder(meetingsBorder);

        for(Appointment a: AppointmentControl.getAppointments()){
            addMeeting(a);
        }
   }

    public void addMeeting(Appointment meeting){
        model.addElement(meeting);
    }

    public void removeMeeting(Appointment meeting){
        model.removeElement(meeting);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()){
            case Calendar.APPOINTMENT_PROPERTY:
                if(evt.getNewValue() instanceof Notification){
                    addMeeting((Appointment) evt.getNewValue());
                }else{
                    removeMeeting((Appointment) evt.getOldValue());
                }

        }
    }
}
