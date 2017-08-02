package com.zombies.server.game.util.actor;

import com.zombies.server.game.Game;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.dynamics.*;

import java.awt.*;

public abstract class Actor {

    protected Body body;

    public Actor(World world, Rectangle bounds, ActorInfo actorInfo) {
        this(world, bounds, actorInfo, false);
    }

    public Actor(World world, Rectangle bounds, ActorInfo actorInfo, boolean staticActor) {
        Float PPM = Game.PPM;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = staticActor ? BodyType.STATIC : BodyType.DYNAMIC;
        bodyDef.position.set(bounds.x / PPM, bounds.y / PPM);

        body = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox((bounds.width / PPM / 2.0f), (bounds.height / PPM / 2.0f));
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        if (!staticActor) {
            fixtureDef.density = 1.0f;
            fixtureDef.friction = 0.4f;
            fixtureDef.restitution = 0.0f;
        }

        actorInfo.setActor(this);
        body.createFixture(fixtureDef).setUserData(actorInfo);
    }

    public abstract void hit(double dmg);
}
