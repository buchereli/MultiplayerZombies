package com.zombies.server.game.zombies;

import com.zombies.server.game.util.Enums;

import java.awt.*;

/**
 * Created by buche on 7/16/2017.
 */
public class ClientZombie {

    private Rectangle bounds;
    private Enums.Direction facing;
    private double health, stamina;

    ClientZombie(Rectangle bounds, Enums.Direction facing, double health, double stamina) {
        this.bounds = bounds;
        this.facing = facing;
        this.health = health;
        this.stamina = stamina;
    }
}
