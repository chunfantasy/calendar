package no.ntnu.pu.gui.view;

import no.ntnu.pu.control.CalendarControl;
import no.ntnu.pu.control.NotificationControl;
import no.ntnu.pu.control.PersonControl;
import no.ntnu.pu.model.*;
import no.ntnu.pu.net.SendMail;
import no.ntnu.pu.storage.AppointmentStorage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginView extends JPanel {

    private JTextField userField;
    private JPasswordField passField;
    private JButton btnLogin, btnForgottenPassword;
    private String usernameInput, passwordInput;
    private static JLabel lblUsername, lblPassword, lblError;
    private static Container pane;
    private static JFrame frmMain;

    public LoginView(){

        /**Set look and feel, catch exceptions**/
        try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());}
        catch (ClassNotFoundException e) {}
        catch (InstantiationException e) {}
        catch (IllegalAccessException e) {}
        catch (UnsupportedLookAndFeelException e) {}

        JPanel panel = new JPanel();
        add(panel);
        panel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        /**Labels**/
        lblUsername = new JLabel("E-post: ", JLabel.LEFT);
        lblPassword = new JLabel("Passord: ", JLabel.LEFT);
        lblError = new JLabel("", JLabel.CENTER);

        /**Textfields (with listeners)**/
        userField = new JTextField(20);
        passField = new JPasswordField(20);
        userField.addActionListener(new myUserAction());
        passField.addActionListener(new myPasswordAction());

        /**Buttons (with listeners)**/
        btnLogin = new JButton("Logg inn");
        btnLogin.addActionListener(new myLoginAction());
        btnForgottenPassword = new JButton("Glemt passord?");
        btnForgottenPassword.addActionListener(new myForgottenAction());

        /**Adding components to panel**/
        panelAdd(3, 0, 0, constraints, lblError, panel);
        panelAdd(1, 0, 1, constraints, lblUsername, panel);
        panelAdd(1, 0, 2, constraints, lblPassword, panel);
        panelAdd(2, 1, 1, constraints, userField, panel);
        panelAdd(2, 1, 2, constraints, passField, panel);
        panelAdd(1, 1, 3, constraints, btnLogin, panel);
        panelAdd(1, 2, 3, constraints, btnForgottenPassword, panel);
    }

    public void panelAdd(int gridwidth, int gridx, int gridy, GridBagConstraints c, Component comp, JPanel panel){
        if(comp.equals(btnLogin) || comp.equals(btnForgottenPassword)){
            c.insets = new Insets(10, 0, 0, 3);
        }
        c.gridwidth = gridwidth;
        c.gridx = gridx;
        c.gridy = gridy;
        panel.add(comp, c);
    }

    public String getUsernameInput() {
        return usernameInput;
    }

    public void setUsernameInput(String usernameInput) {
        this.usernameInput = usernameInput;
    }

    public String getPasswordInput() {
        return passwordInput;
    }

    public void setPasswordInput(String passwordInput) {
        this.passwordInput = passwordInput;
    }

    class myUserAction implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            setUsernameInput(userField.getText());
            btnLogin.doClick();
        }
    }

    class myPasswordAction implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            setPasswordInput(new String(passField.getPassword()));
            btnLogin.doClick();
        }
    }

    class myLoginAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if(PersonControl.getPersonByEmail(userField.getText()) != null){
                if(new String(passField.getPassword()).equals(PersonControl.getPersonByEmail(userField.getText()).getPassword())){
                    Person loggedIn = PersonControl.getPersonByEmail(userField.getText());
                    frmMain.dispose();
                    PersonControl.setModel(loggedIn);
                    MainView mainView = new MainView();
                }
            }
            else{
                lblError.setText("Feil brukernavn og/eller passord");
                userField.setText("");
                passField.setText("");
            }
        }
    }

    class myForgottenAction implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            if(userField.getText().length() == 0){
                lblError.setText("Feil brukernavn og/eller passord");
                userField.setText("");
                passField.setText("");
            }
            else{
                new SendMail(new Email("Gigakalender", usernameInput, "DITT PASSORD", PersonControl.getPersonByEmail(usernameInput).getPassword()));
            }
        }
    }

    public static void main(String args[]){
        frmMain = new JFrame("Logg inn");
        pane = frmMain.getContentPane();
        frmMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pane.add(new LoginView());
        frmMain.pack();
        frmMain.setLocationRelativeTo(null);
        frmMain.setResizable(false);
        frmMain.setVisible(true);
    }
}