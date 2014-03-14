package no.ntnu.pu.gui.view;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lima on 12.03.14.
 */
public abstract class SidePanel extends JPanel {


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

        list.setPreferredSize(new Dimension(280,270));
        add(list);
    }

    protected void addElement(Object value){
        model.addElement(value);
    }

    protected void removeElement(Object value){
        model.removeElement(value);
    }

}
