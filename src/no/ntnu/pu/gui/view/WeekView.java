package no.ntnu.pu.gui.view;

import no.ntnu.pu.control.CalendarControl;
import no.ntnu.pu.model.Appointment;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;

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
    private List<Appointment> appointments;

    public WeekView(){
        super();

        /**Initialize subclass-specific components**/
        if(CalendarControl.getModel().getAppointments() != null && CalendarControl.getAppointments().size() > 0){
            appointments = CalendarControl.getModel().getAppointments();
        }
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
        setBorder(new TitledBorder(LineBorder.createGrayLineBorder(),"Ukevisning"));

        for (int i = 0; i<8; i++){
            calendarTableModel.addColumn(headers[i]);
        }

        calendarTable.setRowHeight(25);
        calendarTableModel.setRowCount(24);
        calendarTableModel.setColumnCount(8);


        for(int i = 0; i<24; i++){
            calendarTableModel.setValueAt((i < 10)? "0" + String.valueOf(i) + ":00" : String.valueOf(i) + ":00", i, 0);
        }

        calendarTable.setAutoCreateColumnsFromModel(false);

        refreshCalendar(realWeek, realMonth,realYear);
    }

    public void refreshCalendar( int week,  int month, int year) {

        while(gregCal.get(GregorianCalendar.DAY_OF_WEEK) != GregorianCalendar.MONDAY){
            gregCal.add(GregorianCalendar.DATE, -1);
        }

        currentDay = gregCal.get(GregorianCalendar.DAY_OF_MONTH);
        numberOfDays = gregCal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);

        for(int i = 0; i < 7; i++){
            midweekChange = (currentDay + i > numberOfDays ? true : false);
            if(midweekChange) break;
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
        refreshCells();
    }

    private void refreshCells() {
        for(int j = 1; j<8; j++) {
            for(int i = 0; i < 24; i++){
                calendarTableModel.setValueAt(null, i, j);
            }
        }
        appointments = CalendarControl.getModel().getAppointments();
        if(appointments != null && appointments.size()>0){
            for(Appointment appointment : appointments){
                Date startTime = appointment.getStartTime();
                Date endTime = appointment.getEndTime();
                GregorianCalendar cal = new GregorianCalendar();
                cal.setTime(startTime);
                int dayStart = cal.get(GregorianCalendar.DAY_OF_MONTH);
                int monthStart = cal.get(GregorianCalendar.MONTH);
                int hourStart = cal.get(GregorianCalendar.HOUR_OF_DAY);
                int yearStart = cal.get(GregorianCalendar.YEAR);
                cal.setTime(endTime);
                int dayEnd = cal.get(GregorianCalendar.DAY_OF_MONTH);
                int monthEnd = cal.get(GregorianCalendar.MONTH);
                int hourEnd = cal.get(GregorianCalendar.HOUR_OF_DAY);
                int yearEnd = cal.get(GregorianCalendar.YEAR);
                while(hourEnd <= hourStart){
                    hourEnd += 1;
                }

                for(int j = 1; j<8; j++) {
                    int appointmentDateStart = Integer.valueOf(""+String.valueOf(yearStart)+String.valueOf(monthStart+1)+String.valueOf(dayStart));
                    int appointmentDateEnd = Integer.valueOf(""+String.valueOf(yearEnd)+String.valueOf(monthEnd+1)+String.valueOf(dayEnd));
                    String[] header = cttcm.getColumn(j).getHeaderValue().toString().split("      ");
                    String headerDate = header[1];
                    String[] headerDateList = headerDate.split("\\.");
                    String currentYearStr = String.valueOf(currentYear);
                    int headerDateInt = Integer.valueOf(currentYearStr + headerDateList[1] + headerDateList[0]);
                    for(int i = hourStart; i < hourEnd; i++) {
                        if(appointmentDateEnd >= headerDateInt && appointmentDateStart <= headerDateInt && yearStart <= currentYear && yearEnd >= currentYear){
                            calendarTableModel.setValueAt(appointment, i, j);
                        }
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
}