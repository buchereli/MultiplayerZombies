package com.zombies.client;

import com.zombies.client.screens.Client;
import com.zombies.client.screens.Login;

import javax.swing.*;
import java.awt.*;

/**
 * Created by jack on 7/21/17.
 */
public class ClientMain {
    public static void main(String[] args) {
        //Window to hold our main screen
        JFrame jframe = new JFrame();
        jframe.setTitle("Zombie Game");
        jframe.setSize(500, 500);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Card layout to switch through screens
        CardLayout cardLayout = new CardLayout();
        JPanel mainPanel = new JPanel();
        //ServerMain screen container
        mainPanel.setLayout(cardLayout);

        //Our screens
        Client gameScreen = new Client(jframe);
        Login login = new Login(cardLayout, mainPanel, jframe);

        //Adding all the screens to the main container
        mainPanel.add(login);
        mainPanel.add(gameScreen);

        //Adding to our window
        jframe.add(mainPanel);

        //Show the world!
        jframe.setVisible(true);
    }
}
