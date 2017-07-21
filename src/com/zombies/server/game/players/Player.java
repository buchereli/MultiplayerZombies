package com.zombies.server.game.players;

import com.zombies.server.game.Game;
import com.zombies.server.game.util.Character;
import org.jbox2d.dynamics.World;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Faylo on 7/11/2017.
 */
public class Player extends Character {
    private Rectangle bounds;
    private double vx, vy, accel;
    private float maxSpeed;
    private boolean alive;
    private ArrayList<String> dirs;
    private String user;
    private double health;

    public Player(World world, String user) {
        super(500 / Game.PPM, 500 / Game.PPM, 9 / Game.PPM, 9 / Game.PPM, world, "Player");
        bounds = new Rectangle(10, 10);
        alive = true;
        vx = 0;
        vy = 0;
        maxSpeed = 100 / Game.PPM;
        accel = 100;
        dirs = new ArrayList<>();
        this.user = user;
        health = 100;
    }

    public ClientPlayer clientPlayer() {
        return new ClientPlayer(new Rectangle(bounds.x, bounds.y, bounds.width, bounds.height), user, health);
    }

    public void move() {
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

            vx *= .8;
            vy *= .8;

            bounds.x = (int) (body.getPosition().x * Game.PPM);
            bounds.y = (int) (body.getPosition().y * Game.PPM);
        }
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void hit(double attackPower) {
        health -= attackPower;
        if (health <= 0)
            alive = false;
    }

    public void setDirs(String[] dirs) {
        this.dirs = new ArrayList<>(Arrays.asList(dirs));
    }

    public String getUser() {
        return user;
    }

    public boolean isAlive() {
        return alive;
    }
}
