package com.zombies.server.game.util.actor;

import com.zombies.server.game.Game;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import java.awt.*;

/**
 * Created by buche on 8/1/2017.
 */
public class DynamicActor extends Actor {

    protected double health, stamina;
    protected boolean alive;

    public DynamicActor(World world, Rectangle bounds, ActorInfo actorInfo, double health, double stamina) {
        super(world, bounds, actorInfo);
        this.health = health;
        this.stamina = stamina;
        this.alive = true;
    }

    public boolean isAlive() {
        return alive;
    }

    public void destroy(World world) {
        world.destroyBody(body);
    }

    public void setVelocities(float vx, float vy) {
        body.setLinearVelocity(new Vec2(vx / Game.PPM, vy / Game.PPM));
    }

    protected void setVX(float vx) {
        body.setLinearVelocity(new Vec2(vx / Game.PPM, body.getLinearVelocity().y));
    }

    protected void setVY(float vy) {
        body.setLinearVelocity(new Vec2(body.getLinearVelocity().x, vy / Game.PPM));
    }

    protected Vec2 getVel() {
        return body.getLinearVelocity();
    }

    public void hit(double dmg) {
        health -= dmg;
        if (health <= 0)
            alive = false;
    }
}
