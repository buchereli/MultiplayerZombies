package com.zombies.client.zombies;

import com.zombies.client.player.Player;
import com.zombies.server.util.Character;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import java.awt.*;

/**
 * Created by Faylo on 7/11/2017.
 */
public class Zombie extends Character {
    Rectangle bounds;
    int speed;

    public Zombie(Rectangle bounds, World w){
        super(bounds, w, "Zombie");
        this.bounds = bounds;
        speed = 100;
    }

    public void draw(Graphics g){
        g.setColor(Color.green);
        g.fillRect((int)(body.getPosition().x), (int)(body.getPosition().y), bounds.width, bounds.height);
    }

    public void move(Player player){
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
        bounds = new Rectangle((int)body.getPosition().x, (int)body.getPosition().y, bounds.width, bounds.height);
    }

    public boolean contains(Rectangle rect){
        return bounds.intersects(rect);
    }

    private void act(Player player){
        if (contains(player.getBounds())){
            player.hit();
        }
    }
    private boolean inSight(Rectangle pBounds){
        double distance = Math.sqrt(Math.pow((bounds.x - pBounds.x), 2) + Math.pow((bounds.y - pBounds.y), 2));
        if (distance < 500)
            return true;
        else
            return false;
    }
}