package no.ntnu.pu.gui.view;

import no.ntnu.pu.control.AppointmentControl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.GregorianCalendar;

public class MonthView extends CalenderView{

    public MonthView() {
        super();

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
        panelAdd(1, 0.5, 1.0, 1, 0, monthLabel, GridBagConstraints.CENTER);
        panelAdd(1, 0.5, 1.0, 1, 3, yearLabel, GridBagConstraints.EAST);
        panelAdd(1, 0.5, 1.0, 2, 3, yearComboBox, GridBagConstraints.WEST);
        panelAdd(1, 0.5, 1.0, 0, 0, previousButton, GridBagConstraints.CENTER);
        panelAdd(1, 0.5, 1.0, 2, 0, nextButton, GridBagConstraints.CENTER);
        panelAdd(3, 0.0, 0.0, 0, 2, calendarTable, GridBagConstraints.CENTER);
        panelAdd(3, 0.0, 0.0, 0, 1, calendarTableHeader, GridBagConstraints.CENTER);

        /**Set Border**/
        setBorder(BorderFactory.createTitledBorder("Månedsvisning"));

        String[] headers = {"Man", "Tir", "Ons", "Tor", "Fre", "Lør", "Søn"};
        for (int i = 0; i<7; i++){
            calendarTableModel.addColumn(headers[i]);
        }

        calendarTable.setRowHeight(38);
        calendarTableModel.setRowCount(6);
        calendarTableModel.setColumnCount(7);

        calendarTable.setAutoCreateColumnsFromModel(false);

        refreshCalendar(realMonth, realYear);
    }

    private void refreshCalendar(int month, int year){
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