package com.zombies.server.game.zombies;

import com.zombies.server.game.Game;
import com.zombies.server.game.players.Player;
import com.zombies.server.game.util.Character;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Faylo on 7/11/2017.
 */
public class Zombie extends Character {
    private Rectangle bounds;
    float speed;
    double attackPower;
    float sight;

    public Zombie(Rectangle bounds, World w) {
        super(bounds.x / Game.PPM, bounds.y / Game.PPM, bounds.width / Game.PPM, bounds.height / Game.PPM, w, "Zombie");
        this.bounds = bounds;
        speed = 50 / Game.PPM;
        attackPower = 5;
        sight = 250;
    }

    public ClientZombie clientZombie() {
        return new ClientZombie(new Rectangle(bounds.x, bounds.y, bounds.width, bounds.height));
    }

    public void move(ArrayList<Player> players) {
        Player player = closestPlayer(players);

        if (player != null) {
            Rectangle pBounds = player.getBounds();
            act(player);
            if (inSight(pBounds)) {
                if (pBounds.x > bounds.x) {
                    setVX(speed);
                } else if (pBounds.x < bounds.x) {
                    setVX(-speed);
                }

                if (pBounds.y > bounds.y) {
                    setVY(speed);
                } else if (pBounds.y < bounds.y) {
                    setVY(-speed);
                }
            } else {
                // Players in world but not in sight
                if (Math.random() < .015) {
                    setVX((float) Math.random() * speed - (speed / 2));
                    setVY((float) Math.random() * speed - (speed / 2));
                }
            }

            bounds.x = (int) (body.getPosition().x * Game.PPM);
            bounds.y = (int) (body.getPosition().y * Game.PPM);
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
        System.out.println(distance);
        return distance < sight;
    }

    // Get the distance from the zombie to player bounds
    private double getDistance(Rectangle pBounds) {
        return Math.sqrt(Math.pow((bounds.x - pBounds.x), 2) + Math.pow((bounds.y - pBounds.y), 2));
    }

    public boolean contains(Rectangle rect) {
        return bounds.intersects(rect);
    }

    private void act(Player player) {
        if (contains(player.getBounds())) {
            player.hit(attackPower);
        }
    }
}