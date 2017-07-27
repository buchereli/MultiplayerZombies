package com.zombies.client.game.hud;

import java.awt.*;

/**
 * Created by Faylo on 7/25/2017.
 */
public class Bar {
    private Color r, c;
    private Rectangle bounds;

    public Bar(Color r, Color c, Rectangle bounds) {
        this.r = r;
        this.c = c;
        this.bounds = bounds;
    }

    public void draw(Graphics g, double percent, Point loc) {
        draw(g, percent, loc, new Point(0, 0));
    }

    public void draw(Graphics g, double percent, Point loc, Point shift) {

        g.setColor(r);
        g.fillRect(loc.x + shift.x, loc.y + shift.y, bounds.width + 20, bounds.height / 2);
        g.setColor(c);
        g.fillRect(loc.x + shift.x, loc.y + shift.y, (int) ((bounds.width + 20) * percent), bounds.height / 2);

    }
}