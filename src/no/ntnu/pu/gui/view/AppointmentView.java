package no.ntnu.pu.gui.view;

import no.ntnu.pu.gui.panel.AddParticipant;
import no.ntnu.pu.gui.panel.AddRoom;
import no.ntnu.pu.model.Appointment;
import no.ntnu.pu.model.Participant;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class AppointmentView extends JPanel implements ListSelectionListener, ActionListener, FocusListener{

    private JButton addParticipantButton, removeButton, saveButton, deleteButton, addRoomButton, dateButton, cancelButton;
    private JTextField appointmentField, dateField, startField, endField, placeField;
    private JLabel appointmentLabel, dateLabel, timeLabel, placeLabel, participantsLabel, betweenTimeLabel;
    private JList<Participant> participantList;
    private DefaultListModel<Participant> participantListModel;
    private final String DATETEXT = "dd.mm.yy", TIMETEXT = "hh:mm";
    private static final String EDITINGTITLE = "Endre avtale", NEWTITLE = "Legg til ny avtale";
    private Appointment model;
    public boolean roomChosen = false;
    private JPanel totalGUI;

    public JPanel createContentPane(boolean editing){
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

        dateButton = new JButton();
        dateButton.setIcon(calIcon);
        dateButton.addActionListener(this);
        dateButton.setPreferredSize(new Dimension(26, 19));
        dateButton.setBorderPainted(false);
        dateButton.setContentAreaFilled(false);
        dateButton.setFocusPainted(false);
//        dateButton.setHorizontalAlignment(SwingConstants.RIGHT);
        dateButton.setOpaque(false);

        cancelButton = new JButton("Avslutt");
        cancelButton.addActionListener(this);

        appointmentField = new JTextField();
        appointmentField.setColumns(20);
        appointmentLabel = new JLabel("Hendelse ");

        dateField = new JTextField(DATETEXT);
        dateField.setColumns(10);
        dateLabel = new JLabel("Dato ");
        dateField.setEditable(false);

        startField = new JTextField(TIMETEXT);
        startField.setToolTipText(TIMETEXT);
        startField.setColumns(5);
        startField.addFocusListener(this);
        timeLabel = new JLabel("Tid ");

        endField = new JTextField(TIMETEXT);
        endField.setToolTipText(TIMETEXT);
        endField.setColumns(5);
        endField.addFocusListener(this);

        placeField = new JTextField();
        placeField.setColumns(15);
        placeField.addFocusListener(this);
        placeLabel = new JLabel("Sted ");
        // Legg til FocusListener SLUTT 

        betweenTimeLabel = new JLabel("-");
        betweenTimeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        Dimension dim = new Dimension(addParticipantButton.getPreferredSize().width, dateField.getPreferredSize().height);

        addParticipantButton.setPreferredSize(dim);
        addRoomButton.setPreferredSize(dim);
        removeButton.setPreferredSize(dim);
        saveButton.setPreferredSize(dim);
        deleteButton.setPreferredSize(dim);
        cancelButton.setPreferredSize(dim);



        setupGBC(1, 1, 0.5, 0, 0, gbc, appointmentLabel, true);
        setupGBC(3, 1, 0.5, 1, 0, gbc, appointmentField, true);
        setupGBC(1, 1, 0.5, 0, 1, gbc, dateLabel, true);
        setupGBC(1, 1, 0.5, 1, 1, gbc, dateField, true);
        setupGBC(1, 1, 0.5, 0, 2, gbc, timeLabel, true);
        setupGBC(1, 1, 0.5, 1, 2, gbc, startField, true);
        setupGBC(1, 1, 0.5, 3, 2, gbc, endField, true);
        setupGBC(1, 1, 0.5, 0, 3, gbc, placeLabel, true);
        setupGBC(3, 1, 0.5, 1, 3, gbc, placeField, true);
        setupGBC(1, 1, 0.5, 4, 3, gbc, addRoomButton, true);
        setupGBC(1, 1, 0.5, 0, 4, gbc, participantsLabel, true);
        setupGBC(3, 4, 0.5, 1, 4, gbc, scroll, true);
        setupGBC(1, 1, 0.5, 4, 4, gbc, addParticipantButton, true);
        setupGBC(1, 1, 0.5, 4, 5, gbc, removeButton, true);
        setupGBC(1, 1, 0.5, 1, 9, gbc, saveButton, true);
        setupGBC(1, 1, 0.5, 2, 9, gbc, deleteButton, true);
        setupGBC(1, 1, 0.5, 3, 9, gbc, cancelButton, true);
        setupGBC(1, 1, 0.5, 2, 2, gbc, betweenTimeLabel, true);
        setupGBC(1, 1, 0.5, 3, 1, gbc, dateButton, false);

        totalGUI.setOpaque(true);
        return totalGUI;
    }

    private static void createAndShowGUI(boolean editing) {
        JFrame frame = new JFrame(NEWTITLE);
        if (editing){
            frame.setTitle(EDITINGTITLE);
        }
        JFrame.setDefaultLookAndFeelDecorated(true);
        AppointmentView view = new AppointmentView();
        frame.setContentPane(view.createContentPane(editing));

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.pack();
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

    public void setRoomChosen(boolean chosen){
        this.roomChosen = chosen;
    }



    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addRoomButton){
            AddRoom.createAndShowGUI();
        }
        if (e.getSource() == addParticipantButton){
            AddParticipant.createAndShowGUI();
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable(){
            public void run() {
                createAndShowGUI(true);
            }
        });
    }

}
