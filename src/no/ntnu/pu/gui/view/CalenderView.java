package no.ntnu.pu.gui.view;

import no.ntnu.pu.model.Appointment;
import no.ntnu.pu.model.Calendar;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.util.GregorianCalendar;

public class CalenderView extends JPanel {

    protected String[] months = { "Januar", "Februar", "Mars", "April", "Mai", "Juni", "Juli", "August", "September", "Oktober", "November", "Desember"};
    protected GridBagLayout       gridbag;
    protected GridBagConstraints  constraints;
    protected JScrollPane         tableScrollPane;
    protected DefaultTableModel   calendarTableModel;
    protected JTable              calendarTable;
    protected JTableHeader        calendarTableHeader;
    protected JLabel              yearLabel, monthLabel;
    protected JButton             previousButton, nextButton;
    protected JComboBox           yearComboBox;
    protected GregorianCalendar   gregCal;

    protected int   realDay,
            realWeek,
            realMonth,
            realYear,
            currentDay,
            currentWeek,
            currentMonth,
            currentYear;

    public CalenderView(){

        /**Set look and feel, catch exceptions**/
        try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());}
        catch (ClassNotFoundException e) {}
        catch (InstantiationException e) {}
        catch (IllegalAccessException e) {}
        catch (UnsupportedLookAndFeelException e) {}

        gridbag = new GridBagLayout();
        setLayout(gridbag);
        constraints = new GridBagConstraints();

        /**Initialize components**/
        monthLabel = new JLabel("Januar");
        yearLabel = new JLabel("Endre år: ");
        previousButton = new JButton("Forrige");
        nextButton = new JButton("Neste");
        yearComboBox = new JComboBox();
        calendarTableModel = new DefaultTableModel(){
            public boolean isCellEditable(int rowIndex, int mColIndex){return false;}
        };
        calendarTable = new JTable(calendarTableModel);
        calendarTableHeader = calendarTable.getTableHeader();
        tableScrollPane = new JScrollPane(calendarTable);
        tableScrollPane.setPreferredSize(new Dimension(200, 200));

        /**Set bounds**/
        setBounds(0, 0, 614, 400);
        setPreferredSize(new Dimension(614, 400));

        /**Create calendar**/
        gregCal = new GregorianCalendar();

        realDay = gregCal.get(GregorianCalendar.DAY_OF_MONTH);
        realWeek = gregCal.get(GregorianCalendar.WEEK_OF_YEAR);
        realMonth = gregCal.get(GregorianCalendar.MONTH);
        realYear = gregCal.get(GregorianCalendar.YEAR);

        while(gregCal.get(GregorianCalendar.DAY_OF_WEEK) != GregorianCalendar.MONDAY){
            gregCal.add(GregorianCalendar.DATE, -1);
        }

        currentDay = gregCal.get(GregorianCalendar.DAY_OF_MONTH);
        currentWeek = realWeek;
        currentMonth = realMonth;
        currentYear = realYear;

        calendarTable.setColumnSelectionAllowed(true);
        calendarTable.setRowSelectionAllowed(true);
        calendarTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        calendarTable.getParent().setBackground(calendarTable.getBackground());
        calendarTableHeader.setResizingAllowed(false);
        calendarTableHeader.setReorderingAllowed(false);

        for(int i = realYear - 10; i <= realYear + 100; i++){
            yearComboBox.addItem(String.valueOf(i));
        }
    }

    protected void panelAdd(int gridwidth, double weightx, double weighty, int gridx, int gridy, Component comp, int anchor){
        if(comp.equals(tableScrollPane)){
            constraints.fill = GridBagConstraints.BOTH;
        }
        constraints.anchor = anchor;
        constraints.gridx = gridx;
        constraints.gridy = gridy;
        constraints.weightx = weightx;
        constraints.weighty = weighty;
        constraints.gridwidth = gridwidth;
        gridbag.setConstraints(comp, constraints);
        add(comp);
    }

    protected class calendarTableRenderer extends DefaultTableCellRenderer {
        public Component getTableCellRendererComponent(JTable table, Object value, boolean selected, boolean focused, int row, int column) {
            super.getTableCellRendererComponent(table, value, selected, focused, row, column);

            if (calendarTable.getColumnCount() - column == 1 || calendarTable.getColumnCount() - column == 2) {
                setBackground(new Color(255, 220, 220));
            }
            else{
                setBackground(new Color(255, 255, 255));
            }

            if(calendarTable.getColumnCount() == 8){
                if(column == 0){
                    setBackground(Color.LIGHT_GRAY);
                }
                else if(selected){
                    setBackground(new Color(220, 255, 220));
                }
            }
            else if(calendarTable.getColumnCount() == 7) {
                if (value != null && (value instanceof Integer)) {
                    if (Integer.parseInt(value.toString()) == realDay && currentMonth == realMonth && currentYear == realYear){
                        setBackground(new Color(107, 94, 255));
                    }
                }
                if(value == null){
                    setBackground(new Color(215, 215, 215));
                }

                if(selected){
                    if(value!= null){
                        setBackground(new Color(220, 255, 220));
                    }
                    else{
                        setBackground(new Color(215, 215, 215));
                    }
                }
            }
            setBorder(null);
            setForeground(Color.black);
            return this;
        }
    }
}