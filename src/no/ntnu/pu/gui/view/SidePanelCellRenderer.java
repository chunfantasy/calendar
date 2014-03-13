package no.ntnu.pu.gui.view;

import javafx.scene.control.ListCell;
import no.ntnu.pu.model.Alarm;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;

/**
 * Created by Lima on 12.03.14.
 */
public class SidePanelCellRenderer extends JLabel implements ListCellRenderer {

    protected  DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();

    @Override
    public Component getListCellRendererComponent(JList list, Object value,
                                                  int index, boolean isSelected,
                                                  boolean cellHasFocus) {

        if(value instanceof String){
            String s = (String) value;
            setText(s);
            if(isSelected){
                setBackground(Color.BLUE);
                setForeground(Color.CYAN);
            }else{
                setForeground(list.getForeground());
                setBackground(list.getBackground());
            }
            setOpaque(true);
        }
        return this;
    }
}
