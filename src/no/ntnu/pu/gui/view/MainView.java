package no.ntnu.pu.gui.view;

import no.ntnu.pu.control.AppointmentControl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainView {
    private GridBagConstraints GBC (GridBagConstraints c, int x, int y){
        c.gridx = x;
        c.gridy = y;
        return c;
    }

    private JPanel container, calendarView, appointmentView, meetingsView;
    private JButton newAppointmentButton, monthButton, weekButton;
    private ButtonGroup buttonGroup;
    private MonthView monthView;
    private WeekView weekView;

    public MainView(){

        JFrame frame = new JFrame();
        container = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        //gbc.insets = new Insets(10,10,10,10);
        gbc.anchor = GridBagConstraints.LINE_START;

        calendarView = new JPanel();
        monthView = new MonthView();
        weekView = new WeekView();

        appointmentView = new AppointmentView();
        meetingsView = new MeetingsView();

        buttonGroup = new ButtonGroup();

        monthButton = new JButton("Month");
        weekButton = new JButton("Week");
        newAppointmentButton = new JButton("New Appointment");

        monthButton.addActionListener(new ButtonListener());
        weekButton.addActionListener(new ButtonListener());
        newAppointmentButton.addActionListener(new ButtonListener());

        calendarView.add(monthView);
        calendarView.add(weekView);


        buttonGroup.add(weekButton);
        buttonGroup.add(monthButton);


        container.add(appointmentView,GBC(gbc,0,1));
        container.add(meetingsView,GBC(gbc,0,2));
        container.add(calendarView,GBC(gbc,1,1));
        container.add(newAppointmentButton,GBC(gbc,1,0));
        container.add(weekButton,GBC(gbc,3,0));
        container.add(monthButton,GBC(gbc,4,0));

        frame.getContentPane().add(container);

        frame.pack();
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

    private class ButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Object s = e.getSource();
            if(s.equals(newAppointmentButton)){
                AppointmentControl.createAppointment();
            }else if(s.equals(monthButton)){
                showMonth();
            }else if(s.equals(weekButton)){
                showWeek();
            }
        }

    }
}
