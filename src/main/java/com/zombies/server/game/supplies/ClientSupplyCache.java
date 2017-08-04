package com.zombies.server.game.supplies;

import java.awt.*;

/**
 * Created by Abraham on 8/4/2017.
 */
public class ClientSupplyCache {
    private Rectangle bounds;
    private int bulletAmount;
    private double healAmount;

    ClientSupplyCache(Rectangle bounds, int bulletAmount, double healAmount) {
        this.bounds = bounds;
        this.bulletAmount = bulletAmount;
        this.healAmount = healAmount;
    }
}
