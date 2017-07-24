package com.zombies.server.game.util;

/**
 * Created by buche on 7/24/2017.
 */
public class ActorInfo {

    private String id;
    private Actor actor;

    public ActorInfo(String id) {
        this.id = id;
    }

    public String getID() {
        return id;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }

    public Actor getActor() {
        return actor;
    }

    @Override
    public String toString() {
        return id;
    }

    public boolean equals(String id) {
        return this.id.equals(id);
    }

}
