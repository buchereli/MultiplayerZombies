package com.zombies.client.game.player;

import com.zombies.client.Client;

import java.awt.*;

public class Player {
    private Rectangle bounds;
    private String user;

    public void draw(Graphics g) {
        if (Client.user.equals(user))
            g.setColor(Color.BLUE);
        else
            g.setColor(Color.BLACK);
        g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
    }
}
