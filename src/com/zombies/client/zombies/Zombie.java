package com.zombies.client.zombies;

import java.awt.*;

/**
 * Created by buche on 7/16/2017.
 */
public class Zombie {
    private Rectangle bounds;

    public void draw(Graphics g) {
        g.setColor(Color.GREEN);
        g.drawRect(bounds.x, bounds.y, bounds.width, bounds.height);
    }
}
