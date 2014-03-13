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

    public SidePanel(){
        this.setPreferredSize(new Dimension(300,300));
        DefaultListModel model = new DefaultListModel();
        ListCellRenderer renderer = new DefaultListCellRenderer();

        model.ensureCapacity(10);

        list = new JList(model);

        list.setCellRenderer(renderer);

        list.setBackground(super.getBackground());

        for(int i =0; i<10;i++){
            model.addElement(Integer.toString(i));
        }

        add(list);
    }

}
