package com.zombies.server.game.util.actor;

import com.zombies.server.game.util.actor.Actor;

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

    public Actor getActor() {
        return actor;
    }

    void setActor(Actor actor) {
        this.actor = actor;
    }

    @Override
    public String toString() {
        return id;
    }

    public boolean equals(String id) {
        return this.id.equals(id);
    }

}
