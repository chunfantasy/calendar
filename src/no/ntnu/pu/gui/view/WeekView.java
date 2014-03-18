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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    private ArrayList<Appointment> appointments;

    public WeekView(){
        super();

        /**Initialize subclass-specific components**/
        appointments = CalendarControl.getAppointments();
        weekLabel = new JLabel("Uke 1");
        dateSpanLabel = new JLabel("(01.01 - 07.01)");
        cttcm = calendarTableHeader.getColumnModel();

        /**Add listeners**/
        previousButton.addActionListener(new previous_Action());
        nextButton.addActionListener(new next_Action());
        yearComboBox.addActionListener(new year_Action());
        calendarTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && calendarTable.getSelectedColumn() != 0) {
                    if(calendarTable.getValueAt(calendarTable.getSelectedRow(), calendarTable.getSelectedColumn()) == null){
                        AppointmentView appointmentView = new AppointmentView();
                    }
                    else{
                        Appointment appointment = (Appointment)calendarTable.getValueAt(calendarTable.getSelectedRow(), calendarTable.getSelectedColumn());
                        AppointmentView appointmentView = new AppointmentView(appointment);
                    }
                    refreshCells();
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



        calendarTable.setDefaultRenderer(calendarTable.getColumnClass(0), new calendarTableRenderer());
    }

    private void refreshCells() {
        for(Appointment appointment : appointments){
            Date startTime = appointment.getStartTime();
            Date endTime = appointment.getEndTime();
            GregorianCalendar cal = new GregorianCalendar();
            cal.setTime(startTime);
            int weekStart = cal.get(GregorianCalendar.WEEK_OF_YEAR);
            int dayStart = cal.get(GregorianCalendar.DAY_OF_MONTH);
            int monthStart = cal.get(GregorianCalendar.MONTH);
            int hourStart = cal.get(GregorianCalendar.HOUR_OF_DAY);
            cal.setTime(endTime);
            int weekEnd = cal.get(GregorianCalendar.WEEK_OF_YEAR);
            int dayEnd = cal.get(GregorianCalendar.DAY_OF_MONTH);
            int monthEnd = cal.get(GregorianCalendar.MONTH);
            int hourEnd = cal.get(GregorianCalendar.HOUR_OF_DAY);
            String appointmentDate = "" + String.valueOf(dayStart) + "." + String.valueOf(monthStart+1);
            for(int j = 1; j<8; j++) {
                String headerDate = cttcm.getColumn(j).toString().substring(cttcm.getColumn(j).toString().length() - 3, cttcm.getColumn(j).toString().length());
                headerDate.trim();
                for(int i = hourStart; i < hourEnd; i++){
                    if(appointmentDate.equals(headerDate)){
                        calendarTableModel.setValueAt(appointment, i, j);
                    }
                    else{
                        calendarTableModel.setValueAt(null, i, j);
                    }
                }
            }
        }
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