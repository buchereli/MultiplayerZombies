package com.zombies.server.game.walls;

import com.zombies.server.game.util.actor.Actor;
import com.zombies.server.game.util.actor.ActorInfo;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;

import java.awt.*;

/**
 * Created by buche on 8/1/2017.
 */
public class Wall extends Actor {

    public Wall(World world, Rectangle bounds) {
        this(world, bounds, new ActorInfo("Wall"));
    }

    private Wall(World world, Rectangle bounds, ActorInfo actorInfo) {
        super(world, bounds, actorInfo, true);
    }

    public Body body() {
        return body;
    }

    @Override
    public void hit(double dmg) {

    }
}
