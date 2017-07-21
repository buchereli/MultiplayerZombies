package com.zombies.server.game.players;

import java.awt.*;

/**
 * Created by buche on 7/18/2017.
 */
public class ClientPlayer {

    private Rectangle bounds;
    private String user;
    private double health;

    ClientPlayer(Rectangle bounds, String user, double health) {
        this.bounds = bounds;
        this.user = user;
        this.health = health;
    }
}
