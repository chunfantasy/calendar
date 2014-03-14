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

    private boolean midweekChangeNext = false, midweekChangePrev = false, newYear = false;
    private JLabel  weekLabel, dateSpanLabel;
    private int numberOfWeeksInMonth,
            realWeekOfMonth,
            currentWeekOfMonth,
            numberOfDays,
            lastDayOfWeek,
            numberOfWeeksInYear;

    public WeekView(){
        super();

        /**Initialize subclass-specific components**/
        weekLabel = new JLabel("Uke 1");
        dateSpanLabel = new JLabel("(01.01 - 07.01)");

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
        panelAdd(1, 0.5, 1.0, 3, 3, yearLabel, GridBagConstraints.EAST);
        panelAdd(1, 0.5, 1.0, 3, 0, dateSpanLabel, GridBagConstraints.CENTER);
        panelAdd(1, 0.5, 1.0, 0, 0, previousButton, GridBagConstraints.CENTER);
        panelAdd(1, 0.5, 1.0, 4, 0, nextButton, GridBagConstraints.CENTER);
        panelAdd(1, 0.5, 1.0, 4, 3, yearComboBox, GridBagConstraints.WEST);
        panelAdd(5, 1.0, 1.0, 0, 2, tableScrollPane, GridBagConstraints.CENTER);
        panelAdd(5, 0.0, 0.0, 0, 2, calendarTable.getTableHeader(), GridBagConstraints.CENTER);

        /**Set border**/
        setBorder(BorderFactory.createTitledBorder("Ukevisning"));

        String[] headers = {"Tid","Man", "Tir", "Ons", "Tor", "Fre", "Lør", "Søn"};
        for (int i = 0; i<8; i++){
            calendarTableModel.addColumn(headers[i]);
        }

        calendarTable.setRowHeight(25);
        calendarTableModel.setRowCount(24);
        calendarTableModel.setColumnCount(8);

        realWeekOfMonth = gregCal.get(GregorianCalendar.WEEK_OF_MONTH);
        currentWeekOfMonth = realWeekOfMonth;

        refreshCalendar(realDay, realWeek, realWeekOfMonth, realMonth,realYear);
    }

    private void refreshCalendar(int day, int week, int weekOfMonth, int month, int year) {
        int startOfMonth;

        previousButton.setEnabled(true);
        nextButton.setEnabled(true);
        if(week == 1 && year <= realYear - 10){
            previousButton.setEnabled(false);
        }
        if(week == 53 && year >= realYear + 100){
            nextButton.setEnabled(false);
        }

        if(midweekChangeNext){
            if(month != 11){
                dateSpanLabel.setText("(" +currentDay+ "." +currentMonth+ " - " +(currentDay+6)+ "." +(currentMonth+1)+ ")");
                monthLabel.setText(months[month]+"/"+months[month+1]);
            }
            else{
                monthLabel.setText(months[11]+"/"+months[0]);
                dateSpanLabel.setText("(" +currentDay+ "." +currentMonth+ " - " +(currentDay+1)+ "." +(currentMonth+1)+ ")");

            }
        }
        else if(midweekChangePrev) {
            if(month != 0) {
                monthLabel.setText(months[month-1]+"/"+months[month]);
            }
            else{
                monthLabel.setText(months[11]+"/"+months[0]);
            }
        }
        else{
            dateSpanLabel.setText("(" + currentDay + "." + (currentMonth+1) + " - " + (currentDay+6) + "." + (currentMonth+1) + ")");
            monthLabel.setText(months[month]);
        }
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
        numberOfWeeksInYear = gregCal.getActualMaximum(GregorianCalendar.WEEK_OF_YEAR);
        numberOfWeeksInMonth = gregCal.getActualMaximum(GregorianCalendar.WEEK_OF_MONTH);
        numberOfDays = gregCal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
        startOfMonth = gregCal.get(GregorianCalendar.DAY_OF_WEEK);

        calendarTable.setDefaultRenderer(calendarTable.getColumnClass(0), new calendarTableRenderer());
    }

    class previous_Action implements ActionListener {
        public void actionPerformed(ActionEvent e) {

            gregCal.add(GregorianCalendar.WEEK_OF_YEAR, -1);

            while(gregCal.get(GregorianCalendar.DAY_OF_WEEK) != GregorianCalendar.SUNDAY){
                gregCal.add(GregorianCalendar.DATE, +1);
            }

            currentDay = gregCal.get(GregorianCalendar.DAY_OF_MONTH);
            currentYear = gregCal.get(GregorianCalendar.YEAR);
            currentWeek = gregCal.get(GregorianCalendar.WEEK_OF_YEAR);
            currentMonth = gregCal.get(GregorianCalendar.MONTH);

            for(int i = 0; i < 7; i++){
                if(currentDay - i < 0){
                    midweekChangePrev = true;
                }
                else{
                    midweekChangePrev = false;
                }

            }

            refreshCalendar(currentDay, currentWeek, currentWeekOfMonth, currentMonth, currentYear);
        }
    }

    class next_Action implements ActionListener {
        public void actionPerformed(ActionEvent e) {

            gregCal.add(GregorianCalendar.WEEK_OF_YEAR, 1);

            while(gregCal.get(GregorianCalendar.DAY_OF_WEEK) != GregorianCalendar.MONDAY){
                gregCal.add(GregorianCalendar.DATE, -1);
            }

            currentDay = gregCal.get(GregorianCalendar.DAY_OF_MONTH);
            currentYear = gregCal.get(GregorianCalendar.YEAR);
            currentWeek = gregCal.get(GregorianCalendar.WEEK_OF_YEAR);
            currentMonth = gregCal.get(GregorianCalendar.MONTH);

            for(int i = 0; i < 7; i++){
                if(currentDay + i >= numberOfDays){
                    midweekChangeNext = true;
                }
                else{
                    midweekChangeNext = false;
                }

            }

            refreshCalendar(currentDay, currentWeek, currentWeekOfMonth, currentMonth, currentYear);
        }
    }
    class year_Action implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (yearComboBox.getSelectedItem() != null) {
                String y = yearComboBox.getSelectedItem().toString();
                currentYear = Integer.parseInt(y);
                refreshCalendar(currentDay, currentWeek, currentWeekOfMonth, currentMonth,currentYear);
            }
        }
    }
}