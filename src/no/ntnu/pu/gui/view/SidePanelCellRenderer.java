package no.ntnu.pu.gui.view;

import no.ntnu.pu.model.*;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

/**
 * Created by Lima on 12.03.14.
 */
public class SidePanelCellRenderer extends JLabel implements ListCellRenderer {

    private static final Color ALARM_COLOR = new Color(205, 0, 0);
    private static final Color INVITATION_COLOR = new Color(65, 121, 205);
    private static final Color CHANGENOTI_COLOR= new Color(205, 201, 0);
    private static final Color DECLINENOTI_COLOR = new Color(205, 134, 0);
    private static final Color APPOINTMENT_COLOR = new Color(0, 205, 0);


    @Override
    public Component getListCellRendererComponent(JList list, Object value,
                                                  int index, boolean isSelected,
                                                  boolean cellHasFocus) {

        if(value instanceof Invitation){
            Invitation invi = (Invitation) value;
            setText("Invitasjon til: " + invi.getAppointment().getTitle());
            setCellBorder(INVITATION_COLOR,cellHasFocus);

        }else if(value instanceof Alarm){
            Alarm a = (Alarm) value;
            setText(a.getAppointment().getTitle()+" "+a.getTime());
            if(cellHasFocus){
                this.setBorder(BorderFactory.createMatteBorder(
                        0, 6, 3, 0, ALARM_COLOR));
            }else{
                this.setBorder(BorderFactory.createMatteBorder(
                        0, 8, 3, 0, ALARM_COLOR));
            }

        }else if(value instanceof ChangeNotification){
            ChangeNotification cn = (ChangeNotification) value;
            setText(String.valueOf(cn.getChangedProperties().size())+" felt er endret i "+ cn.getAppointment().getTitle());
            setCellBorder(CHANGENOTI_COLOR,cellHasFocus);

        }else if(value instanceof DeclineNotification){
            DeclineNotification dn = (DeclineNotification) value;
            setText(dn.getDecliner().getName()+ " har avslått invitasjonen til "+dn.getAppointment().getTitle());
            setCellBorder(DECLINENOTI_COLOR,cellHasFocus);

        }else if(value instanceof Appointment){
            Appointment app = (Appointment) value;
            setText(app.getTitle()+ " klokken "+app.getStartTime());
            setCellBorder(APPOINTMENT_COLOR,cellHasFocus);
        }

        setOpaque(true);
        return this;
    }

    private void setCellBorder(Color c, Boolean focus){
        if(focus){
            this.setBorder(BorderFactory.createMatteBorder(
                0, 4, 1, 0, c));
        }else{
            this.setBorder(BorderFactory.createMatteBorder(
                    0, 6, 1, 0, c));
        }
    }

    @Override
    public void setText(String s){
        super.setText(" "+s);
    }
}
