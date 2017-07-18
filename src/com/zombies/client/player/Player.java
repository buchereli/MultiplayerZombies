package com.zombies.client.player;

import com.zombies.server.game.util.Character;
import org.jbox2d.dynamics.World;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Faylo on 7/11/2017.
 */
public class Player {
    private Rectangle bounds;

    public void draw(Graphics g) {
        g.setColor(Color.BLUE);
        g.drawRect(bounds.x, bounds.y, bounds.width, bounds.height);
    }
}
