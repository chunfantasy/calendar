package no.ntnu.pu.gui.view;

import no.ntnu.pu.control.AppointmentControl;
import no.ntnu.pu.model.Appointment;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.GregorianCalendar;

public class WeekView extends CalenderView {

    private boolean midweekChange = false, prev = false, next = false, newYear = false;
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

        refreshCalendar(realWeek, realMonth,realYear);
    }

    private void refreshCalendar( int week,  int month, int year) {

        previousButton.setEnabled(true);
        nextButton.setEnabled(true);
        if(week == 1 && year <= realYear - 10){
            previousButton.setEnabled(false);
        }
        if(week == 53 && year >= realYear + 100){
            nextButton.setEnabled(false);
        }

        if(next){
            int var = 6 - (gregCal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH) - currentDay);

            if(midweekChange){
                if(month != 11){
                    dateSpanLabel.setText("(" +currentDay+ "." +(currentMonth+1)+ " - " + var + "." +(currentMonth+2)+ ")");
                    monthLabel.setText(months[month]+"/"+months[month+1]);
                }
                else{
                    monthLabel.setText(months[11]+"/"+months[0]);
                    dateSpanLabel.setText("(" +currentDay+ "." +currentMonth+ " - " +(currentDay+1)+ "." +(currentMonth+1)+ ")");
                }
            }
            else{
                dateSpanLabel.setText("(" + currentDay + "." + (currentMonth+1) + " - " + (currentDay+6) + "." + (currentMonth+1) + ")");
                monthLabel.setText(months[month]);
            }
        }
        else if(prev) {
            gregCal.add(GregorianCalendar.MONTH, -1);
            int varDay = gregCal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH) - (6 - currentDay);
            int varMonth = gregCal.get(GregorianCalendar.MONTH)+1;
            gregCal.add(GregorianCalendar.MONTH, 1);

            if(midweekChange){
                if(month != 0) {
                    dateSpanLabel.setText("(" + varDay + "." +(currentMonth)+ " - " + currentDay + "." +(currentMonth+1)+ ")");
                    monthLabel.setText(months[month-1]+"/"+months[month]);
                }
                else{

                    dateSpanLabel.setText("(" + varDay + "." + varMonth + " - " + currentDay + "." +(currentMonth+1)+ ")");
                    monthLabel.setText(months[11]+"/"+months[0]);
                }
            }
            else {
                dateSpanLabel.setText("(" + (currentDay-6) + "." + (currentMonth+1) + " - " + currentDay + "." + (currentMonth+1) + ")");
                monthLabel.setText(months[month]);
            }

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

        calendarTable.setDefaultRenderer(calendarTable.getColumnClass(0), new calendarTableRenderer());
    }

    class previous_Action implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            prev = true;

            gregCal.add(GregorianCalendar.WEEK_OF_YEAR, -1);

            while(gregCal.get(GregorianCalendar.DAY_OF_WEEK) != GregorianCalendar.SUNDAY){
                gregCal.add(GregorianCalendar.DATE, 1);
            }

            currentDay = gregCal.get(GregorianCalendar.DAY_OF_MONTH);
            currentYear = gregCal.get(GregorianCalendar.YEAR);
            currentWeek = gregCal.get(GregorianCalendar.WEEK_OF_YEAR);
            currentMonth = gregCal.get(GregorianCalendar.MONTH);

            for(int i = 0; i < 7; i++){
                if(currentDay - i < 0){
                    midweekChange = true;
                }
                else{
                    midweekChange = false;
                }
            }

            refreshCalendar(currentWeek, currentMonth, currentYear);
            prev = false;
        }
    }

    class next_Action implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            next = true;

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
                    midweekChange = true;
                }
                else{
                    midweekChange = false;
                }

            }

            refreshCalendar(currentWeek, currentMonth, currentYear);
            next = false;
        }
    }
    class year_Action implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (yearComboBox.getSelectedItem() != null) {
                String y = yearComboBox.getSelectedItem().toString();
                int var = Integer.parseInt(y);
                var = var - currentYear;
                gregCal.add(GregorianCalendar.YEAR, var);

                currentDay = gregCal.get(GregorianCalendar.DAY_OF_MONTH);
                currentYear = gregCal.get(GregorianCalendar.YEAR);
                currentWeek = gregCal.get(GregorianCalendar.WEEK_OF_YEAR);
                currentMonth = gregCal.get(GregorianCalendar.MONTH);

                next = true;
                prev = true;
                refreshCalendar(currentWeek, currentMonth,currentYear);
                next = false;
                prev = false;
            }
        }
    }
}