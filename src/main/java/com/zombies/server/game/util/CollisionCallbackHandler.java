package com.zombies.server.game.util;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.contacts.Contact;

/**
 * Created by jack on 7/26/17.
 */
public class CollisionCallbackHandler implements ContactListener {

    //How much damage to do on Player -> Zombie hit
    private final static double PLAYER_DAMAGE = 3.0;


    @Override
    public void beginContact(Contact contact) {

    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold manifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse contactImpulse) {
        //Lets get the things that are colliding
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        //Lets get info about them
        ActorInfo actorInfoA = (ActorInfo) fixtureA.getUserData();
        ActorInfo actorInfoB = (ActorInfo) fixtureB.getUserData();

        //Are they both things with info about them?
        if(actorInfoA != null && actorInfoB != null) {
            //Get their id's
            String idA = actorInfoA.getID();
            String idB = actorInfoB.getID();

            //Check for either a Player -> Zombie Collision or the other way around
            if (idA.equals("Player") && idB.equals("Zombie")) {
                //We have a hit
                actorInfoA.getActor().hit(PLAYER_DAMAGE);
            } else if (idA.equals("Zombie") && idB.equals("Player")) {
                //We have the same hit just opposite
                actorInfoB.getActor().hit(PLAYER_DAMAGE);
            }
        }
    }
}
