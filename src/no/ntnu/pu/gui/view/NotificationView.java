package no.ntnu.pu.gui.view;

import no.ntnu.pu.control.NotificationControl;
import no.ntnu.pu.model.*;

import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.util.Date;

public class NotificationView extends SidePanel{

    //private List<Notification> notificationList;

    private static Border notificationBorder = new TitledBorder(LineBorder.createGrayLineBorder(),"Notifikasjoner");

    public NotificationView(){
        super();
        this.setBorder(notificationBorder);

        for(Notification n: NotificationControl.getNotifications()){
            this.addNotification(n);
        }

    }

    public void addNotification(Notification notification){
        addElement(notification);
    }

    public void removeNotification(Notification notification){
        removeElement(notification);
    }
}