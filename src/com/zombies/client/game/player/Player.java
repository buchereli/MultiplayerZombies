package com.zombies.client.game.player;

import com.zombies.client.Client;

import java.awt.*;

public class Player {
    private Rectangle bounds;
    private String user;

    public void draw(Graphics g, Point shift) {
        if (Client.user.equals(user))
            g.setColor(Color.BLUE);
        else
            g.setColor(Color.BLACK);
        g.fillRect(bounds.x + shift.x, bounds.x + shift.y, bounds.width, bounds.height);
    }

    public String getUser() {
        return user;
    }
    public Rectangle getBounds(){
        return bounds;
    }
}

