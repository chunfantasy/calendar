package no.ntnu.pu.gui.view;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Created by Lima on 12.03.14.
 */
public abstract class SidePanel extends JPanel {

    protected JList list;

    public SidePanel(){
        list = new JList();


        add(list);
    }

}
