package com.zombies.server.game.util;

import com.zombies.server.game.Game;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;

import java.awt.*;

/**
 * Created by jack on 7/16/17.
 */
public class Actor {

    protected double health, stamina;
    protected boolean alive;
    protected Body body;
    protected ActorInfo actorInfo;

    public Actor(World world, Rectangle bounds, ActorInfo actorInfo, double health, double stamina) {
        this.health = health;
        this.stamina = stamina;
        this.alive = true;
        this.actorInfo = actorInfo;

        Float PPM = Game.PPM;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DYNAMIC;
        bodyDef.position.set(bounds.x / PPM, bounds.y / PPM);

        body = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox((bounds.width / PPM / 2.0f), (bounds.height / PPM / 2.0f));
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.0f;

        actorInfo.setActor(this);
        body.createFixture(fixtureDef).setUserData(this.actorInfo);
    }

    public void setVelocities(float vx, float vy) {
        body.setLinearVelocity(new Vec2(vx / Game.PPM, vy / Game.PPM));
    }

    public void setVX(float vx) {
        body.setLinearVelocity(new Vec2(vx / Game.PPM, body.getLinearVelocity().y));
    }

    public void setVY(float vy) {
        body.setLinearVelocity(new Vec2(body.getLinearVelocity().x, vy / Game.PPM));
    }

    public Vec2 getVel() {
        return body.getLinearVelocity();
    }

    public void hit(double dmg) {
        health -= dmg;
        if (health <= 0)
            alive = false;
    }

    public void running() {
        stamina -= 2;

    }

    public void runningZomb() {
        stamina -= 1;
    }

    public void resting() {
        stamina += .2;
    }

    public void restingZomb() {
        stamina += .5;
    }

    public boolean isAlive() {
        return alive;
    }

    public void destroy(World world) {
        world.destroyBody(body);
    }
}
