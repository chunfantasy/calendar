package no.ntnu.pu.gui.view;

import no.ntnu.pu.gui.panel.MeetingPanel;
import no.ntnu.pu.model.Appointment;
import no.ntnu.pu.model.Notification;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lima on 12.03.14.
 */
public abstract class SidePanel extends JPanel implements PropertyChangeListener{


    protected JList list;

    protected DefaultListModel model;

    public SidePanel(){
        this.setPreferredSize(new Dimension(300,300));
        model = new DefaultListModel();
        ListCellRenderer renderer = new SidePanelCellRenderer();

        model.ensureCapacity(10);

        list = new JList(model);

        list.setCellRenderer(renderer);

        list.setBackground(super.getBackground());

        list.addMouseListener(new ListClickListener());

        list.setPreferredSize(new Dimension(280,270));
        add(list);
    }

    protected void addElement(Object value){
        model.addElement(value);
    }

    protected void removeElement(Object value){
        model.removeElement(value);
    }

    class ListClickListener extends MouseAdapter{

        @Override
        public void mouseClicked(MouseEvent mouseEvent){
            if(mouseEvent.getClickCount() == 2){
                int index = list.locationToIndex(mouseEvent.getPoint());
                if(index >= 0){
                    Object o = list.getModel().getElementAt(index);

                    //Open the AppointmentView
                    if(o instanceof Appointment){
                        new AppointmentView((Appointment) o);
                    }else{
                        new AppointmentView(((Notification) o).getAppointment());
                    }
                }
            }
        }

    }
}
