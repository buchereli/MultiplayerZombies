package com.zombies.server.game.util;

import org.jbox2d.callbacks.RayCastCallback;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.World;

/**
 * Created by buche on 7/24/2017.
 */
public class Ray {

    private static Vec2 collisionPoint;
    private static Fixture collisionObject;
    private static float closestFraction;

    public static void fireShot(World world, Vec2 fromPoint, Vec2 toPoint, double dmg) {
        collisionPoint = new Vec2();
        collisionObject = null;
        closestFraction = 1.0f;

        RayCastCallback callback = (fixture, point, normal, fraction) -> {
            if (!fixture.getUserData().equals("Player")) {
                if (fraction < closestFraction) {
                    closestFraction = fraction;
                    collisionPoint.set(point);
                    collisionObject = fixture;
                }
            }

            return 1;
        };

        world.raycast(callback, fromPoint, toPoint);

        if (collisionObject != null) {
            System.out.println("A " + collisionObject.getUserData() + " has been shot");
            ActorInfo info = (ActorInfo) collisionObject.getUserData();
            info.getActor().hit(dmg);
        }
    }

}
