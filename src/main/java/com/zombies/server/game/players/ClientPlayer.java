package com.zombies.server.game.players;

import com.zombies.server.game.util.Enums;

import java.awt.*;

/**
 * Created by buche on 7/18/2017.
 */
public class ClientPlayer {

    public double health, stamina;
    private Rectangle bounds;
    private String user;
    private Enums.Direction facing;
    private boolean hit;
    private String image;
    private boolean shooting;

    private int clip, clipSize;

    ClientPlayer(Rectangle bounds, String user, double health, double stamina, Enums.Direction facing, boolean hit, String image, boolean shooting, int clip, int maxClip) {
        this.bounds = bounds;
        this.user = user;
        this.health = health;
        this.stamina = stamina;

        this.facing = facing;
        this.hit = hit;
        this.image = image;
        this.shooting = shooting;
        this.clip = clip;
        this.clipSize = maxClip;
    }


}
