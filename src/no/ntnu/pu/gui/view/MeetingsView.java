package no.ntnu.pu.gui.view;

import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class MeetingsView extends SidePanel{

    private static Border meetingsBorder = new TitledBorder(LineBorder.createGrayLineBorder(),"Møter");

    public MeetingsView(){
        super();
        this.setBorder(meetingsBorder);

    }
}
