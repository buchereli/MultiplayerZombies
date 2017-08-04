package com.zombies.server.game.supplies;

import com.zombies.server.game.util.actor.Actor;
import com.zombies.server.game.util.actor.ActorInfo;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import java.awt.*;

public class SupplyCache extends Actor {

    private Rectangle bounds;
    private int bulletAmount;
    private double healAmount;
    private boolean alive;

    public SupplyCache(World world, Rectangle bounds, int bulletAmount, double healAmount) {
        super(world, bounds, new ActorInfo("SupplyCache"));
        this.bounds  = bounds;
        this.bulletAmount = bulletAmount;
        this.healAmount = healAmount;
        this.alive = true;

        // only works if this is here even though it's already in Actor class
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.STATIC;
        bodyDef.position.set(bounds.x, bounds.y);

        body = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox((bounds.width), (bounds.height));
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
    }

    public ClientSupplyCache clientSupplyCache() {
        return new ClientSupplyCache(new Rectangle(bounds.x, bounds.y, bounds.width, bounds.height), bulletAmount, healAmount);
    }

    private static Point getSpawn() {
        int x, y;
        x = (int) (Math.random() * 1000);
        y = (int) (Math.random() * 1000);
//        Rectangle rect = new Rectangle(0, 0, 1000, 1000);
//        while (rect.contains(x, y)) {
//            x = (int) (Math.random() * 1000);
//            y = (int) (Math.random() * 1000);
//        }
        return new Point(x, y);
    }

    public static SupplyCache spawnSupplyCache(World w) {
        Point spawn = getSpawn();
        return new SupplyCache(w, new Rectangle(spawn.x, spawn.y, 32, 32), 2, -10);
    }

    @Override
    public void hit(double dmg) {

    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(Boolean b){
        alive = b;
    }

    public void destroy(World world) {
        world.destroyBody(body);
    }

    public double getHealAmount() {
        return healAmount;
    }


}
