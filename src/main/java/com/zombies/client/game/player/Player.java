package com.zombies.client.game.player;

import com.zombies.client.game.hud.Bar;
import com.zombies.client.screens.Client;
import com.zombies.client.util.Enums;
import com.zombies.client.util.ImageManager;

import java.awt.*;

public class Player {
    private final Bar bar = new Bar((Color.red), (Color.green), new Rectangle(16, 10));
    private Rectangle bounds;
    private String user;
    private double health, stamina;
    private Enums.Direction facing;
    private boolean hit;
    private String image;
    private boolean shooting;

    public void draw(Graphics g, Point shift) {
        int x = bounds.x + shift.x;
        int y = bounds.y + shift.y;

        g.setColor(Color.WHITE);
        g.drawRect(x, y, bounds.width, bounds.height);
        g.setColor(Color.RED);

        if (shooting) {
            g.drawImage(ImageManager.get(image, facing), x - 16, y - 16, null);
        } else {
            g.drawImage(ImageManager.get(image, facing), x, y, null);
        }

        g.setFont(new Font("default", Font.BOLD, 12));
        FontMetrics fontMetrics = g.getFontMetrics();
        int strWidth = fontMetrics.stringWidth(user);

        g.drawString(user, x - strWidth / 2 + bounds.width / 2, y - 10);


        if (!Client.user.equals(this.user)) {
            if (hit) {
                bar.draw(g, getHealth() / 100, new Point(x, y - 25));
            }

        }
    }


    public String getUser() {
        return user;
    }

    public Rectangle getBounds() {
        return new Rectangle(bounds.x, bounds.y, bounds.width, bounds.height);
    }

    public double getHealth() {
        return health;
    }

    public double getStamina() {
        return stamina;
    }

}

