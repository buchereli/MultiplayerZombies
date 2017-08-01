package com.zombies.server.game.zombies;

import com.zombies.server.game.Game;
import com.zombies.server.game.players.Player;
import com.zombies.server.game.util.Actor;
import com.zombies.server.game.util.ActorInfo;
import com.zombies.server.game.util.Enums;
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
    private float sight, burstSight;
    private Enums.Direction facing;

    private Zombie(World world, Rectangle bounds, float speed, double attackPower, int sight, int burstSight) {
        super(world, bounds, new ActorInfo("Zombie"), 100, 25);
        this.bounds = new Rectangle(32, 32);
        this.speed = speed;
        this.attackPower = 5;
        this.sight = 250;
        this.burstSight = 100;
        this.facing = Enums.Direction.NORTH;
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
        return new Zombie(w, new Rectangle(spawn.x, spawn.y, 32, 32), 50, 5, 250, 100);
    }

    public static Zombie fat(World w) {
        Point spawn = getSpawn();
        return new Zombie(w, new Rectangle(spawn.x, spawn.y, 32, 32), 30, 25, 250, 100);
    }

    public ClientZombie clientZombie() {
        return new ClientZombie(new Rectangle(bounds.x, bounds.y, bounds.width, bounds.height), this.facing, health, stamina);
    }

    public void move(ArrayList<Player> players) {
        Player player = closestPlayer(players);

        if (player != null) {
            Rectangle pBounds = player.getBounds();
            act(player);
            if (inSight(pBounds)&&!inBurstSight(pBounds)||(inBurstSight(pBounds)&&stamina<=0)) {
                int dy = pBounds.x - bounds.x;
                if (dy > 0) {
                    setVX(Math.min(dy, speed));
                } else if (dy < 0) {
                    setVX(Math.max(dy, -speed));
                } else
                    setVX(0);

                int dx = pBounds.y - bounds.y;
                if (dx > 0) {
                    setVY(Math.min(dx, speed));
                } else if (dx < 0) {
                    setVY(Math.max(dx, -speed));
                } else {
                    setVY(0);
                }
            }
            else if (inBurstSight(pBounds)&&stamina>0){
                int dy = pBounds.x - bounds.x;
                if (dy > 0) {
                    setVX(Math.min(dy, speed+50));
                } else if (dy < 0) {
                    setVX(Math.max(dy, -speed-50));
                } else
                    setVX(0);

                int dx = pBounds.y - bounds.y;
                if (dx > 0) {
                    setVY(Math.min(dx, speed+50));
                } else if (dx < 0) {
                    setVY(Math.max(dx, -speed-50));
                } else {
                    setVY(0);
                }
                runningZomb();
            }




            else {
                // Players in world but not in sight
                if (Math.random() < .015) {
                    setVX((float) Math.random() * speed - (speed / 2));
                    setVY((float) Math.random() * speed - (speed / 2));

                }
                if(stamina<25)
                restingZomb();
            }

            setDir(getVel());

            bounds.x = (int) (body.getPosition().x * Game.PPM) - bounds.width / 2;
            bounds.y = (int) (body.getPosition().y * Game.PPM) - bounds.height / 2;
        } else {
            // No players in world
            body.setLinearVelocity(new Vec2(0.0f, 0.0f));
        }
    }

    private void setDir(Vec2 vel) {
        float dx = vel.x;
        float dy = vel.y;

        if (Math.abs(dx) >= Math.abs(dy * 2)) {
            if (dx > 0)
                this.facing = Enums.Direction.EAST;
            if (dx < 0)
                this.facing = Enums.Direction.WEST;
        } else if (Math.abs(dy) >= Math.abs(dx * 2)) {
            if (dy > 0)
                this.facing = Enums.Direction.SOUTH;
            if (dy < 0)
                this.facing = Enums.Direction.NORTH;
        } else {
            if ((dy > 0) && (dx > 0)) {
                this.facing = Enums.Direction.SOUTH_EAST;
            }
            if ((dy > 0) && (dx < 0)) {
                this.facing = Enums.Direction.SOUTH_WEST;
            }
            if ((dy < 0) && (dx > 0)) {
                this.facing = Enums.Direction.NORTH_EAST;
            }
            if ((dy < 0) && (dx < 0)) {
                this.facing = Enums.Direction.NORTH_WEST;
            }
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
    private boolean inBurstSight(Rectangle pBounds){
        double distance = getDistance(pBounds);
        return distance< burstSight;
    }

    // Get the distance from the zombie to player bounds
    private double getDistance(Rectangle pBounds) {
        return Math.sqrt(Math.pow((bounds.x - pBounds.x), 2) + Math.pow((bounds.y - pBounds.y), 2));
    }



    public boolean contains(Rectangle rect) {
        return bounds.intersects(rect);
    }

    private void act(Player player) {
//        if (contains(player.getBounds())) {
//            player.hit(attackPower);
//        }
    }

    public double getHealth() {
        return health;
    }
    public double getStamina(){return stamina;}
}