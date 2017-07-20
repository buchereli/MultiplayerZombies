package com.zombies.server.game.zombies;

import org.jbox2d.dynamics.World;

import java.awt.*;

/**
 * Created by Faylo on 7/20/2017.
 */
public class FatZombie extends Zombie {

    public FatZombie(Rectangle bounds, World w) {
        super(bounds, w);
        bounds.width = 20;
        bounds.height = 20;
        speed = 80;
    }
}
