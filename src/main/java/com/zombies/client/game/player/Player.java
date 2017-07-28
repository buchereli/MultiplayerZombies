package com.zombies.client.game.player;

import com.zombies.client.game.hud.Bar;
import com.zombies.client.screens.Client;
import com.zombies.client.util.Enums;
import com.zombies.client.util.ImageManager;

import java.awt.*;

public class Player {
    private Rectangle bounds;
    private String user;
    double health, stamina;
    private Enums.Direction facing;
    private final Bar bar = new Bar((Color.red), (Color.green), new Rectangle(16, 10));

    public void draw(Graphics g, Point shift) {
        if (Client.user.equals(user)) {
            g.setColor(Color.BLUE);
        } else {

            g.setColor(Color.BLACK);
        }
//        g.fillRect(bounds.x + shift.x, bounds.y + shift.y, bounds.width, bounds.height);
        g.setColor(Color.WHITE);
        g.drawRect((bounds.x) + shift.x, (bounds.y) + shift.y, bounds.width, bounds.height);
        g.setColor(Color.RED);

        g.drawImage(ImageManager.get("player", facing), bounds.x + shift.x, bounds.y + shift.y, null);

        g.setFont(new Font("default", Font.BOLD, 12));
        FontMetrics fontMetrics = g.getFontMetrics();
        int strWidth = fontMetrics.stringWidth(user);

        g.drawString(user, bounds.x + shift.x - strWidth / 2 + bounds.width / 2, bounds.y + shift.y - 10);


        if(!Client.user.equals(this.user)) {
            bar.draw(g, getHealth() / 100, new Point(bounds.x + shift.x, bounds.y - 25 + shift.y));
        }


        //check if player is not player then draw bar
        //do the same for zombies if you have time tonight!

    }

    public String getUser() {
        return user;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public double getHealth() {
        return health;
    }

    public double getStamina() {
        return stamina;
    }
}

