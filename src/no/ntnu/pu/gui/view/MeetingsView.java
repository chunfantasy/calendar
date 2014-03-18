package no.ntnu.pu.gui.view;

import no.ntnu.pu.control.CalendarControl;
import no.ntnu.pu.model.Appointment;

import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class MeetingsView extends SidePanel{

    private static Border meetingsBorder = new TitledBorder(LineBorder.createGrayLineBorder(),"Møter");

    public MeetingsView(){
        super();
        this.setBorder(meetingsBorder);

        for(Appointment a: CalendarControl.getAppointments()){
            addMeeting(a);
        }
   }

    public void addMeeting(Appointment meeting){
        model.addElement(meeting);
    }

    public void removeMeeting(Appointment meeting){
        model.removeElement(meeting);
    }
}
