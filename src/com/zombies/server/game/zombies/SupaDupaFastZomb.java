package com.zombies.server.game.zombies;

import org.jbox2d.dynamics.World;

import java.awt.*;

/**
 * Created by derekho01778 on 7/20/17.
 */
public class SupaDupaFastZomb extends Zombie{

    public SupaDupaFastZomb(Rectangle bounds, World w) {
        super(bounds, w);
        speed = 500;
        bounds.width = 8;
        bounds.height = 8;
        attackPower = 2;
    }
}
