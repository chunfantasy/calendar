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
public class AddExternalParticipant extends JPanel implements ActionListener {
    private JTextField nameField, emailField;
    private JButton inviteButton;
    private JLabel nameLabel, emailLabel;
    private JPanel totalGUI;
    private boolean emailBackground, nameBackground;

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
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getSource() == nameField){
                    if (nameBackground){
                        nameField.setBackground(new JTextField().getBackground());
                        nameBackground = false;
                    }
                }
            }
        });
        nameLabel = new JLabel("Navn ");
        nameLabel.setHorizontalAlignment(SwingConstants.RIGHT);


        emailField = new JTextField(15);
        emailField.addActionListener(this);
        emailLabel = new JLabel("Email ");
        emailLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        emailField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getSource() == emailField){
                    if (emailBackground){
                        emailField.setBackground(new JTextField().getBackground());
                        emailBackground = false;
                    }
                }
            }
        });

        // aidsx2


        inviteButton = new JButton("Inviter");
        inviteButton.addActionListener(this);
        inviteButton.setFocusable(false);

        setupGBC(1, 0, 0, gbc, nameLabel, false);
        setupGBC(2, 1, 0, gbc, nameField, true);
        setupGBC(1, 0, 1, gbc, emailLabel, false);
        setupGBC(2, 1, 1, gbc, emailField, true);
        setupGBC(1, 1, 2, gbc, inviteButton, true);

        totalGUI.setPreferredSize(new Dimension(400, 300));
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
                emailField.setBackground(new Color(250, 0, 0));
                emailBackground = true;
            }
            if (nameField.getText().length() == 0){
            }
        }
        if (e.getSource() == inviteButton){
            if (nameField.getText().length()== 0){
                nameField.setBackground(new Color(250, 0, 0));
                nameBackground = true;
            }
            if (!ev.validate(emailField.getText().trim())){
                emailField.setBackground(new Color(250, 0, 0));
                emailBackground = true;
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