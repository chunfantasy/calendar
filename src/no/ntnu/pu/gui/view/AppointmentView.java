package no.ntnu.pu.gui.view;


import no.ntnu.pu.gui.panel.AddParticipant;
import no.ntnu.pu.gui.panel.AddRoom;
import no.ntnu.pu.model.Appointment;
import no.ntnu.pu.model.Participant;
import no.ntnu.pu.model.Person;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class AppointmentView extends JPanel implements ListSelectionListener, ActionListener, FocusListener{

    private JButton addParticipantButton, removeButton, saveButton, deleteButton, addRoomButton, cancelButton;
    private JToggleButton mainButton, roomButton, participantButton, externalButton;
    private JTextField appointmentField, placeField, descField;
    private JLabel appointmentLabel, placeLabel, participantsLabel, startDateLabel, endDateLabel, startTimeLabel, endTimeLabel, descLabel;
    private JList<Participant> participantList;
    private JFrame frame;
    private ButtonGroup grp;
    private DefaultListModel<Participant> participantListModel;
    private final String DATETEXT = "dd.mm.yy", TIMETEXT = "hh:mm";
    private static final String EDITINGTITLE = "Endre avtale", NEWTITLE = "Legg til ny avtale";
    private Appointment model;
    private JPanel totalGUI, container, addRoomView, addParticipantView, appointmentView, externalView;
    private ArrayList<Integer> thrirtyOne, thrity, twentyNine, twentyEight, hours, years;
    private ArrayList<String> minutes, months;

//
//    private static void createAndShowGUI(boolean editing) {
//        JFrame frame = new JFrame(NEWTITLE);
//        if (editing){
//            frame.setTitle(EDITINGTITLE);
//        }
//        JFrame.setDefaultLookAndFeelDecorated(true);
//        AppointmentView view = new AppointmentView();
//        frame.setContentPane(view.createContentPane(editing));
//
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setVisible(true);
//        frame.pack();
//    }

    public AppointmentView(Appointment appointment){


        makeAndShowGUI();

    }

    public AppointmentView(){
        frame = new JFrame(NEWTITLE);

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

        mainButton = new JButton("Vis avtale");
        mainButton.addActionListener(this);
        mainButton.setFocusable(false);

        roomButton = new JButton("Book rom");
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
        model = new Appointment();

        totalGUI = new JPanel();

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
        scroll.setPreferredSize(new Dimension(180, 150));
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
        appointmentLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        startField = new JTextField(TIMETEXT);
        startField.setToolTipText(TIMETEXT);
        startField.setColumns(5);
        startField.addFocusListener(this);
        timeLabel = new JLabel("Tid ");
        timeLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        endField = new JTextField(TIMETEXT);
        endField.setToolTipText(TIMETEXT);
        endField.setColumns(5);
        endField.addFocusListener(this);

        placeField = new JTextField();
        placeField.setColumns(15);
        placeField.addFocusListener(this);
        placeLabel = new JLabel("Sted ");
        placeLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        betweenTimeLabel = new JLabel("-");
        betweenTimeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Set height of buttons to be equal to height of JTextFields
        Dimension dim = new Dimension(addParticipantButton.getPreferredSize().width, endField.getPreferredSize().height);
        addParticipantButton.setPreferredSize(dim);
        addRoomButton.setPreferredSize(dim);
        removeButton.setPreferredSize(dim);
        saveButton.setPreferredSize(dim);
        deleteButton.setPreferredSize(dim);
        cancelButton.setPreferredSize(dim);

//        AddRoom room = new AddRoom(model);
//        frame.add(room);
//        room.setVisible(false);

        setupGBC(1, 1, 0.5, 0, 1, gbc, appointmentLabel, true);
        setupGBC(3, 1, 0.5, 1, 1, gbc, appointmentField, true);
        setupGBC(1, 1, 0.5, 0, 3, gbc, timeLabel, true);
        setupGBC(1, 1, 0.5, 1, 3, gbc, startField, true);
        setupGBC(1, 1, 0.5, 3, 3, gbc, endField, true);
        setupGBC(1, 1, 0.5, 0, 4, gbc, placeLabel, true);
        setupGBC(3, 1, 0.5, 1, 4, gbc, placeField, true);
        setupGBC(1, 1, 0.5, 0, 5, gbc, participantsLabel, true);
        setupGBC(3, 4, 0.5, 1, 5, gbc, scroll, true);
        setupGBC(1, 1, 0.5, 0, 8, gbc, removeButton, true);
        setupGBC(1, 1, 0.5, 1, 10, gbc, saveButton, true);
        setupGBC(1, 1, 0.5, 2, 10, gbc, deleteButton, true);
        setupGBC(1, 1, 0.5, 3, 10, gbc, cancelButton, true);
        setupGBC(1, 1, 0.5, 2, 3, gbc, betweenTimeLabel, true);

        totalGUI.setOpaque(true);
        return totalGUI;
    }


    public void setupGBC(int gridwidth, int gridheight, double weightx, int x, int y, GridBagConstraints c, Component comp, boolean fill){
        c.gridheight = gridheight;
        c.gridwidth = gridwidth;
        c.weightx = weightx;
        c.gridx = x;
        c.gridy = y;
        if (fill) {
            c.fill = GridBagConstraints.HORIZONTAL;
        } else {
            c.fill = GridBagConstraints.NONE;
        }
        totalGUI.add(comp, c);
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
            ArrayList<Participant> participants = new ArrayList<Participant>();
            for (int i = 0; i<participantListModel.getSize(); i++){
                participants.add(participantListModel.getElementAt(i));
            }
            //model.setParticipants(participants);
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

    }

    public void valueChanged(ListSelectionEvent e) {

    }


    public void focusGained(FocusEvent e) {
        if (e.getSource() == startField) {
            startField.setText("");
        }
        if (e.getSource() == endField) {
            endField.setText("");
        }
        if (e.getSource() == placeField) {
            placeField.setText("");
        }
        if (e.getSource() == saveButton){
            model.setAddress(null);
        }

    }

    public void focusLost(FocusEvent e) {
        // Nothing
        if (e.getSource() == startField) {
            if (startField.getText().length() == 0){
                startField.setText(TIMETEXT);
            }
        }
        if (e.getSource() == endField) {
            if (endField.getText().length() == 0){
                endField.setText(TIMETEXT);
            }
        }
    }

    public void setEnabledButton(){
        addRoomButton.setEnabled(true);
        addParticipantButton.setEnabled(true);
    }

//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(new Runnable(){
//            public void run() {
//                createAndShowGUI(true);
//            }
//        });
//    }

    public static void main(String[] args){
        new AppointmentView();
    }

    public Appointment getModel(){
        return this.model;
    }



}


