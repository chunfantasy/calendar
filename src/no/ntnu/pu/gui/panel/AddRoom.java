package no.ntnu.pu.gui.panel;

import no.ntnu.pu.control.RoomControl;
import no.ntnu.pu.gui.view.AppointmentView;
import no.ntnu.pu.model.Appointment;
import no.ntnu.pu.model.Room;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;

public class AddRoom extends JPanel implements ActionListener, FocusListener {
    private JButton chooseButton, removeButton;
    private JTextField searchField;
    private JTable roomTable;
    private JLabel searchLabel, capacityLabel, selectedRoomLabel;
    private JComboBox capacityCB;
    private DefaultTableModel tableModel;
    private JPanel totalGUI;
    private static JFrame frame;
    private Appointment model;
    private String[] SIZES = {"5", "10", "15", "20", "50", "100", "200"};
    private String[] HEADER  = {"Romnavn", "Romkode", "Kapasitet"};
    private Object[][] data = {{new Room("Fraglejens"), "p72", 8}, {new Room("Isbjørn"), "s15", 15}, {new Room("Qalypso"), "r21", 4}};

//    public AddRoom(){
////        this.model = appointment;
//        createAndShowGUI();
//    }

    public JPanel createContentPane(){
        totalGUI = new JPanel();

        model = AppointmentView.getAppointment();

        GridBagConstraints gbc;
        totalGUI.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();

        // Look&Feel
        try{ UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());}
        catch (ClassNotFoundException e) {}
        catch (InstantiationException e) {}
        catch (IllegalAccessException e) {}
        catch (UnsupportedLookAndFeelException e) {}

        // tableModel - DefaultTableModel()
        tableModel = new DefaultTableModel(data, HEADER){
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
//        System.out.println(tableModel.getColumnCount()+ " x " + tableModel.getRowCount());
        updateModel(0);
//        System.out.println(tableModel.getColumnCount()+ " x " + tableModel.getRowCount());



        searchField = new JTextField("Søk");
        searchField.setColumns(15);
        searchField.addActionListener(this);
        searchField.addFocusListener(this);
        searchLabel = new JLabel("Søk");
        searchLabel.setPreferredSize(new Dimension(13, 13));
        searchLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        capacityCB = new JComboBox(SIZES);
        capacityCB.addActionListener(this);
        capacityLabel = new JLabel("Kapasitet: ");
        capacityLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        selectedRoomLabel = new JLabel("Valgt rom: ");
        try{
            selectedRoomLabel.setText("Valgt rom: " + model.getMeetingRoom());


        } catch (NullPointerException e){

        }

        chooseButton = new JButton("Velg");
        chooseButton.setFocusable(false);
        chooseButton.addActionListener(this);

        removeButton = new JButton("Fjern");
        removeButton.setFocusable(false);
        removeButton.addActionListener(this);

        // Table
        roomTable = new JTable();
        
        roomTable.setModel(tableModel);
        roomTable.setShowGrid(false);
        // selection
        roomTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        roomTable.setRowSelectionAllowed(true);
        roomTable.setColumnSelectionAllowed(false);
        roomTable.setCellSelectionEnabled(false);
        if (model.getMeetingRoom() != null){

        }
        // Renderer
        roomTable.setDefaultRenderer(roomTable.getColumnClass(0), new MyTableCellRenderer());
        roomTable.getColumnModel().getColumn(0).setHeaderRenderer(new MyTableHeaderRenderer());
        roomTable.getColumnModel().getColumn(1).setHeaderRenderer(new MyTableHeaderRenderer());
        roomTable.getColumnModel().getColumn(2).setHeaderRenderer(new MyTableHeaderRenderer());

        // Listeners
        roomTable.addFocusListener(this);
        // Scrollpane
        JScrollPane scroll = new JScrollPane(roomTable);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setPreferredSize(new Dimension(250, 150));
        roomTable.setFillsViewportHeight(true);


        setupGBC(5, 1, 0, 0, 0, gbc, selectedRoomLabel, true);
        setupGBC(1, 1, 0, 0, 1, gbc, searchLabel, true);
        setupGBC(1, 1, 0.5, 1, 1, gbc, searchField, true);
        setupGBC(1, 1, 0.5, 3, 1, gbc, capacityCB, false);
        setupGBC(1, 1, 0.5, 2, 1, gbc, capacityLabel, true);
        setupGBC(4, 4, 0.5, 0, 2, gbc, scroll, true);
        setupGBC(2, 1, 0.5, 0, 7, gbc, chooseButton, false);
        setupGBC(2, 1, 0.5, 3, 7, gbc, removeButton, false);

        totalGUI.setOpaque(true);
        totalGUI.setPreferredSize(new Dimension(400, 300));
        return totalGUI;

    }



    public static void createAndShowGUI() {
        frame = new JFrame("Book rom");
        JFrame.setDefaultLookAndFeelDecorated(true);
        AddRoom view = new AddRoom();
        frame.setContentPane(view.createContentPane());

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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

    public void actionPerformed(ActionEvent e) {
        // searchField - search for specific word
        if (e.getSource() == searchField){
            String text = searchField.getText();
            for (int row = 0; row <= roomTable.getRowCount() - 1; row++){
                if (text.equals(roomTable.getValueAt(row, 0))){
                    // this will automatically set the view of the scroll in the location of the value
                    roomTable.scrollRectToVisible(roomTable.getCellRect(row, 0, true));

                    // this will automatically set the focus of the seached/selected row/value
                    roomTable.setRowSelectionInterval(row, row);
                }
            }
        }
        // capacityCB - search for rooms with specific capacity
        if (e.getSource() == capacityCB){
            JComboBox cb = (JComboBox)e.getSource();
            int value = Integer.parseInt((String)cb.getSelectedItem());
            System.out.println(value);
            updateModel(value);
        }

        if (e.getSource() == chooseButton){
            model.setMeetingRoom((Room)roomTable.getValueAt(roomTable.getSelectedRow(), 0));
            selectedRoomLabel.setText("Valgt rom: " + model.getMeetingRoom());
            selectedRoomLabel.setVisible(true);

        }
        if (e.getSource() == removeButton){
            if (model.getMeetingRoom() != null){
                model.setMeetingRoom(null);
                selectedRoomLabel.setText("Valgt rom: ");
            }
        }

    }


    public void updateModel(int value){
            // todo
        ArrayList<Room> suitable = RoomControl.getSuitableRooms(value);
        DefaultTableModel ting = new DefaultTableModel();
        for (Room rom : suitable){
            System.out.println(rom);
            Object[] obj = {rom, rom.getId(), rom.getCapacity()};
//            System.out.println(obj);
            ting.addRow(obj);
        }
//        System.out.println(ting.getColumnCount()+ " x " + ting.getRowCount());


        tableModel = ting;
    }

    @Override
    public void focusGained(FocusEvent e) {
        if (e.getSource() == searchField) {
            searchField.setText("");
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        if (e.getSource() == searchField){
            if (searchField.getText().length() == 0) {
                searchField.setText("Søk");
            }
        }
        
        if (e.getSource() == roomTable){
            roomTable.clearSelection();
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
            // SE PÅ - Center Header Text
            setHorizontalTextPosition(SwingConstants.RIGHT); // Funker ikke
            // SE PÅ - Center Header Text
            return this;
        }
    }
    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }


}

