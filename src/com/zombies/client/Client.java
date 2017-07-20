package com.zombies.client;

import com.zombies.client.communicator.Communicator;
import com.zombies.client.game.player.Player;
import com.zombies.client.game.zombies.Zombie;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Scanner;

public class Client extends JApplet implements MouseListener, KeyListener {

    public static final boolean LOCAL = false;
    public static ArrayList<Zombie> zombies;
    public static ArrayList<Player> players;
    private Graphics bufferGraphics;
    private Image offscreen;
    private ArrayList<String> dirs;
    private String user;

    public void init() {
        Scanner sc = new Scanner(System.in);
        System.out.print("ENTER A USERNAME: ");
        user = sc.next();


        setSize(1000, 1000);
        setFocusable(true);

        Rectangle screen = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        offscreen = createImage(screen.width, screen.height);
        bufferGraphics = offscreen.getGraphics();

        dirs = new ArrayList<>();
        zombies = new ArrayList<>();
        players = new ArrayList<>();

        Communicator.connect();
        Communicator.joinGame(user);

        addMouseListener(this);
        addKeyListener(this);
    }

    public void paint(Graphics g) {
        bufferGraphics.clearRect(0, 0, offscreen.getWidth(this), offscreen.getHeight(this));

        for (Zombie zombie : zombies)
            zombie.draw(bufferGraphics);

        for (Player player : players)
            player.draw(bufferGraphics);

        g.drawImage(offscreen, 0, 0, this);
        repaint();
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyChar() == 'w') {
            if (!dirs.contains("up")) {
                dirs.add("up");
            }
        } else if (e.getKeyChar() == 's') {
            if (!dirs.contains("down")) {
                dirs.add("down");
            }
        } else if (e.getKeyChar() == 'd') {
            if (!dirs.contains("right")) {
                dirs.add("right");
            }
        } else if (e.getKeyChar() == 'a') {
            if (!dirs.contains("left")) {
                dirs.add("left");
            }
        }

        String[] dirsArray = new String[dirs.size()];
        dirsArray = dirs.toArray(dirsArray);
        Communicator.setDirection(dirsArray);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyChar() == 'w') {
            dirs.remove("up");
        } else if (e.getKeyChar() == 's') {
            dirs.remove("down");
        } else if (e.getKeyChar() == 'd') {
            dirs.remove("right");
        } else if (e.getKeyChar() == 'a') {
            dirs.remove("left");
        }

        String[] dirsArray = new String[dirs.size()];
        dirsArray = dirs.toArray(dirsArray);
        Communicator.setDirection(dirsArray);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
