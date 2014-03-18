package no.ntnu.pu.gui.view;

import no.ntnu.pu.model.Appointment;
import no.ntnu.pu.model.Calendar;

import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.util.GregorianCalendar;

public class WeekView extends CalenderView {

    private boolean midweekChange = false;
    private JLabel  weekLabel, dateSpanLabel;
    private TableColumnModel cttcm;
    private String[] headers = {"Tid","Man", "Tir", "Ons", "Tor", "Fre", "Lør", "Søn"};
    private int numberOfDays,
            firstDayOfWeek,
            lastDayOfWeek,
            firstMonth,
            lastMonth;

    public WeekView(){
        super();

        /**Initialize subclass-specific components**/
        weekLabel = new JLabel("Uke 1");
        dateSpanLabel = new JLabel("(01.01 - 07.01)");
        cttcm = calendarTableHeader.getColumnModel();

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
        panelAdd(5, 0.0, 0.0, 0, 2, calendarTableHeader, GridBagConstraints.CENTER);

        /**Set border**/
        setBorder(BorderFactory.createTitledBorder("Ukevisning"));


        for (int i = 0; i<8; i++){
            calendarTableModel.addColumn(headers[i]);
        }

        calendarTable.setRowHeight(25);
        calendarTableModel.setRowCount(24);
        calendarTableModel.setColumnCount(8);

        for(int i = 0; i<24; i++){
            if(i < 10){
                calendarTableModel.setValueAt("0" + String.valueOf(i) + ":00", i, 0);
            }
            else{
                calendarTableModel.setValueAt(String.valueOf(i) + ":00", i, 0);
            }
        }

        calendarTable.setAutoCreateColumnsFromModel(false);

        refreshCalendar(realWeek, realMonth,realYear);
    }

    private void refreshCalendar( int week,  int month, int year) {

        while(gregCal.get(GregorianCalendar.DAY_OF_WEEK) != GregorianCalendar.MONDAY){
            gregCal.add(GregorianCalendar.DATE, -1);
        }

        currentDay = gregCal.get(GregorianCalendar.DAY_OF_MONTH);
        numberOfDays = gregCal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);

        for(int i = 0; i < 7; i++){
            if(currentDay + i > numberOfDays){
                midweekChange = true;
            }
            else{
                midweekChange = false;
            }

        }

        previousButton.setEnabled(!(week == 1 && year <= realYear - 10));
        nextButton.setEnabled(!(week == gregCal.getActualMaximum(GregorianCalendar.WEEK_OF_YEAR) && year >= realYear + 100));
        weekLabel.setText("Uke " + week);
        yearComboBox.setSelectedItem(String.valueOf(year));

        firstDayOfWeek = gregCal.get(GregorianCalendar.DAY_OF_MONTH);
        firstMonth = gregCal.get(GregorianCalendar.MONTH);

        while(gregCal.get(GregorianCalendar.DAY_OF_WEEK) != GregorianCalendar.SUNDAY){
            gregCal.add(GregorianCalendar.DATE, 1);
        }

        lastDayOfWeek = gregCal.get(GregorianCalendar.DAY_OF_MONTH);
        lastMonth = gregCal.get(GregorianCalendar.MONTH);

        dateSpanLabel.setText("(" + firstDayOfWeek + "." + (firstMonth + 1) + " - " + lastDayOfWeek + "." + (lastMonth+1) + ")");
        monthLabel.setText((firstMonth == lastMonth) ? months[month] : months[firstMonth] + "/" + months[lastMonth]);
        if(midweekChange){
            int j = 1;
            for(int i = 1; i < 8; i++){
                if(((firstDayOfWeek+(i-1))) <= numberOfDays){
                    cttcm.getColumn(i).setHeaderValue(headers[i] + "      " + (firstDayOfWeek+i-1)+ "." + (firstMonth+1));
                }
                else{
                    cttcm.getColumn(i).setHeaderValue(headers[i] + "      " + (j)+ "." + (lastMonth + 1));
                    j ++;
                }
                calendarTableHeader.repaint();
            }
        }
        else{
            for(int i = 1; i < 8; i++){
                cttcm.getColumn(i).setHeaderValue(headers[i] + "      " + (firstDayOfWeek+i-1)+ "." + (month+1));
                calendarTableHeader.repaint();
            }
        }

        for(int i = 0; i<24; i++) {
            for(int j = 1; j<8; j++) {
                calendarTableModel.setValueAt(null, i, j);
            }
        }
        /**
        for(int i = 0; i < loggedIn.getAppointments().size(); i++){
            for(int j = 0; j<24; j++) {
                for(int k = 1; k<8; k++) {
                    calendarTableModel.setValueAt(loggedIn.getAppointments().get(i), j, k);
                }
            }
        }**/

        calendarTable.setDefaultRenderer(calendarTable.getColumnClass(0), new calendarTableRenderer());
    }

    class previous_Action implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            gregCal.add(GregorianCalendar.WEEK_OF_YEAR, -1);

            currentYear = gregCal.get(GregorianCalendar.YEAR);
            currentWeek = gregCal.get(GregorianCalendar.WEEK_OF_YEAR);
            currentMonth = gregCal.get(GregorianCalendar.MONTH);

            refreshCalendar(currentWeek, currentMonth, currentYear);
        }
    }
    class next_Action implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            gregCal.add(GregorianCalendar.WEEK_OF_YEAR, 1);

            currentYear = gregCal.get(GregorianCalendar.YEAR);
            currentWeek = gregCal.get(GregorianCalendar.WEEK_OF_YEAR);
            currentMonth = gregCal.get(GregorianCalendar.MONTH);

            refreshCalendar(currentWeek, currentMonth, currentYear);
        }
    }
    class year_Action implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (yearComboBox.getSelectedItem() != null) {
                String y = yearComboBox.getSelectedItem().toString();
                int var = Integer.parseInt(y);
                var -= currentYear;
                gregCal.add(GregorianCalendar.YEAR, var);

                currentYear = gregCal.get(GregorianCalendar.YEAR);
                currentWeek = gregCal.get(GregorianCalendar.WEEK_OF_YEAR);
                currentMonth = gregCal.get(GregorianCalendar.MONTH);

                if(var != 0){
                    refreshCalendar(currentWeek, currentMonth,currentYear);
                }
            }
        }
    }

    /** PROPERTYCHANGE **/
    public void propertyChange(PropertyChangeEvent evt) {
        if(evt.getPropertyName().equals(Calendar.APPOINTMENT_PROPERTY)){
            if(evt.getNewValue() instanceof Appointment){
                //Appointment added
            }
            else{
                //Appointment removed
            }
        }
    }
}