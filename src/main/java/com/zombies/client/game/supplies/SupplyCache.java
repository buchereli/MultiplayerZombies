package com.zombies.client.game.supplies;

import java.awt.*;

public class SupplyCache {
    private Rectangle bounds;
    private int bulletAmount;
    private double healAmount;

    public void draw(Graphics g, Point shift) {
        int x = bounds.x + shift.x;
        int y = bounds.y + shift.y;

        g.setColor(Color.CYAN);
        g.fillRect(x, y, bounds.width, bounds.height);
    }
}
