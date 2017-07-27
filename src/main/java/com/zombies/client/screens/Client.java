package com.zombies.client.screens;

import com.zombies.client.communicator.Communicator;
import com.zombies.client.game.hud.HUD;
import com.zombies.client.game.player.Player;
import com.zombies.client.game.zombies.Zombie;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class Client extends JPanel implements MouseListener, KeyListener {

    /*
        Make this a JPanel
        Make another class that is the main JApplet.
        Have all the JPanels added to a card layout.
        Use the next command and first command to switch between login and game panels.
     */

    public static final boolean LOCAL = true;
    public static ArrayList<Zombie> zombies;
    public static ArrayList<Player> players;
    public static String user;
    private Graphics bufferGraphics;
    private Image offscreen;
    private ArrayList<String> dirs;
    private BufferedImage map;

    /*
     * Now takes in the main window that
     * holds the main panel.
     */
    public Client(JFrame window) {

        window.setSize(1000, 1000);
        window.setFocusable(true);

        Rectangle screen = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        offscreen = new BufferedImage(screen.width, screen.height, BufferedImage.TYPE_INT_ARGB);
        bufferGraphics = offscreen.getGraphics();

        try {
            map = ImageIO.read(Client.class.getResourceAsStream("/background.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        dirs = new ArrayList<>();
        zombies = new ArrayList<>();
        players = new ArrayList<>();

        window.addMouseListener(this);
        window.addKeyListener(this);
    }

    //FPS variables
    long nextSecond = System.currentTimeMillis() + 1000;
    int framesInCurrentSecond = 0;
    int framesInLastSecond = 0;

    public void paint(Graphics g) {
        bufferGraphics.clearRect(0, 0, offscreen.getWidth(this), offscreen.getHeight(this));

        Point shift = getShift();

        bufferGraphics.drawImage(map, shift.x, shift.y, this);

        for (Zombie zombie : zombies)
            zombie.draw(bufferGraphics, shift);

        for (Player player : players)
            player.draw(bufferGraphics, shift);

        HUD.draw(bufferGraphics, getHeight(), getWidth(), framesInLastSecond);

        g.drawImage(offscreen, 0, 0, this);

        // start of FPS counter code
        // every second, it counts the number of times it repaints
        long currentTime = System.currentTimeMillis();
        if (currentTime > nextSecond) {
            nextSecond += 1000;
            framesInLastSecond = framesInCurrentSecond;
            framesInCurrentSecond = 0;
        }
        framesInCurrentSecond++;
        // end

        repaint();
    }

    private Point getShift() {
        Player player = getPlayer();
        if (player == null)
            return new Point();
        return new Point(-player.getBounds().x - player.getBounds().width / 2 + getWidth() / 2,
                -player.getBounds().y - player.getBounds().height / 2 + getHeight() / 2);
    }

    private Player getPlayer() {
        for (Player player : players)
            if (player.getUser().equals(user)) {
            HUD.player = player;
                return player;
            }
        return null;
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
        } else if (e.getKeyChar() == ' ') {
            Communicator.fireShot(getRotation());
        }
        if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
            if (!dirs.contains("TURBO SPEED"))
                dirs.add("TURBO SPEED");
        }

            String[] dirsArray = new String[dirs.size()];
            dirsArray = dirs.toArray(dirsArray);
            Communicator.setDirection(dirsArray);
        }







    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyChar() == 'w') {
            dirs.remove("up");
        }  else if (e.getKeyChar() == 's') {
            dirs.remove("down");
        }  else if (e.getKeyChar() == 'd') {
            dirs.remove("right");
        } else if (e.getKeyChar() == 'a') {
            dirs.remove("left");
        } else if (e.getKeyCode()==KeyEvent.VK_SHIFT){
            dirs.remove("TURBO SPEED");
            dirs.remove("up");
            dirs.remove("down");
            dirs.remove("right");
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

    private double getRotation() {
        double radians = 0;
        if (dirs.contains("up")) {
            radians += .5;
            if (dirs.contains("right"))
                radians -= .25;
            if (dirs.contains("left"))
                radians += .25;
        }

        if (dirs.contains("down")) {
            radians -= .5;
            if (dirs.contains("right"))
                radians += .25;
            if (dirs.contains("left"))
                radians -= .25;
        }

        if (dirs.contains("right")) {
            if (dirs.contains("down"))
                radians -= .25;
            if (dirs.contains("up"))
                radians += .25;
        }

        if (dirs.contains("left")) {
            radians += 1;
            if (dirs.contains("down"))
                radians += .25;
            if (dirs.contains("up"))
                radians -= .25;
        }

        return radians;
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
