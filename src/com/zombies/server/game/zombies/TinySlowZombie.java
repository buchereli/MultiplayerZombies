package com.zombies.server.game.zombies;

import org.jbox2d.dynamics.World;

import java.awt.*;

/**
 * Created by buche on 7/20/2017.
 */
public class TinySlowZombie extends Zombie {

    public TinySlowZombie(Rectangle bounds, World w) {
        super(bounds, w);
        bounds.width = 5;
        bounds.height = 5;
        speed = 50;
        attackPower = 3;
    }
}
