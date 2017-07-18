package com.zombies.client.game.player;

import java.awt.*;

public class Player {
    private Rectangle bounds;

    public void draw(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
    }
}
