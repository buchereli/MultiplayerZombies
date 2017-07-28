package com.zombies.client.game.zombies;

import com.zombies.client.game.hud.Bar;
import com.zombies.client.util.ImageManager;

import java.awt.*;

/**
 * Created by buche on 7/16/2017.
 */
public class Zombie {
    private Rectangle bounds;
    double health = 100;
    double stamina = 25;
    public static final Bar zombieBar = new Bar(Color.red, Color.green, new Rectangle(40, 10));
    public static final Bar burstBar = new Bar(Color.GRAY, Color.BLUE, new Rectangle(10, 10));

    public void draw(Graphics g, Point shift) {
        g.setColor(Color.WHITE);
        g.drawRect((bounds.x) + shift.x, (bounds.y) + shift.y, bounds.width, bounds.height);
        g.drawImage(ImageManager.get("zombie"), bounds.x + shift.x, bounds.y + shift.y, null);



        zombieBar.draw(g, health / 100, new Point(bounds.x - 13 + shift.x, bounds.y - 12 + shift.y));


        burstBar.draw(g, stamina/25, new Point (bounds.x+3+shift.x, bounds.y-17+shift.y));

    }

}
