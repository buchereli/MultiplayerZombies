package com.zombies.server.game.zombies;

import com.zombies.server.game.Game;
import com.zombies.server.game.players.Player;
import com.zombies.server.game.util.Actor;
import com.zombies.server.game.util.ActorInfo;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Faylo on 7/11/2017.
 */
public class Zombie extends Actor {
    private Rectangle bounds;
    private float speed;
    private double attackPower;
    private float sight;


    private Zombie(World world, Rectangle bounds, float speed, double attackPower, int sight) {
        super(world, bounds, new ActorInfo("Zombie"), 100);
        this.bounds = bounds;
        this.speed = speed;
        this.attackPower = 5;
        this.sight = 250;
    }

    public static Point getSpawn() {
        int x, y;
        x = (int) (Math.random() * 10000) - 5000;
        y = (int) (Math.random() * 10000) - 5000;
        Rectangle rect = new Rectangle(0, 0, 1000, 1000);
        while (rect.contains(x, y)) {
            x = (int) (Math.random() * 10000) - 5000;
            y = (int) (Math.random() * 10000) - 5000;
        }
        return new Point(x, y);
    }

    // Different types of zombies that do not modify methods
    public static Zombie normal(World w) {
        Point spawn = getSpawn();
        return new Zombie(w, new Rectangle(spawn.x, spawn.y, 32, 32), 50, 5, 250);
    }

    public static Zombie fat(World w) {
        Point spawn = getSpawn();
        return new Zombie(w, new Rectangle(spawn.x, spawn.y, 64, 64), 30, 25, 250);
    }

    public ClientZombie clientZombie() {
        return new ClientZombie(new Rectangle(bounds.x, bounds.y, bounds.width, bounds.height));
    }

    public void move(ArrayList<Player> players) {
        Player player = closestPlayer(players);

        if (player != null) {
            Rectangle pBounds = player.getBounds();
            if (inSight(pBounds)) {
                int d = (pBounds.x + pBounds.width / 2) - (bounds.x + bounds.width / 2);
                if (d > 0)
                    setVX(Math.min(d, speed));
                else if (d < 0)
                    setVX(Math.max(d, -speed));
                else
                    setVX(0);

                d = (pBounds.y + pBounds.height / 2) - (bounds.y + bounds.height / 2);
                if (d > 0)
                    setVY(Math.min(d, speed));
                else if (d < 0)
                    setVY(Math.max(d, -speed));
                else
                    setVY(0);
            } else {
                // Players in world but not in sight
                if (Math.random() < .015) {
                    setVX((float) Math.random() * speed - (speed / 2));
                    setVY((float) Math.random() * speed - (speed / 2));
                }
            }

            bounds.x = (int) (body.getPosition().x * Game.PPM) - bounds.width / 2;
            bounds.y = (int) (body.getPosition().y * Game.PPM) - bounds.height / 2;
        } else {
            // No players in world
            body.setLinearVelocity(new Vec2(0.0f, 0.0f));
        }
    }

    // Find closest player to zombie
    private Player closestPlayer(ArrayList<Player> players) {
        if (players.isEmpty())
            return null;

        Player closestPlayer = players.get(0);
        double distance = getDistance(closestPlayer.getBounds());

        for (Player player : players) {
            double tempDistance = getDistance(player.getBounds());
            if (tempDistance < distance) {
                distance = tempDistance;
                closestPlayer = player;
            }
        }

        return closestPlayer;
    }

    // Check if zombie can see player
    private boolean inSight(Rectangle pBounds) {
        double distance = getDistance(pBounds);
        return distance < sight;
    }

    // Get the distance from the zombie to player bounds
    private double getDistance(Rectangle pBounds) {
        return Math.sqrt(Math.pow((bounds.x - pBounds.x), 2) + Math.pow((bounds.y - pBounds.y), 2));
    }

    public boolean contains(Rectangle rect) {
        return bounds.intersects(rect);
    }
}