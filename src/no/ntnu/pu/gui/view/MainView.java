package no.ntnu.pu.gui.view;

import no.ntnu.pu.control.CalendarControl;
import no.ntnu.pu.control.PersonControl;
import no.ntnu.pu.model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class MainView {
    private JPanel container, calendarView, notificationView, meetingsView;

    private JButton newAppointmentButton, monthButton, weekButton, refreshButton;
    private ButtonGroup buttonGroup;
    private MonthView monthView;
    private WeekView weekView;
    private JComboBox<Person> dropdown;

    public MainView(){

        JFrame frame = new JFrame("Gigakalender");
        frame.setDefaultLookAndFeelDecorated(true);
        frame.setResizable(false);
        container = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        calendarView = new JPanel();
        monthView = new MonthView();
        weekView = new WeekView();

        notificationView = new NotificationView();
        meetingsView = new MeetingsView();

        buttonGroup = new ButtonGroup();

        monthButton = new JButton("Måned");
        monthButton.setEnabled(false);
        weekButton = new JButton("Uke");
        newAppointmentButton = new JButton("Ny avtale");
        refreshButton = new JButton("Oppdater");

        dropdown = new JComboBox<Person>();

        for(Person p : PersonControl.getAll()){
            dropdown.addItem(p);
        }

        monthButton.addActionListener(new ButtonListener());
        weekButton.addActionListener(new ButtonListener());
        newAppointmentButton.addActionListener(new ButtonListener());
        refreshButton.addActionListener(new ButtonListener());
        dropdown.addItemListener(new DropDownListener());

        calendarView.add(monthView);
        calendarView.add(weekView);
        showMonth();


        buttonGroup.add(weekButton);
        buttonGroup.add(monthButton);

        container.add(dropdown, GBC(gbc,0,0));
        container.add(notificationView, GBC(gbc, 0, 1));
        container.add(meetingsView, GBC(gbc, 0, 2));
        container.add(newAppointmentButton, GBC(gbc, 1, 0));
        GridBagConstraints c = GBC(gbc, 2, 0);
        c.anchor = GridBagConstraints.EAST;
        container.add(weekButton, c);
        c.gridx = 3;
        container.add(monthButton, c);
        c.gridx = 4;
        container.add(refreshButton, c);
        c = GBC(gbc, 1, 1);
        c.anchor = GridBagConstraints.CENTER;
        c.gridwidth = 4;
        c.gridheight = 2;
        container.add(calendarView, c);

        frame.getContentPane().add(container);
        frame.setSize(1024, 768);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void showMonth(){
        weekView.setVisible(false);
        monthView.setVisible(true);
    }

    private void showWeek(){
        monthView.setVisible(false);
        weekView.setVisible(true);

    }

    private GridBagConstraints GBC (GridBagConstraints c, int x, int y){
        c.anchor = GridBagConstraints.WEST;
        c.gridx = x;
        c.gridy = y;
        return c;
    }

    private class ButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Object s = e.getSource();
            if(s.equals(newAppointmentButton)){
                new AppointmentView();
            }else if(s.equals(monthButton)){
                showMonth();
                monthButton.setEnabled(false);
                weekButton.setEnabled(true);
            }else if(s.equals(weekButton)){
                showWeek();
                weekButton.setEnabled(false);
                monthButton.setEnabled(true);
            }else if(s.equals(refreshButton)){
                CalendarControl.refresh();
            }
        }
    }

    private class DropDownListener implements ItemListener{

        @Override
        public void itemStateChanged(ItemEvent e) {
            if(e.getStateChange() == ItemEvent.SELECTED){
                System.out.print(((Person) e.getItem()).getId());
                PersonControl.setModel((Person) e.getItem());
                CalendarControl.refresh();
            }
        }
    }

}
