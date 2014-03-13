package no.ntnu.pu.gui.view;

import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class NotificationView extends SidePanel{

    //private List<Notification> notificationList;

    private static Border notificatonBorder = new TitledBorder(LineBorder.createGrayLineBorder(),"Notifikasjoner");

    public NotificationView(){
        super();
        this.setBorder(notificatonBorder);

/*        notificationList.addAll(Calender.getNotifications());
        for (Notification n: notificationList){
            SidePanelCellRenderer.makeNotification(n);
        }*/
    }
}
