package no.ntnu.pu.gui.view;

import no.ntnu.pu.control.AppointmentControl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.GregorianCalendar;

public class WeekView extends CalenderView {

    private JLabel  weekLabel;
    private int numberOfWeeksInMonth,
            numberOfDays,
            numberOfWeeksInYear;

    public WeekView(){
        super();

        /**Initialize subclass-specific components**/
        weekLabel = new JLabel("Uke 1");

        /**Add listeners**/
        previousButton.addActionListener(new previous_Action());
        nextButton.addActionListener(new next_Action());
        yearComboBox.addActionListener(new year_Action());
        calendarTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    AppointmentControl.createAppointment();
                }
            }
        });

        /**Add components to panel**/
        panelAdd(1, 0.5, 1.0, 2, 0, monthLabel, GridBagConstraints.CENTER);
        panelAdd(1, 0.5, 1.0, 1, 0, weekLabel, GridBagConstraints.CENTER);
        panelAdd(1, 0.5, 1.0, 2, 3, yearLabel, GridBagConstraints.EAST);
        panelAdd(1, 0.5, 1.0, 0, 0, previousButton, GridBagConstraints.CENTER);
        panelAdd(1, 0.5, 1.0, 3, 0, nextButton, GridBagConstraints.CENTER);
        panelAdd(1, 0.5, 1.0, 3, 3, yearComboBox, GridBagConstraints.WEST);
        panelAdd(4, 1.0, 1.0, 0, 2, tableScrollPane, GridBagConstraints.CENTER);
        panelAdd(4, 0.0, 0.0, 0, 2, calendarTable.getTableHeader(), GridBagConstraints.CENTER);

        /**Set border**/
        setBorder(BorderFactory.createTitledBorder("Ukevisning"));

        String[] headers = {"Tid","Man", "Tir", "Ons", "Tor", "Fre", "Lør", "Søn"};
        for (int i = 0; i<8; i++){
            calendarTableModel.addColumn(headers[i]);
        }

        calendarTable.setRowHeight(25);
        calendarTableModel.setRowCount(24);
        calendarTableModel.setColumnCount(8);

        refreshCalendar(realWeek, realMonth,realYear);
    }

    private void refreshCalendar(int week, int month, int year) {
        int startOfMonth;

        previousButton.setEnabled(true);
        nextButton.setEnabled(true);
        if(week == 1 && year <= realYear - 10){
            previousButton.setEnabled(false);
        }
        if(week == 53 && year >= realYear + 100){
            nextButton.setEnabled(false);
        }

        monthLabel.setText(months[month]);
        weekLabel.setText("Uke " + week);
        yearComboBox.setSelectedItem(String.valueOf(year));

        for(int i = 0; i<24; i++) {
            for(int j = 0; j<7; j++) {
                calendarTableModel.setValueAt(null, i, j);
            }

        }
        for(int i = 0; i<24; i++){
            calendarTableModel.setValueAt(String.valueOf(i) + ":00", i, 0);

        }
        gregCal = new GregorianCalendar(year,month, 1);
        numberOfWeeksInYear = gregCal.getActualMaximum(GregorianCalendar.WEEK_OF_YEAR);
        numberOfWeeksInMonth = gregCal.getActualMaximum(GregorianCalendar.WEEK_OF_MONTH);
        numberOfDays = gregCal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
        startOfMonth = gregCal.get(GregorianCalendar.DAY_OF_WEEK);
        startOfMonth = (startOfMonth == 1) ? 7 : startOfMonth - 1;

        calendarTable.setDefaultRenderer(calendarTable.getColumnClass(0), new calendarTableRenderer());
    }

    class previous_Action implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if(currentWeek % 4 == 1){
                if (currentWeek == 1) {
                    currentWeek = 52;
                    currentYear -= 1;
                    currentMonth = 11;
                }
                else {
                    currentMonth -= 1;
                    currentWeek -= 1;
                }
            }
            else {
                currentWeek -= 1;
            }
            refreshCalendar(currentWeek, currentMonth, currentYear);
        }
    }

    class next_Action implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if(currentWeek % 4 == 0){

                if (currentWeek == 52){
                    currentWeek = 1;
                    currentYear += 1;
                    currentMonth = 0;
                }
                else {
                    currentMonth += 1;
                    currentWeek += 1;
                }
            }
            else {
                currentWeek += 1;
            }
            refreshCalendar(currentWeek, currentMonth, currentYear);
        }
    }
    class year_Action implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (yearComboBox.getSelectedItem() != null) {
                String y = yearComboBox.getSelectedItem().toString();
                currentYear = Integer.parseInt(y);
                refreshCalendar(currentWeek, currentMonth,currentYear);
            }
        }
    }
}