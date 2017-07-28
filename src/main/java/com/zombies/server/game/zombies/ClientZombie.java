package com.zombies.server.game.zombies;

import com.zombies.server.game.util.Enums;

import java.awt.*;

/**
 * Created by buche on 7/16/2017.
 */
public class ClientZombie {
    private Rectangle bounds;
    private Enums.Direction facing;

    ClientZombie(Rectangle bounds, Enums.Direction facing) {
        this.bounds = bounds;
        this.facing = facing;
    }
}
