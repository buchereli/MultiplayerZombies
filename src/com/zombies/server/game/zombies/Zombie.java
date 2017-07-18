package com.zombies.server.game.zombies;

import com.zombies.client.Client;
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
    private int speed;

    public Zombie(Rectangle bounds, World w) {
        super(bounds, w, "Zombie");
        this.bounds = bounds;
        speed = 100;
    }

    public ClientZombie clientZombie(){
        return new ClientZombie(new Rectangle((int) (body.getPosition().x), (int) (body.getPosition().y), bounds.width, bounds.height));
    }

    public void move(ArrayList<Player> players) {
        Player player = closestPlayer(players);

        if (player != null) {
            Rectangle pBounds = player.getBounds();
            act(player);
            if (inSight(pBounds)) {
                if (pBounds.x > body.getPosition().x) {
                    setVX(speed);
                }
                if (pBounds.x < body.getPosition().x) {
                    setVX(-speed);
                }
                if (pBounds.y > body.getPosition().y) {
                    setVY(speed);
                }
                if (pBounds.y < body.getPosition().y) {
                    setVY(-speed);
                }
            } else {
                body.setLinearVelocity(new Vec2(0.0f, 0.0f));
            }
            bounds = new Rectangle((int) body.getPosition().x, (int) body.getPosition().y, bounds.width, bounds.height);
        }
    }

    // Find closest player to zombie
    private Player closestPlayer(ArrayList<Player> players){
        if(players.isEmpty())
            return null;

        Player closestPlayer = players.get(0);
        double distance = getDistance(closestPlayer.getBounds());

        for(Player player : players){
            double tempDistance = getDistance(player.getBounds());
            if(tempDistance < distance){
                distance = tempDistance;
                closestPlayer = player;
            }
        }

        return closestPlayer;
    }

    // Check if zombie can see player
    private boolean inSight(Rectangle pBounds) {
        double distance = getDistance(pBounds);
        if (distance < 500)
            return true;
        else
            return false;
    }

    // Get the distance from the zombie to player bounds
    private double getDistance(Rectangle pBounds){
        return Math.sqrt(Math.pow((bounds.x - pBounds.x), 2) + Math.pow((bounds.y - pBounds.y), 2));
    }

    public boolean contains(Rectangle rect) {
        return bounds.intersects(rect);
    }

    private void act(Player player) {
        if (contains(player.getBounds())) {
            player.hit();
        }
    }
}