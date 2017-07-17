package com.zombies.client;

import com.zombies.client.player.Player;
import com.zombies.client.zombies.Zombie;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

/**
 * Created by Faylo on 7/11/2017.
 */
public class ClientLocal extends JApplet implements KeyListener {
    private Graphics bg;
    private Image offscreen;
    Player p1;
    ArrayList<Zombie> zom;
    private ArrayList<String> dirs;
    World world;

    public void init() {
        world = new World(new Vec2(0, 0));
        Rectangle screen = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        offscreen = createImage(screen.width, screen.height);
        bg = offscreen.getGraphics();
        setSize(screen.width, screen.height);
        zom = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            zom.add(new Zombie(new Rectangle((int) (Math.random() * screen.width), (int) (Math.random() * screen.height), 10, 10), world));
        }
        float time = 30.0f;
        Timer timer = new Timer((int) time, ae -> {
            p1.move(dirs);
            for (Zombie zoms : zom) {
                zoms.move(p1);
            }
            repaint();
            world.step(time * .001f, 6, 2);
        });
        timer.start();
        dirs = new ArrayList<>();
        p1 = new Player(world);
        setFocusable(true);
        addKeyListener(this);
    }

    public void paint(Graphics g) {
        bg.clearRect(0, 0, offscreen.getWidth(this), offscreen.getHeight(this));
        p1.draw(bg);
        for (Zombie zoms : zom) {
            zoms.draw(bg);
        }
        g.drawImage(offscreen, 0, 0, this);
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
        }
        if (e.getKeyChar() == 's') {
            if (!dirs.contains("down")) {
                dirs.add("down");
            }
        }
        if (e.getKeyChar() == 'd') {
            if (!dirs.contains("right")) {
                dirs.add("right");
            }
        }
        if (e.getKeyChar() == 'a') {
            if (!dirs.contains("left")) {
                dirs.add("left");
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyChar() == 'w') {
            dirs.remove("up");
        }
        if (e.getKeyChar() == 's') {
            dirs.remove("down");
        }
        if (e.getKeyChar() == 'd') {
            dirs.remove("right");
        }
        if (e.getKeyChar() == 'a') {
            dirs.remove("left");
        }
    }
}
