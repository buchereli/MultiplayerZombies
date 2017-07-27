package com.zombies.server.game.players;

import com.zombies.server.game.Game;
import com.zombies.server.game.util.Actor;
import com.zombies.server.game.util.ActorInfo;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Faylo on 7/11/2017.
 */
public class Player extends Actor {
    private Rectangle bounds;
    private double vx, vy, accel;
    private float maxSpeed;
    private ArrayList<String> dirs;
    private String user;
    private enum direction {NORTH, EAST, SOUTH, WEST, NORTH_EAST, NORTH_WEST, SOUTH_EAST, SOUTH_WEST}
    private direction facing;

    public Player(World world, String user) {
        super(world, new Rectangle(500, 500, 32, 32), new ActorInfo("Player"), 100);
        this.bounds = new Rectangle(32, 32);
        this.vx = 0;
        this.vy = 0;
        this.maxSpeed = 100;
        this.accel = 100;
        this.dirs = new ArrayList<>();
        this.user = user;
        this.facing = direction.NORTH;
    }

    public ClientPlayer clientPlayer() {
        return new ClientPlayer(new Rectangle(bounds.x, bounds.y, bounds.width, bounds.height), user, health);
    }

    public void move() {
        if (alive) {
            if (dirs.contains("up"))
                vy -= accel;
                this.facing = direction.NORTH;
            if (dirs.contains("down"))
                vy += accel;
                this.facing = direction.SOUTH;
            if (dirs.contains("right"))
                vx += accel;
                this.facing = direction.EAST;
            if (dirs.contains("left"))
                vx -= accel;
                this.facing = direction.WEST;
            if (dirs.contains("right") && dirs.contains("up"))
                this.facing = direction.NORTH_EAST;
            if (dirs.contains("left") && dirs.contains("up"))
                this.facing = direction.NORTH_WEST;
            if (dirs.contains("right") && dirs.contains("down"))
                this.facing = direction.SOUTH_EAST;
            if (dirs.contains("left") && dirs.contains("down"))
                this.facing = direction.SOUTH_WEST;

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

            bounds.x = (int) (body.getPosition().x * Game.PPM) - bounds.width / 2;
            bounds.y = (int) (body.getPosition().y * Game.PPM) - bounds.height / 2;
        }
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void setDirs(String[] dirs) {
        this.dirs = new ArrayList<>(Arrays.asList(dirs));
    }

    public String getUser() {
        return user;
    }

    public Vec2 getLoc() {
        return body.getPosition();
    }
}
