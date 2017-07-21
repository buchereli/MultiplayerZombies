package com.zombies.server.game.util;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;

/**
 * Created by jack on 7/16/17.
 */
public class Character {
    protected Body body;

    public Character(float x, float y, float width, float height, World w, String id) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DYNAMIC;
        bodyDef.position.set(x, y);

        body = w.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox((width / 2.0f), (height / 2.0f));
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.0f;

        body.createFixture(fixtureDef);
        body.setUserData(id);
    }

    public void setVelocities(float vx, float vy) {
        body.setLinearVelocity(new Vec2(vx, vy));
    }

    public void setVX(float vx) {
        body.setLinearVelocity(new Vec2(vx, body.getLinearVelocity().y));
    }

    public void setVY(float vy) {
        body.setLinearVelocity(new Vec2(body.getLinearVelocity().x, vy));
    }
}
