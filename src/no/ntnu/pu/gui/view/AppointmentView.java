package no.ntnu.pu.gui.view;


import no.ntnu.pu.gui.panel.AddExternalParticipant;
import no.ntnu.pu.gui.panel.AddParticipant;
import no.ntnu.pu.gui.panel.AddRoom;
import no.ntnu.pu.model.Appointment;
import no.ntnu.pu.model.Participant;
import no.ntnu.pu.model.Person;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.Arrays;

public class AppointmentView extends JPanel implements ListSelectionListener, ActionListener, FocusListener{

    private JButton addParticipantButton, removeButton, saveButton, deleteButton, addRoomButton, cancelButton;
    private JToggleButton mainButton, roomButton, participantButton, externalButton;
    private JTextField appointmentField, placeField, descField;
    private JLabel appointmentLabel, placeLabel, participantsLabel, startDateLabel, endDateLabel, startTimeLabel, endTimeLabel, descLabel;
    private JList<Participant> participantList;
    private JComboBox startDayCB, startMonthCB, startYearCB, startHourCB, startMinCB, endDayCB, endMonthCB, endYearCB, endHourCB, endMinCB;
    private JFrame frame;
    private ButtonGroup grp;
    private DefaultListModel<Participant> participantListModel;
    private Appointment model;
    private JPanel totalGUI, container, addRoomView, addParticipantView, appointmentView, externalView;
    private ArrayList<Integer> thrirtyOne, thrity, twentyNine, twentyEight, hours, years;
    private ArrayList<String> minutes, months;

    public AppointmentView(Appointment appointment){

        boolean isEdited = true;
        frame = new JFrame("Endre avtale");

        makeAndShowGUI();

    }

    public AppointmentView(){
        boolean isEdited = false;
        frame = new JFrame("Legg til ny avtale");

        makeAndShowGUI();
    }

    public void makeAndShowGUI(){
        // GridBagLayout
        GridBagConstraints c;
        setLayout(new GridBagLayout());
        c = new GridBagConstraints();

        // Different Views
        addRoomView = new AddRoom().createContentPane();
        addParticipantView = new AddParticipant().createContentPane();
        externalView = new AddExternalParticipant().createContentPane();
        appointmentView = createContentPane(false);
        appointmentView.setPreferredSize(new Dimension(400, 300));

        mainButton = new JToggleButton("Vis avtale");
        mainButton.addActionListener(this);
        mainButton.setFocusable(false);
        mainButton.setSelected(true);

        roomButton = new JToggleButton("Book rom");
        roomButton.addActionListener(this);
        roomButton.setFocusable(false);

        participantButton = new JToggleButton("Deltakere");
        participantButton.addActionListener(this);
        participantButton.setFocusable(false);

        externalButton = new JToggleButton("Legg til eksterne deltakere");
        externalButton.addActionListener(this);
        externalButton.setFocusable(false);

        grp = new ButtonGroup();
        grp.add(mainButton);
        grp.add(roomButton);
        grp.add(participantButton);
        grp.add(externalButton);

        container = new JPanel(new GridBagLayout());

        // Setup GridBagLayout
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.weightx = 0.5;
        c.fill = GridBagConstraints.HORIZONTAL;
        container.add(mainButton, c);

        c.gridx = 1;
        container.add(participantButton, c);

        c.gridx = 2;
        container.add(roomButton, c);

        c.gridx = 3;
        container.add(externalButton, c);

        c.gridy = 1;
        c.gridx = 0;
        c.gridwidth = 4;
        container.add(appointmentView, c);
        container.add(addRoomView, c);
        container.add(addParticipantView, c);
        container.add(externalView, c);
        showAppointment();
        frame.getContentPane().add(container);
        frame.setDefaultLookAndFeelDecorated(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
        frame.pack();
        frame.setLocationRelativeTo(null);
    }

    public JPanel createContentPane(boolean editing){
        // Appointment
        model = new Appointment(new Person("Hei"));

        totalGUI = new JPanel();

        // setDropDownLists
        setDropDownLists();

        // GridBag
        GridBagConstraints gbc;
        totalGUI.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();


        // participantListModel
        participantListModel = new DefaultListModel<Participant>();

        // Icons
        ImageIcon calIcon = new ImageIcon(getClass().getResource("calendar.png"));

        // Look&Feel
        try{ UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());}
        catch (ClassNotFoundException e) {}
        catch (InstantiationException e) {}
        catch (IllegalAccessException e) {}
        catch (UnsupportedLookAndFeelException e) {}

        // participantList Start
        participantList = new JList<Participant>(participantListModel);
        participantList.setModel(participantListModel);
        participantList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        participantList.addListSelectionListener(this);
        JScrollPane scroll = new JScrollPane(participantList);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setPreferredSize(new Dimension(100, 150));
        participantsLabel = new JLabel("Deltakere ");
        participantsLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        // participantList End

        addParticipantButton = new JButton("Legg til");
        addParticipantButton.addActionListener(this);

        removeButton = new JButton("Fjern");
        removeButton.addActionListener(this);

        saveButton = new JButton("Lagre");
        saveButton.addActionListener(this);

        deleteButton = new JButton("Slett");
        deleteButton.addActionListener(this);
        deleteButton.setEnabled(editing);

        addRoomButton = new JButton("Rom");
        addRoomButton.addActionListener(this);

        cancelButton = new JButton("Avslutt");
        cancelButton.addActionListener(this);

        appointmentField = new JTextField();
        appointmentField.setColumns(20);
        appointmentLabel = new JLabel("Hendelse ");

        placeField = new JTextField();
        placeField.setColumns(15);
        placeField.addFocusListener(this);
        placeLabel = new JLabel("Sted ");

        descField = new JTextField();
        descField.addActionListener(this);

        descLabel = new JLabel("Beskrivelse ");

        startDateLabel = new JLabel("Startdato ");

        startDayCB = new JComboBox(thrirtyOne.toArray());
        startDayCB.addActionListener(this);
        startDayCB.setPreferredSize(new Dimension(37, 20));

        startMonthCB = new JComboBox(months.toArray());
        startMonthCB.addActionListener(this);

        startYearCB = new JComboBox(years.toArray());
        startYearCB.addActionListener(this);

        startTimeLabel = new JLabel("Starttid ");

        startHourCB = new JComboBox(hours.toArray());
        startHourCB.addActionListener(this);

        startMinCB = new JComboBox(minutes.toArray());
        startMinCB.addActionListener(this);

        endDateLabel = new JLabel("Sluttdato ");

        endDayCB = new JComboBox(thrirtyOne.toArray());
        endDayCB.addActionListener(this);
        endDayCB.setPreferredSize(new Dimension(37, 20));

        endMonthCB = new JComboBox(months.toArray());
        endMonthCB.addActionListener(this);

        endYearCB = new JComboBox(years.toArray());
        endYearCB.addActionListener(this);

        endTimeLabel = new JLabel("Slutttid ");

        endHourCB = new JComboBox(hours.toArray());
        endHourCB.addActionListener(this);

        endMinCB = new JComboBox(minutes.toArray());
        endMinCB.addActionListener(this);


        // Appointment
        setupGBC(1, 1, 0.5, 0, 1, gbc, appointmentLabel, false, GridBagConstraints.EAST);
        setupGBC(3, 1, 0.5, 1, 1, gbc, appointmentField, true, GridBagConstraints.CENTER);
        // startTime
        setupGBC(1, 1, 0.5, 0, 2, gbc, startDateLabel, false, GridBagConstraints.EAST);
        setupGBC(1, 1, 0.5, 1, 2, gbc, startDayCB, false, GridBagConstraints.EAST);
        setupGBC(1, 1, 0.5, 2, 2, gbc, startMonthCB, false, GridBagConstraints.WEST);
        setupGBC(1, 1, 0.5, 3, 2, gbc, startYearCB, false, GridBagConstraints.WEST);
        setupGBC(1, 1, 0.5, 6, 2, gbc, startTimeLabel, false, GridBagConstraints.EAST);
        setupGBC(1, 1, 0.5, 7, 2, gbc, startHourCB, false, GridBagConstraints.WEST);
        setupGBC(1, 1, 0.5, 8, 2, gbc, startMinCB, false, GridBagConstraints.WEST);
        // endTime
        setupGBC(1, 1, 0.5, 0, 3, gbc, endDateLabel, false, GridBagConstraints.EAST);
        setupGBC(1, 1, 0.5, 1, 3, gbc, endDayCB, false, GridBagConstraints.EAST);
        setupGBC(1, 1, 0.5, 2, 3, gbc, endMonthCB, false, GridBagConstraints.WEST);
        setupGBC(1, 1, 0.5, 3, 3, gbc, endYearCB, false, GridBagConstraints.WEST);
        setupGBC(1, 1, 0.5, 6, 3, gbc, endTimeLabel, false, GridBagConstraints.EAST);
        setupGBC(1, 1, 0.5, 7, 3, gbc, endHourCB, false, GridBagConstraints.WEST);
        setupGBC(1, 1, 0.5, 8, 3, gbc, endMinCB, false, GridBagConstraints.WEST);
        // place
        setupGBC(1, 1, 0.5, 0, 4, gbc, placeLabel, false, GridBagConstraints.EAST);
        setupGBC(3, 1, 0.5, 1, 4, gbc, placeField, true, GridBagConstraints.WEST);
        // Desc
        setupGBC(1, 1, 0.5, 0, 5, gbc, descLabel, false, GridBagConstraints.EAST);
        setupGBC(3, 1, 0.5, 1, 5, gbc, descField, true, GridBagConstraints.CENTER);
        // participantList
        setupGBC(1, 1, 0.5, 0, 6, gbc, participantsLabel, true, GridBagConstraints.EAST);
        setupGBC(3, 4, 0.5, 1, 6, gbc, scroll, true, GridBagConstraints.CENTER);
        // remove/save/cancel/delete
        setupGBC(1, 1, 0.5, 0, 8, gbc, removeButton, true, GridBagConstraints.CENTER);
        setupGBC(1, 1, 0.5, 1, 11, gbc, saveButton, true, GridBagConstraints.CENTER);
        setupGBC(1, 1, 0.5, 2, 11, gbc, deleteButton, true, GridBagConstraints.CENTER);
        setupGBC(1, 1, 0.5, 3, 11, gbc, cancelButton, true, GridBagConstraints.CENTER);

        totalGUI.setOpaque(true);
        return totalGUI;
    }


    public void setupGBC(int gridwidth, int gridheight, double weightx, int x, int y, GridBagConstraints c, Component comp, boolean fill, int anchor){
        c.gridheight = gridheight;
        c.gridwidth = gridwidth;
        c.weightx = weightx;
        c.gridx = x;
        c.gridy = y;
        c.anchor = anchor;
        if (fill) {
            c.fill = GridBagConstraints.HORIZONTAL;
        } else {
            c.fill = GridBagConstraints.NONE;
        }
        totalGUI.add(comp, c);
    }

    private ArrayList<Integer> createDropDownList(int start, int max){
        ArrayList<Integer> list = new ArrayList<Integer>();
        for(int i = start; i<=max; i++){
            list.add(i);
        }
        return list;
    }

    private void setDropDownLists(){
        thrirtyOne = createDropDownList(1, 31);
        thrity = createDropDownList(1, 30);
        twentyNine = createDropDownList(1, 29);
        twentyEight = createDropDownList(1, 28);
        hours = createDropDownList(1, 24);
        years = createDropDownList(2014, 2050);
        months = new ArrayList<String>(Arrays.asList("Januar", "Februar", "Mars", "April", "Mai", "Juni", "Juli", "August", "September", "Oktober", "November", "Desember"));
        minutes = new ArrayList<String>(Arrays.asList("00", "15", "30", "45"));
    }


    public void showAppointment(){
        addRoomView.setVisible(false);
        addParticipantView.setVisible(false);
        externalView.setVisible(false);
        appointmentView.setVisible(true);
        container.setBorder(new TitledBorder("Legg til avtale"));
    }

    public void showRoom(){
        addParticipantView.setVisible(false);
        appointmentView.setVisible(false);
        externalView.setVisible(false);
        addRoomView.setVisible(true);
        container.setBorder(new TitledBorder("Velg rom"));
    }

    public void showParticipant(){
        addRoomView.setVisible(false);
        appointmentView.setVisible(false);
        externalView.setVisible(false);
        addParticipantView.setVisible(true);
        container.setBorder(new TitledBorder("Legg til deltaker"));
    }

    public void showExternal(){
        addRoomView.setVisible(false);
        appointmentView.setVisible(false);
        addParticipantView.setVisible(false);
        externalView.setVisible(true);
        container.setBorder(new TitledBorder("Inviter eksterne deltakere"));
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == roomButton){
//            AddRoom.createAndShowGUI(model);
            showRoom();
        }
        if (e.getSource() == participantButton){
            showParticipant();
        }
        if (e.getSource() == mainButton){
            showAppointment();
        }
        if (e.getSource() == externalButton){
            showExternal();
        }
        if (e.getSource() == saveButton){
            model.setDescription("");
            model.setAddress(placeField.getText());
            model.setTitle(appointmentField.getText());
//            model.setStartTime(startField.getText());
//            model.setEndTime(endField.getText());
            model.setMeetingRoom(null);
            // setParticipants
            for (int i = 0; i<participantListModel.getSize(); i++){
                model.addParticipant(participantListModel.getElementAt(i));
            }
        }
        if (e.getSource()== cancelButton){
            frame.dispose();
        }
        if (e.getSource() == endDayCB){
            int start = (Integer)startDayCB.getSelectedItem();
            int end = (Integer)endDayCB.getSelectedItem();
            if (start > end){
                startDayCB.setSelectedIndex(endDayCB.getSelectedIndex());
            }
        }
        if (e.getSource() == startDayCB){
            int start = (Integer)startDayCB.getSelectedItem();
            int end = (Integer)endDayCB.getSelectedItem();
            if (start > end){
                endDayCB.setSelectedIndex(startDayCB.getSelectedIndex());
            }
        }
        if (e.getSource() == endMonthCB){
            int start = (Integer)startMonthCB.getSelectedIndex();
            int end = (Integer)endMonthCB.getSelectedIndex();
            if (start > end){
                startMonthCB.setSelectedIndex(endMonthCB.getSelectedIndex());
            }
        }
        if (e.getSource() == startMonthCB){
            int start = (Integer)startMonthCB.getSelectedIndex();
            int end = (Integer)endMonthCB.getSelectedIndex();
            if (start > end){
                endMonthCB.setSelectedIndex(startMonthCB.getSelectedIndex());
            }
        }
        if (e.getSource() == endYearCB){
            int start = (Integer)startYearCB.getSelectedItem();
            int end = (Integer)endYearCB.getSelectedItem();
            if (start > end){
                startYearCB.setSelectedIndex(endYearCB.getSelectedIndex());
            }
        }
        if (e.getSource() == startYearCB){
            int start = (Integer)startYearCB.getSelectedItem();
            int end = (Integer)endYearCB.getSelectedItem();
            if (start > end){
                endYearCB.setSelectedIndex(startYearCB.getSelectedIndex());
            }
        }
        if (e.getSource() == endHourCB){
            int start = (Integer)startHourCB.getSelectedItem();
            int end = (Integer)endHourCB.getSelectedItem();
            if (start > end){
                startHourCB.setSelectedIndex(endHourCB.getSelectedIndex());
            }
        }
        if (e.getSource() == startMinCB){
            int start = (Integer)startMinCB.getSelectedIndex();
            int end = (Integer)endMinCB.getSelectedIndex();
            if (start > end){
                endMinCB.setSelectedIndex(startMinCB.getSelectedIndex());
            }
        }
        if (e.getSource() == endMinCB){
            int start = (Integer)startMinCB.getSelectedIndex();
            int end = (Integer)endMinCB.getSelectedIndex();
            if (start > end){
                startMinCB.setSelectedIndex(endMinCB.getSelectedIndex());
            }
        }


        // aids
    }

    public void valueChanged(ListSelectionEvent e) {

    }


    public void focusGained(FocusEvent e) {
        if (e.getSource() == placeField) {
            placeField.setText("");
        }
        if (e.getSource() == saveButton){
            model.setAddress(null);
        }

    }

    public void focusLost(FocusEvent e) {

    }

    public void setEnabledButton(){
        addRoomButton.setEnabled(true);
        addParticipantButton.setEnabled(true);
    }


    public static void main(String[] args){
        new AppointmentView();
    }
}

