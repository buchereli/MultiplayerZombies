package com.zombies.client.game.hud;

import com.zombies.client.game.player.Player;

import java.awt.*;


public class HUD {
    public static Player player;

    public static void draw(Graphics g, int width) {
        g.setColor(Color.RED);
        g.fillRect(0, 0, width / 2, 40);
        g.setColor(Color.GREEN);
        if (player != null)
            g.fillRect(0, 0, (((int) player.getHealth()) * width)/200, 40);
    }

}
