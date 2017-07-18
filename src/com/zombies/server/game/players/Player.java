package com.zombies.server.game.players;

import com.zombies.server.game.util.Character;
import org.jbox2d.dynamics.World;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Faylo on 7/11/2017.
 */
public class Player extends Character {
    private Rectangle bounds;
    private double vx, vy, accel;
    private int maxSpeed;
    private boolean alive;

    public Player(World world) {
        super(new Rectangle(500, 500, 9, 9), world, "Player");
        bounds = new Rectangle(500, 500, 10, 10);
        alive = true;
        vx = 0;
        vy = 0;
        maxSpeed = 300;
        accel = 100;
    }

    public void draw(Graphics g) {
        g.setColor(Color.BLUE);
        if (alive) {
            g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
        }
    }

    public void move(ArrayList<String> dirs) {
        if (alive) {
            if (dirs.contains("up"))
                vy -= accel;
            if (dirs.contains("down"))
                vy += accel;
            if (dirs.contains("right"))
                vx += accel;
            if (dirs.contains("left"))
                vx -= accel;

            if (vx > maxSpeed)
                vx = maxSpeed;
            else if (vx < -maxSpeed)
                vx = -maxSpeed;

            if (vy > maxSpeed)
                vy = maxSpeed;
            else if (vy < -maxSpeed)
                vy = -maxSpeed;

            setVY((float) vy);
            setVX((float) vx);

            vx *= .9;
            vy *= .9;

            bounds = new Rectangle((int) body.getPosition().x, (int) body.getPosition().y, bounds.width, bounds.height);
        }
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void hit() {
        alive = false;
    }
}
