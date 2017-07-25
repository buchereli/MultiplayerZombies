package com.zombies.client.game.zombies;

import java.awt.*;

/**
 * Created by buche on 7/16/2017.
 */
public class Zombie {
    private Rectangle bounds;

    public void draw(Graphics g, Point shift) {
        g.setColor(Color.GREEN);
        g.fillRect(bounds.x + shift.x, bounds.y + shift.y, bounds.width, bounds.height);
    }
}
