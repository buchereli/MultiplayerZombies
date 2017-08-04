package com.zombies.server.game.players;

import com.zombies.server.game.Game;
import com.zombies.server.game.util.AnimationManager;
import com.zombies.server.game.util.Enums;
import com.zombies.server.game.util.Weapon;
import com.zombies.server.game.util.actor.ActorInfo;
import com.zombies.server.game.util.actor.DynamicActor;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Faylo on 7/11/2017.
 */
public class Player extends DynamicActor {
    private Rectangle bounds;
    private double radian;
    private float acceleration, maxSpeed, hitTimer;
    private ArrayList<String> dirs;
    private String user;
    private Enums.Direction facing;
    private AnimationManager animations;
    private boolean shooting;
    public Weapon weapon = new Weapon(10, 0, 2000, 8, 8, 8, 1600);

    public Player(World world, String user) {
        super(world, new Rectangle((int) (Math.random() * 500), (int) (Math.random() * 500), 32, 32), new ActorInfo("Player"), 100, 100);
        this.bounds = new Rectangle(32, 32);
        this.maxSpeed = 200;
        this.acceleration = 100;
        this.dirs = new ArrayList<>();
        this.user = user;
        this.facing = Enums.Direction.NORTH;
        this.animations = new AnimationManager();
    }

    public ClientPlayer clientPlayer() {
        return new ClientPlayer(new Rectangle(bounds.x, bounds.y, bounds.width, bounds.height), user, health, stamina, this.facing, hitTimer > 0, animations.getImage(), shooting, weapon.getClip(), weapon.getClipSize());
    }

    public void update(int dt) {
        if (hitTimer > 0) {
            hitTimer -= dt;
        }

        move();
        setFacing();

        if (dirs.contains("r"))
            weapon.reload();

        weapon.update(dt);

        animations.setAnimation(this);
        animations.getAnimation().update(dt);
    }

    private void move() {
        // Only move if alive
        if (alive) {
            // Get velocities and apply friction
            float vx = body.getLinearVelocity().x * Game.PPM * .8f;
            float vy = body.getLinearVelocity().y * Game.PPM * .8f;

            // Update stamina
            boolean running = isRunning();
            if (running)
                stamina -= 2;
            else if (stamina < 100)
                stamina += .2;

            // Calculate speed
            float speed = running ? acceleration * 2 : acceleration;
            float max = running ? maxSpeed * 2 : maxSpeed;

            // Update vel
            if (dirs.contains("up"))
                vy -= speed;
            if (dirs.contains("down"))
                vy += speed;
            if (dirs.contains("right"))
                vx += speed;
            if (dirs.contains("left"))
                vx -= speed;

            if (vx > max)
                vx = max;
            else if (vx < -max)
                vx = -max;
            if (vy > max)
                vy = max;
            else if (vy < -max)
                vy = -max;

            setVelocities(vx, vy);

            // Update bounds
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

    public Vec2 getVel() {
        return body.getLinearVelocity();
    }

    public boolean isRunning() {
        return dirs.contains("TURBO SPEED") && stamina > 0;
    }

    public boolean fire() {
        if (weapon.fire()) {
            animations.setAnimationShooting();
            return true;
        }
        return false;
    }

    public void setShooting(boolean b) {
        shooting = b;
    }

    public double getHealth() {
        return health;
    }

    @Override
    public void hit(double dmg) {
        super.hit(dmg);
        hitTimer = 1000;
    }

    private void setFacing() {
        if (dirs.contains("right") && dirs.contains("up"))
            this.facing = Enums.Direction.NORTH_EAST;
        else if (dirs.contains("left") && dirs.contains("up"))
            this.facing = Enums.Direction.NORTH_WEST;
        else if (dirs.contains("right") && dirs.contains("down"))
            this.facing = Enums.Direction.SOUTH_EAST;
        else if (dirs.contains("left") && dirs.contains("down"))
            this.facing = Enums.Direction.SOUTH_WEST;
        else if (dirs.contains("up"))
            this.facing = Enums.Direction.NORTH;
        else if (dirs.contains("down"))
            this.facing = Enums.Direction.SOUTH;
        else if (dirs.contains("right"))
            this.facing = Enums.Direction.EAST;
        else if (dirs.contains("left"))
            this.facing = Enums.Direction.WEST;
    }

    public double getRotation() {
        if (this.facing == Enums.Direction.NORTH)
            radian = -Math.PI / 2;
        else if (this.facing == Enums.Direction.SOUTH)
            radian = -3 * Math.PI / 2;
        else if (this.facing == Enums.Direction.EAST)
            radian = 2 * Math.PI;
        else if (this.facing == Enums.Direction.WEST)
            radian = Math.PI;
        else if (this.facing == Enums.Direction.NORTH_EAST)
            radian = -Math.PI / 4;
        else if (this.facing == Enums.Direction.NORTH_WEST)
            radian = -3 * Math.PI / 4;
        else if (this.facing == Enums.Direction.SOUTH_WEST)
            radian = -5 * Math.PI / 4;
        else if (this.facing == Enums.Direction.SOUTH_EAST)
            radian = -7 * Math.PI / 4;

        return radian;
    }

    public boolean shooting(){
        return shooting;
    }

}
