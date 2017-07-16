package com.zombies.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Client extends JApplet implements MouseListener, KeyListener {

    public static final boolean LOCAL = true;

    private Graphics bufferGraphics;
    private Image offscreen;

    public void init() {
        Rectangle screen = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        offscreen = createImage(screen.width, screen.height);
        bufferGraphics = offscreen.getGraphics();

        addMouseListener(this);
        addKeyListener(this);
    }

    public void paint(Graphics g) {
        bufferGraphics.clearRect(0, 0, offscreen.getWidth(this), offscreen.getHeight(this));

        g.drawImage(offscreen, 0, 0, this);
        repaint();
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

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
