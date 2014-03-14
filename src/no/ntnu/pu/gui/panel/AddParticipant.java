package no.ntnu.pu.gui.panel;

import no.ntnu.pu.model.Participant;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class AddParticipant extends JPanel implements ActionListener, ListSelectionListener {
    private JButton addButton, cancelButton;
    private JTextField searchField;
    private JList<Participant> participantList;
    private JToggleButton personButton, groupButton;
    private ButtonGroup buttonGroup;
    private DefaultListModel<Participant> participantListModel;
    private JPanel totalGUI;

    public JPanel createContentPane(){
        totalGUI = new JPanel();

        // Gridbag
        GridBagConstraints gbc;
        totalGUI.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();

        // Look&Feel
        try{ UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());}
        catch (ClassNotFoundException e) {}
        catch (InstantiationException e) {}
        catch (IllegalAccessException e) {}
        catch (UnsupportedLookAndFeelException e) {}

        // participantListModel
        participantListModel = new DefaultListModel<Participant>();

        addButton = new JButton("Legg til");
        addButton.addActionListener(this);

        cancelButton = new JButton("Avbryt");
        cancelButton.addActionListener(this);

        /**
         searchField with Image
         final ImageIcon image = new ImageIcon(getClass().getResource("search.png"));
         searchField = new JTextField("Search"){
         protected void paintComponent(Graphics g){
         super.paintComponent(g);
         int centerPoint = (getHeight() - image.getIconHeight())/2;
         g.drawImage(image, 0, centerPoint, this);

         }
         };

         searchField.setMargin(new Insets(0, image.getWidth(), 0, 0));
         **/

        searchField = new JTextField("Søk");
        searchField.setColumns(15);

        participantList = new JList<Participant>(participantListModel);
        participantList.setModel(participantListModel);
        participantList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        participantList.addListSelectionListener(this);
        JScrollPane scroll = new JScrollPane(participantList);
        JLabel label = new JLabel("Navn", JLabel.LEFT);
        label.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.DARK_GRAY, Color.DARK_GRAY));
        scroll.setColumnHeaderView(label);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setPreferredSize(new Dimension(180, 150));

        personButton = new JToggleButton("Person");
        personButton.addActionListener(this);

        groupButton = new JToggleButton("Gruppe");
        groupButton.addActionListener(this);

        buttonGroup = new ButtonGroup();
        buttonGroup.add(personButton);
        buttonGroup.add(groupButton);


        setupGBC(2, 1, 0.5, 0, 1, gbc, searchField, true);
        setupGBC(4, 4, 0.5, 0, 2, gbc, scroll, true);
        setupGBC(1, 1, 0.5, 0, 6, gbc, addButton, false);
        setupGBC(1, 1, 0.5, 3, 6, gbc, cancelButton, false);
        setupGBC(1, 1, 0.5, 1, 0, gbc, personButton, false);
        setupGBC(1, 1, 0.5, 2, 0, gbc, groupButton, false);


        totalGUI.setOpaque(true);
        return totalGUI;

    }

    private static void createAndShowGUI(){
        JFrame frame = new JFrame("Legg til deltaker");
        JFrame.setDefaultLookAndFeelDecorated(true);
        AddParticipant view = new AddParticipant();
        frame.setContentPane(view.createContentPane());

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

    @Override
    public void actionPerformed(ActionEvent arg0) {

    }

    @Override
    public void valueChanged(ListSelectionEvent arg0) {

    }

    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable(){
            public void run() {
                createAndShowGUI();
            }
        });
    }

}
