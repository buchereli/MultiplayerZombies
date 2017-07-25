package com.zombies.client.game.hud;

import com.zombies.client.game.player.Player;

import java.awt.*;


public class HUD {
    public static Player player;
    public static final Bar healthBar = new Bar(Color.red, Color.green, new Rectangle(100, 10));

    public static void draw(Graphics g, int height) {
        if (player != null)
            healthBar.draw(g, player.getHealth() / 100, new Point(10, height - 20));
    }
}
