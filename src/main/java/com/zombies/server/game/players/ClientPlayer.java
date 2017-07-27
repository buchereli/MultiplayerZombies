package com.zombies.server.game.players;

import java.awt.*;

/**
 * Created by buche on 7/18/2017.
 */
public class ClientPlayer {

    private Rectangle bounds;
    private String user;
    public double health, stamina;

    ClientPlayer(Rectangle bounds, String user, double health, double stamina) {
        this.bounds = bounds;
        this.user = user;
        this.health = health;
        this.stamina = stamina;
    }
}
