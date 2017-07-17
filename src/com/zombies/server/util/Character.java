package com.zombies.server.util;

import com.badlogic.gdx.physics.box2d.*;

import java.awt.*;

/**
 * Created by jack on 7/16/17.
 */
public class Character {
    protected Body body;

    public Character(Rectangle bounds, World w, String id) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(bounds.x, bounds.y);

        body = w.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox((bounds.width / 2.0f), (bounds.height / 2.0f));
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f;
        fixtureDef.friction = .4f;
        fixtureDef.restitution = 0.0f;

        body.createFixture(fixtureDef);
        body.setUserData(id);
        shape.dispose();
    }

    public void setVelocities(float vx, float vy) {
        body.setLinearVelocity(vx, vy);
    }

    public void setVX(float vx) {
        body.setLinearVelocity(vx, body.getLinearVelocity().y);
    }

    public void setVY(float vy) {
        body.setLinearVelocity(body.getLinearVelocity().x, vy);
    }
}
