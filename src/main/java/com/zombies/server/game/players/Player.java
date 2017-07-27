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
    private float maxSpeed, turboSpeed;
    private ArrayList<String> dirs;
    private String user;
    private enum direction {NORTH, EAST, SOUTH, WEST, NORTH_EAST, NORTH_WEST, SOUTH_EAST, SOUTH_WEST}
    private direction facing;

    public Player(World world, String user) {
        super(world, new Rectangle(500, 500, 32, 32), new ActorInfo("Player"), 100, 100);
        this.bounds = new Rectangle(32, 32);
        this.vx = 0;
        this.vy = 0;
        this.maxSpeed = 100;
        this.turboSpeed = 50;
        this.accel = 100;
        this.dirs = new ArrayList<>();
        this.user = user;
        this.facing = direction.NORTH;
    }

    public ClientPlayer clientPlayer() {
        return new ClientPlayer(new Rectangle(bounds.x, bounds.y, bounds.width, bounds.height), user, health, stamina);
    }

    public void move() {
        if (alive) {
            if (dirs.contains("up")&&!dirs.contains("TURBO SPEED"))
                vy -= accel;
            this.facing = direction.NORTH;
            if (dirs.contains("down")&&!dirs.contains("TURBO SPEED"))
                vy += accel;
            this.facing = direction.SOUTH;
            if (dirs.contains("right")&&!dirs.contains("TURBO SPEED"))
                vx += accel;
            this.facing = direction.EAST;
            if (dirs.contains("left")&&!dirs.contains("TURBO SPEED"))
                vx -= accel;
            this.facing = direction.WEST;
            if (dirs.contains("up")&&dirs.contains("TURBO SPEED"))
                vy-=turboSpeed;
            if (dirs.contains("down")&&dirs.contains("TURBO SPEED"))
                vy+=turboSpeed;
            if (dirs.contains("right")&&dirs.contains("TURBO SPEED"))
                vx+=turboSpeed;
            if (dirs.contains("left")&&dirs.contains("TURBO SPEED"))
                vx-=turboSpeed;



            if (dirs.contains("right") && dirs.contains("up"))
                this.facing = direction.NORTH_EAST;
            if (dirs.contains("left") && dirs.contains("up"))
                this.facing = direction.NORTH_WEST;
            if (dirs.contains("right") && dirs.contains("down"))
                this.facing = direction.SOUTH_EAST;
            if (dirs.contains("left") && dirs.contains("down"))
                this.facing = direction.SOUTH_WEST;




            if (!dirs.contains("TURBO SPEED")&&vx > maxSpeed)
                vx = maxSpeed;
            if (!dirs.contains("TURBO SPEED")&&vx < -maxSpeed)
                vx = -maxSpeed;

            if (!dirs.contains("TURBO SPEED")&&vy > maxSpeed)
                vy = maxSpeed;
            if (!dirs.contains("TURBO SPEED")&&vy < -maxSpeed)
                vy = -maxSpeed;

            setVY((float) vy);
            setVX((float) vx);

            vx *= .8;
            vy *= .8;

            bounds.x = (int) (body.getPosition().x * Game.PPM) - bounds.width / 2;
            bounds.y = (int) (body.getPosition().y * Game.PPM) - bounds.height / 2;
        }
        if(dirs.contains("TURBO SPEED")&&stamina>0){
            running();
        }
        else if (stamina<100){
            resting();
        }
        else if (stamina<=0){
            dirs.remove("TURBO SPEED");
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
