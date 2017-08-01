package com.zombies.client.screens;

import com.zombies.client.communicator.Communicator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by jack on 7/21/17.
 */
public class Login extends JPanel implements ActionListener {

    JLabel usernameLbl;
    JLabel passwordLbl;
    JLabel errorLbl;
    JTextField username;
    JTextField password;
    JButton submit;

    CardLayout cardLayout;
    JPanel container;

    Box vbox;


    /*
     * Takes in the card layout for screen switching,
     * and the a JPanel that has the card layout
     * set as its layout. Also a JFrame that it uses to get
     * the size of the window.
     */

    public Login(CardLayout cardLayout, JPanel container, JFrame window) {
        //Layout used for switching screens
        this.cardLayout = cardLayout;

        //JPanel that holds all our screens;
        this.container = container;

        //Variable Init
        usernameLbl = new JLabel("Username: ");
        passwordLbl = new JLabel("Password: ");
        username = new JTextField();
        password = new JPasswordField();
        submit = new JButton("Submit");
        errorLbl = new JLabel("Enter a username");

        //Set font sizes
        username.setFont(new Font("Arial", Font.PLAIN, 30));
        password.setFont(new Font("Arial", Font.PLAIN, 30));

        //Button Click Listener
        submit.addActionListener(this);

        //Make it look nice in this layout
        vbox = Box.createVerticalBox();
        vbox.setPreferredSize(new Dimension(window.getWidth() - 20, window.getHeight() / 4));

        //Add it all to the boxLayout in the right order
        vbox.add(errorLbl);
        vbox.add(usernameLbl);
        vbox.add(username);
        //vbox.add(passwordLbl);
        //vbox.add(password);
        vbox.add(submit);
        this.add(vbox);
    }

    /*
     * Callback for when the button is pressed
     * Takes in an actionEvent. This can
     * be used to tell what type of click
     * it was.
     *
     * This should never be called directly by our programmers!
     */

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        //When the button is pressed to login, check it here.
        //For now we will just switch to the next screen anyways.
        if (username.getText().length() <= 12 && username.getText().length() > 0) {
            Client.user = username.getText();
            Communicator.connect();
            Communicator.joinGame(Client.user);
            cardLayout.next(container);
        } else {
            errorLbl.setFont(new Font("Arial", Font.PLAIN, 80));
            errorLbl.setText("LESS THAN 12 CHARS");
        }
    }
}
