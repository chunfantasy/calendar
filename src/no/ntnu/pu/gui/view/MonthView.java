package no.ntnu.pu.gui.view;

import sun.util.calendar.Gregorian;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.GregorianCalendar;

public class MonthView extends JPanel{

    private JLabel      monthLabel,
                        yearLabel;
    private JButton     previousButton,
                        nextButton;
    private JTable      calendarTable;
    private JComboBox   yearComboBox;
    private JScrollPane tableScrollPane;
    private DefaultTableModel calendarTableModel;
    private int         realYear,
                        realMonth,
                        realDay,
                        currentYear,
                        currentMonth;

    public MonthView() {
        try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());}
        catch (ClassNotFoundException e) {}
        catch (InstantiationException e) {}
        catch (IllegalAccessException e) {}
        catch (UnsupportedLookAndFeelException e) {}

        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        monthLabel = new JLabel("Januar");
        yearLabel = new JLabel("Endre år:");
        yearComboBox = new JComboBox();
        previousButton = new JButton ("Forrige");
        nextButton = new JButton ("Neste");
        calendarTableModel = new DefaultTableModel(){
            public boolean isCellEditable(int rowIndex, int mColIndex){return false;}
        };
        calendarTable = new JTable(calendarTableModel);
        tableScrollPane = new JScrollPane(calendarTable);

        setBorder(BorderFactory.createTitledBorder("Månedsvisning"));

        previousButton.addActionListener(new previous_Action());
        nextButton.addActionListener(new next_Action());
        yearComboBox.addActionListener(new year_Action());

        panelAdd(0.5, 1, 0, constraints, monthLabel);
        panelAdd(0.5, 0, 3, constraints, yearLabel);
        panelAdd(0.5, 2, 3, constraints, yearComboBox);
        panelAdd(0.5, 0, 0, constraints, previousButton);
        panelAdd(0.5, 2, 0, constraints, nextButton);
        panelAdd(0.0, 0, 2, constraints, calendarTable);
        panelAdd(0.0, 0, 1, constraints, calendarTable.getTableHeader());


        setBounds(0, 0, 320, 335);

        GregorianCalendar cal = new GregorianCalendar();
        realDay = cal.get(GregorianCalendar.DAY_OF_MONTH);
        realMonth = cal.get(GregorianCalendar.MONTH);
        realYear = cal.get(GregorianCalendar.YEAR);
        currentMonth = realMonth;
        currentYear = realYear;

        String[] headers = {"Man", "Tir", "Ons", "Tor", "Fre", "Lør", "Søn"};
        for (int i = 0; i<7; i++){
            calendarTableModel.addColumn(headers[i]);
        }

        calendarTable.getParent().setBackground(calendarTable.getBackground());
        calendarTable.getTableHeader().setResizingAllowed(false);
        calendarTable.getTableHeader().setReorderingAllowed(false);
        calendarTable.setRowHeight(38);
        calendarTableModel.setColumnCount(7);
        calendarTableModel.setRowCount(6);

        for(int i = 2000; i < realYear + 100; i++){
            yearComboBox.addItem(String.valueOf(i));
        }

        refreshCalendar(realMonth, realYear);
    }

    public void panelAdd(double weightx, int gridx, int gridy, GridBagConstraints c, Component comp){
        if(comp.equals(calendarTable) || comp.equals(calendarTable.getTableHeader())){
            c.fill = GridBagConstraints.HORIZONTAL;
            c.gridwidth = 3;

        }
        else {
            c.weighty = 1.0;
        }
        c.gridx = gridx;
        c.gridy = gridy;
        c.weightx = weightx;
        add(comp, c);
        c.weighty = 0.0;
    }

    private void refreshCalendar(int month, int year){
        String[] months = {"Januar", "Februar", "Mars", "April", "Mai", "Juni", "Juli", "August", "September", "Oktober", "November", "Desember"};
        int numberOfDays, startOfMonth;

        previousButton.setEnabled(true);
        nextButton.setEnabled(true);
        if(month == 0 && year <= realYear - 10){
            previousButton.setEnabled(false);
        }
        if(month == 11 && year >= realYear + 100){
            nextButton.setEnabled(false);
        }

        monthLabel.setText(months[month]);
        monthLabel.setBounds(160 - monthLabel.getPreferredSize().width/2, 25, 180, 25);
        yearComboBox.setSelectedItem(String.valueOf(year));

        for(int i = 0; i<6; i++) {
            for(int j = 0; j<7; j++){
                calendarTableModel.setValueAt(null, i, j);
            }
        }

        GregorianCalendar cal = new GregorianCalendar(year, month, 1);
        numberOfDays = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
        startOfMonth = cal.get(GregorianCalendar.DAY_OF_WEEK);
        startOfMonth = (startOfMonth == 1) ? 7 : startOfMonth - 1;

        for(int i = 1; i <= numberOfDays; i++) {
            int row = new Integer((i + startOfMonth - 2)/7);
            int column = (i + startOfMonth - 2)%7;
            calendarTableModel.setValueAt(i, row, column);
        }
        calendarTable.setDefaultRenderer(calendarTable.getColumnClass(0), new calendarTableRenderer());
    }

    class calendarTableRenderer extends DefaultTableCellRenderer {
        public Component getTableCellRendererComponent(JTable table, Object value, boolean selected, boolean focused, int row, int column) {
            super.getTableCellRendererComponent(table, value, selected, focused, row, column);
            if (column == 6 || column == 5) {
                setBackground(new Color(255, 220, 220));
            }
            else {
                setBackground(new Color(255, 255, 255));
            }
            if (value != null) {
                if (Integer.parseInt(value.toString()) == realDay && currentMonth == realMonth && currentYear == realYear){
                    setBackground(new Color(220, 220, 255));
                }
            }
            setBorder(null);
            setForeground(Color.black);
            return this;
        }
    }

    class previous_Action implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (currentMonth == 0) {
                currentMonth = 11;
                currentYear -= 1;
            }
            else {
                currentMonth -= 1;
            }
            refreshCalendar(currentMonth, currentYear);
        }
    }

    class next_Action implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (currentMonth == 11){
                currentMonth = 0;
                currentYear += 1;
            }
            else {
                currentMonth += 1;
            }
            refreshCalendar(currentMonth, currentYear);
        }
    }

    class year_Action implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (yearComboBox.getSelectedItem() != null) {
                String y = yearComboBox.getSelectedItem().toString();
                currentYear = Integer.parseInt(y);
                refreshCalendar(currentMonth,currentYear);
            }
        }
    }

}
