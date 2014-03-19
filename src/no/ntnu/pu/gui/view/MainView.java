package no.ntnu.pu.gui.view;

import no.ntnu.pu.control.CalendarControl;
import no.ntnu.pu.model.Calendar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainView {
    private JPanel container, calendarView, notificationView, meetingsView;

    private JButton newAppointmentButton, monthButton, weekButton;
    private ButtonGroup buttonGroup;
    private MonthView monthView;
    private WeekView weekView;

    public MainView(){

        JFrame frame = new JFrame("Gigakalender");
        frame.setResizable(false);
        container = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        //gbc.insets = new Insets(10,10,10,10);
        gbc.anchor = GridBagConstraints.LINE_START;

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

        monthButton.addActionListener(new ButtonListener());
        weekButton.addActionListener(new ButtonListener());
        newAppointmentButton.addActionListener(new ButtonListener());

        calendarView.add(monthView);
        calendarView.add(weekView);
        showMonth();


        buttonGroup.add(weekButton);
        buttonGroup.add(monthButton);


        container.add(notificationView, GBC(gbc, 0, 1));
        container.add(meetingsView, GBC(gbc, 0, 2));
        container.add(newAppointmentButton, GBC(gbc, 1, 0));
        container.add(weekButton, GBC(gbc, 2, 0));
        container.add(monthButton, GBC(gbc, 3, 0));
        GridBagConstraints c = GBC(gbc, 1, 1);
        c.gridwidth=3;
        c.gridheight=2;
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
            }
        }
    }

    public static void main(String args[]){
        CalendarControl.setModel(new Calendar());
        new MainView();
    }

}
