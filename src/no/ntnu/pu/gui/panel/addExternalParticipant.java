package no.ntnu.pu.gui.panel;

import no.ntnu.pu.control.PersonControl;
import no.ntnu.pu.model.Email;
import no.ntnu.pu.model.Person;
import no.ntnu.pu.net.SendMail;
import no.ntnu.pu.storage.PersonStorage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Bakkan on 19.03.14.
 */
public class addExternalParticipant extends JPanel implements ActionListener {
    private JTextField nameField, emailField;
    private JButton inviteButton, cancelButton;
    private JLabel nameLabel, emailLabel, mailErrorLabel, nameErrorLabel;
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

        nameField = new JTextField();
        nameField.addActionListener(this);
        nameField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (nameErrorLabel.isVisible()){
                    nameErrorLabel.setVisible(false);
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        nameLabel = new JLabel("Navn ");
        nameLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        mailErrorLabel = new JLabel(UIManager.getIcon("OptionPane.errorIcon"));
        mailErrorLabel.setHorizontalAlignment(SwingConstants.LEFT);
        mailErrorLabel.setVisible(false);
        nameErrorLabel = mailErrorLabel;

        emailField = new JTextField();
        emailField.addActionListener(this);
        emailLabel = new JLabel("Email ");
        emailLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        emailField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (mailErrorLabel.isVisible()){
                    mailErrorLabel.setVisible(false);
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        cancelButton = new JButton("Avbryt");
        cancelButton.addActionListener(this);
        cancelButton.setFocusable(false);

        inviteButton = new JButton("Inviter");
        inviteButton.addActionListener(this);
        inviteButton.setFocusable(false);

        setupGBC(1, 0, 0, gbc, nameLabel, false);
        setupGBC(2, 1, 0, gbc, nameField, true);
        setupGBC(1, 3, 0, gbc, nameErrorLabel, false);
        setupGBC(1, 0, 1, gbc, emailLabel, false);
        setupGBC(2, 1, 1, gbc, emailField, true);
        setupGBC(1, 3, 1, gbc, mailErrorLabel, false);
        setupGBC(1, 1, 2, gbc, inviteButton, true);
        setupGBC(1, 2, 2, gbc, cancelButton, true);

        return totalGUI;
    }

    public void setupGBC(int gridwidth, int x, int y, GridBagConstraints c, Component comp, boolean fill){
        c.gridwidth = gridwidth;
        c.gridx = x;
        c.gridy = y;
        if (fill){
            c.fill = GridBagConstraints.HORIZONTAL;
        } else {
            c.fill = GridBagConstraints.NONE;
        }
        totalGUI.add(comp, c);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        EmailValidator ev = new EmailValidator();
        if (e.getSource() == emailField){
            if (nameField.getText().length() > 0 && ev.validate(emailField.getText().trim())){
                PersonControl.insertPerson(new Person(nameField.getText()));
                // todo: legg person til i liste
            }
            if (!ev.validate(emailField.getText().trim())){
                mailErrorLabel.setVisible(true);
            }
            if (nameField.getText().length() == 0){
                nameErrorLabel.setVisible(true);
            }
        }
    }
}



class EmailValidator {

    private Pattern pattern;
    private Matcher matcher;

    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public EmailValidator() {
        pattern = Pattern.compile(EMAIL_PATTERN);
    }

    /**
     * Validate hex with regular expression
     *
     * @param hex
     *            hex for validation
     * @return true valid hex, false invalid hex
     */
    public boolean validate(final String hex) {

        matcher = pattern.matcher(hex);
        return matcher.matches();

    }
}