package com.zombies.client.game.zombies;

import com.zombies.client.util.ImageManager;

import java.awt.*;

/**
 * Created by buche on 7/16/2017.
 */
public class Zombie {
    private Rectangle bounds;

    public void draw(Graphics g, Point shift) {
        g.drawImage(ImageManager.get("zombie"), bounds.x + shift.x, bounds.y + shift.y, null);
    }
}
