package com.zombies.server.game.players;

import com.zombies.server.game.util.Enums;

import java.awt.*;

/**
 * Created by buche on 7/18/2017.
 */
public class ClientPlayer {

    private Rectangle bounds;
    private String user;
    public double health, stamina;
    private Enums.Direction facing;
    private boolean hit;

    ClientPlayer(Rectangle bounds, String user, double health, double stamina, Enums.Direction facing, boolean hit) {
        this.bounds = bounds;
        this.user = user;
        this.health = health;
        this.stamina = stamina;
        this.facing = facing;
        this.hit = hit;
    }
}
