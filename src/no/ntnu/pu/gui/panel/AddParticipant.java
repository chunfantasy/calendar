package no.ntnu.pu.gui.panel;

import no.ntnu.pu.control.GroupControl;
import no.ntnu.pu.control.PersonControl;
import no.ntnu.pu.gui.view.AppointmentView;
import no.ntnu.pu.model.Appointment;
import no.ntnu.pu.model.Group;
import no.ntnu.pu.model.Participant;
import no.ntnu.pu.model.Person;
import no.ntnu.pu.storage.PersonStorage;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;


public class AddParticipant extends JPanel implements ActionListener, ListSelectionListener, FocusListener {
    private JButton addButton;
    private JTextField searchField;
    private JLabel searchLabel;
    private JTable participantTable;
    private JToggleButton personButton, groupButton;
    private ButtonGroup buttonGroup;
    private DefaultTableModel personTableModel, groupTableModel;
    private static JFrame frame;
    private static Appointment model;
    private JPanel totalGUI;
    private Object[][] Personer = {{"Bernt"}, {"Gerd"}, {"Gjørdis"}, {"Olga"}, {"Per Hege"}, {"Arnold"}, {"Ludvig"}}, Grupper = {{"Juventus"}, {"Inter"}, {"Napoli"}, {"Milan"}, {"Roma"}};
    private String[] HEADER = {"Navn"};

    public JPanel createContentPane(){
        totalGUI = new JPanel();


        // Gridbag
        GridBagConstraints gbc;
        totalGUI.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();

        model = AppointmentView.getAppointment();

        // Look&Feel
        try{ UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());}
        catch (ClassNotFoundException e) {}
        catch (InstantiationException e) {}
        catch (IllegalAccessException e) {}
        catch (UnsupportedLookAndFeelException e) {}

        // Icons
        ImageIcon icon = new ImageIcon(getClass().getResource("search.png"));

        // personTableModel
        personTableModel = new DefaultTableModel(Personer, HEADER){
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        updateModel();

        // groupTableModel
        groupTableModel = new DefaultTableModel(Grupper, HEADER){
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        updateGroupModel();

        // Buttons
        addButton = new JButton("Legg til");
        addButton.addActionListener(this);
        addButton.setFocusable(false);
        addButton.setHorizontalAlignment(SwingConstants.CENTER);

        searchField = new JTextField("Søk");
//        searchField.setColumns(10);
        searchField.addFocusListener(this);
        searchField.addActionListener(this);
        searchField.setHorizontalAlignment(SwingConstants.LEFT);
        searchLabel = new JLabel(icon);
        searchLabel.setPreferredSize(new Dimension(13, 13));
        searchLabel.setHorizontalAlignment(SwingConstants.LEFT);

        // participantTable
        participantTable = new JTable();
        // setup
        participantTable.setModel(personTableModel);
        participantTable.setShowGrid(false);
        // Selection
        participantTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        participantTable.setCellSelectionEnabled(false);
        participantTable.setColumnSelectionAllowed(false);
        participantTable.setRowSelectionAllowed(true);
        // Renderer
        participantTable.setDefaultRenderer(participantTable.getColumnClass(0), new MyTableCellRenderer());
        participantTable.getColumnModel().getColumn(0).setHeaderRenderer(new MyTableHeaderRenderer());
        // Listener
        participantTable.addFocusListener(this);
        // ScrollPane
        JScrollPane scroll = new JScrollPane(participantTable);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setPreferredSize(new Dimension(250, 150));
        participantTable.setFillsViewportHeight(true);

        // buttons
        personButton = new JToggleButton("Person");
        personButton.addActionListener(this);
        personButton.setSelected(true);

        groupButton = new JToggleButton("Gruppe");
        groupButton.addActionListener(this);

        buttonGroup = new ButtonGroup();
        buttonGroup.add(personButton);
        buttonGroup.add(groupButton);


        setupGBC(1, 1, 0.5, 1, 1, gbc, searchLabel, true);
        setupGBC(1, 1, 0.5, 0, 1, gbc, searchField, true);
        setupGBC(2, 4, 0.5, 0, 2, gbc, scroll, false);
        setupGBC(2, 1, 0.5, 0, 6, gbc, addButton, false);
//        setupGBC(1, 1, 0.5, 3, 6, gbc, cancelButton, false);
        setupGBC(1, 1, 0.5, 0, 0, gbc, personButton, true);
        setupGBC(1, 1, 0.5, 1, 0, gbc, groupButton, true);



        totalGUI.setOpaque(true);
        totalGUI.setPreferredSize(new Dimension(400, 300));
        return totalGUI;

    }

    public static void createAndShowGUI(){
        frame = new JFrame("Legg til deltaker");
        JFrame.setDefaultLookAndFeelDecorated(true);
        AddParticipant view = new AddParticipant();
        frame.setContentPane(view.createContentPane());

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
        frame.pack();
    }

    public void updateModel(){
        ArrayList<Person> all = PersonControl.getAll();
        Appointment mod = AppointmentView.getAppointment();

        // Finds all persons who're not already invited
        ArrayList<Person> participants = mod.getParticipants();
        if (all.size() == participants.size()){
            for(int i = personTableModel.getRowCount()-1; i>-1;i--){
                personTableModel.removeRow(i);
            }
        }
        if (all.size() != participants.size()){
            for(int i = 0; i<all.size()-1; i++){
                for(int j = 0; j<participants.size();j++){
                    String firstN = participants.get(j).getName().trim();
                    String secondN = all.get(i).getName().trim();
                    if (firstN.equals(secondN)){
                        System.out.println("fjernet");
                        all.remove(i);
                    }
                }
            }
            // Clears current tableModel
            if (personTableModel.getRowCount() > 0){
                for(int i = personTableModel.getRowCount()-1; i>-1; i--){
                    personTableModel.removeRow(i);
                }
            }
            // Fills in tableModel
            for(int i = 0; i<all.size(); i++) {
                Person[] obj = {all.get(i)};
                personTableModel.addRow(obj);
            }
        }
    }

    public void updateGroupModel(){
        if (groupTableModel.getRowCount() > 0){
            for(int i = groupTableModel.getRowCount()-1; i>-1; i--){
                groupTableModel.removeRow(i);
            }
        }
        ArrayList<Group> grp = GroupControl.getAll();
        for(int i = 0; i< grp.size(); i++){
            Group[] obj = {grp.get(i)};
            groupTableModel.addRow(obj);
        }
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

    public Appointment getModel(){
        return model;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Person - Group toggle
        if(e.getSource() == personButton){
            updateModel();
            participantTable.setModel(personTableModel);
            searchField.setText("Søk");
            participantTable.getColumnModel().getColumn(0).setHeaderRenderer(new MyTableHeaderRenderer());
            participantTable.getTableHeader().repaint();
        }
        if (e.getSource() == groupButton){
            updateModel();
            participantTable.setModel(groupTableModel);
            searchField.setText("Søk");
            participantTable.getColumnModel().getColumn(0).setHeaderRenderer(new MyTableHeaderRenderer());
            participantTable.getTableHeader().repaint();
        }
        // searchField - search for specific word
        if (e.getSource() == searchField){
            String text = searchField.getText();
            if (personButton.isSelected()){
                for (int row = 0; row<personTableModel.getRowCount(); row++){
                    Person p = (Person)personTableModel.getValueAt(row, 0);
                    if (text.equals(p.getName())){
                        // this will automatically set the view of the scroll in the location of the value
                        participantTable.scrollRectToVisible(participantTable.getCellRect(row, 0, true));
                        // this will automatically set the focus of the searched/selected row/value
                        participantTable.setRowSelectionInterval(row, row);
                    }
                }
            } else {
                for (int row = 0; row<groupTableModel.getRowCount(); row++){
                    Group g = (Group)groupTableModel.getValueAt(row, 0);
                    if (text.equals(g.getName())){
                        // this will automatically set the view of the scroll in the location of the value
                        participantTable.scrollRectToVisible(participantTable.getCellRect(row, 0, true));
                        // this will automatically set the focus of the searched/selected row/value
                        participantTable.setRowSelectionInterval(row, row);
                    }
                }
            }



        }
        if (e.getSource() == addButton){
            if (personButton.isSelected()){
                model.addParticipant((Person)participantTable.getValueAt(participantTable.getSelectedRow(), 0));
                personTableModel.removeRow(participantTable.getSelectedRow());
            }
            if (groupButton.isSelected()){
                Group grp = (Group)participantTable.getValueAt(participantTable.getSelectedRow(), 0);
                ArrayList<Person> persons = grp.getPersons();
                for (Person p : persons){
                    model.addParticipant(p);
                }
                groupTableModel.removeRow(participantTable.getSelectedRow());
            }
        }



    }

    @Override
    public void valueChanged(ListSelectionEvent arg0) {

    }

    @Override
    public void focusGained(FocusEvent e) {
        if (e.getSource() == searchField){
            searchField.setText("");
        }

    }

    @Override
    public void focusLost(FocusEvent e) {
        if (e.getSource() == searchField){
            searchField.setText("Søk");
        }
        if (e.getSource() == participantTable){
            participantTable.clearSelection();
        }
    }

    class MyTableCellRenderer extends DefaultTableCellRenderer {

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
//            Component c = super.getTableCellRenderer
            Border outside = new MatteBorder(2, 0, 2, 0, new Color(0, 0, 0));
            Border inside = new EmptyBorder(0, 1, 0, 1);
            Border highLight = new CompoundBorder(outside, inside);
            if (!isSelected){
                setBackground(row % 2 == 0 ? Color.WHITE : new Color(130, 169, 215));
            }
            if (table.isRowSelected(row)){
                setBorder(highLight);
                setBackground(super.getBackground());
            }
            table.getTableHeader().setReorderingAllowed(false);
            return this;
        }
    }
    class MyTableHeaderRenderer extends DefaultTableCellRenderer {

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            setToolTipText((String) value);
            setBackground(Color.LIGHT_GRAY);
            setHorizontalAlignment(JLabel.CENTER);
            setBorder(new MatteBorder(0, 0, 2, 1, Color.black));
            return this;
        }
    }

    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable(){
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
