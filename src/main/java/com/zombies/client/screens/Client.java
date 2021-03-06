package com.zombies.client.screens;

import com.zombies.client.communicator.Communicator;
import com.zombies.client.game.hud.HUD;
import com.zombies.client.game.map.Map;
import com.zombies.client.game.player.Player;
import com.zombies.client.game.supplies.SupplyCache;
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
import java.lang.reflect.Array;
import java.util.ArrayList;

import static java.awt.event.KeyEvent.VK_S;

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
    public static ArrayList<SupplyCache> supplies;
    public static String user;

    private Graphics bufferGraphics;
    private Image offscreen;
    private ArrayList<String> dirs;

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

        dirs = new ArrayList<>();
        zombies = new ArrayList<>();
        players = new ArrayList<>();
        supplies = new ArrayList<>();

        window.addMouseListener(this);
        window.addKeyListener(this);
        new Map();
    }

    public void paint(Graphics g) {
        Communicator.update();

        bufferGraphics.clearRect(0, 0, offscreen.getWidth(this), offscreen.getHeight(this));

        Rectangle bounds = new Rectangle();
        if (getPlayer() != null)
            bounds = getPlayer().getBounds();
        Point shift = getShift();

        Map.draw(bufferGraphics, new Point(bounds.x, bounds.y), shift);

        for (Zombie zombie : zombies)
            zombie.draw(bufferGraphics, shift);

        for (Player player : players)
            player.draw(bufferGraphics, shift);

        for (SupplyCache supplyCache : supplies)
            supplyCache.draw(bufferGraphics, shift);

        HUD.draw(bufferGraphics, getHeight(), getWidth());

        g.drawImage(offscreen, 0, 0, this);

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
        if (user != null) {
            if (e.getKeyCode() == KeyEvent.VK_W) {
                if (!dirs.contains("up")) {
                    dirs.add("up");
                }
            } else if (e.getKeyCode() == KeyEvent.VK_S) {
                if (!dirs.contains("down")) {
                    dirs.add("down");
                }
            } else if (e.getKeyCode() == KeyEvent.VK_D) {
                if (!dirs.contains("right")) {
                    dirs.add("right");
                }
            } else if (e.getKeyCode() == KeyEvent.VK_A) {
                if (!dirs.contains("left")) {
                    dirs.add("left");
                }
            } else if (e.getKeyCode() == KeyEvent.VK_SPACE && !dirs.contains("SPACE")) {
                Communicator.fireShot();
                dirs.add("SPACE");
            }
            if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
                if (!dirs.contains("TURBO SPEED"))
                    dirs.add("TURBO SPEED");
            }
            if (e.getKeyCode() == KeyEvent.VK_R) {
                if (!dirs.contains("r")){
                    dirs.add("r");
                }
            }


            String[] dirsArray = new String[dirs.size()];
            dirsArray = dirs.toArray(dirsArray);
            Communicator.setDirection(dirsArray);
        }
    }


    @Override
    public void keyReleased(KeyEvent e) {
        if (user != null) {
            if (e.getKeyCode() == KeyEvent.VK_W) {
                dirs.remove("up");
            } else if (e.getKeyCode() == VK_S) {
                dirs.remove("down");
            } else if (e.getKeyCode() == KeyEvent.VK_D) {
                dirs.remove("right");
            } else if (e.getKeyCode() == KeyEvent.VK_A) {
                dirs.remove("left");
            } else if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
                dirs.remove("TURBO SPEED");
            } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                dirs.remove("SPACE");
            } else if (e.getKeyCode() == KeyEvent.VK_R) {
                    dirs.remove("r");
            }

            String[] dirsArray = new String[dirs.size()];
            dirsArray = dirs.toArray(dirsArray);
            Communicator.setDirection(dirsArray);
        }
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
