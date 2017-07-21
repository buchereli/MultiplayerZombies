package com.zombies.server.game.util;

import com.zombies.server.game.Game;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;

import java.awt.*;

/**
 * Created by jack on 7/16/17.
 */
public class Character {
    protected Body body;

    public Character(World world, Rectangle bounds, String id) {
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

        body.createFixture(fixtureDef);
        body.setUserData(id);
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
}
