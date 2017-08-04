package com.zombies.client.game.hud;

import com.zombies.client.game.player.Player;

import java.awt.*;

public class HUD {
    private static final Bar healthBar = new Bar(Color.red, Color.green, new Rectangle(100, 10));
    private static final Bar staminaBar = new Bar(Color.GRAY, Color.BLUE, new Rectangle(100, 10));
    private static final Bar reloadBar = new Bar(Color.yellow, Color.RED, new Rectangle(100, 10));
    public static Player player;


    private static long nextSecond = System.currentTimeMillis() + 1000;
    private static int framesInCurrentSecond = 0;
    private static int fps = 0;

    public static void draw(Graphics g, int height, int width) {
        if (player != null) {
            healthBar.draw(g, player.getHealth() / 100, new Point(10, height - 20));
            staminaBar.draw(g, player.getStamina() / 100, new Point(10, height - 40));
            reloadBar.draw(g, player.getClip() / (double) player.getClipSize(), new Point(10, height-60));
        }

        fps();
        g.setColor(Color.green);
        g.drawString(fps + " fps", width - 40, 20);
    }

    private static void fps() {
        long currentTime = System.currentTimeMillis();
        if (currentTime > nextSecond) {
            nextSecond += 1000;
            fps = framesInCurrentSecond;
            framesInCurrentSecond = 0;
        }
        framesInCurrentSecond++;
    }
}
