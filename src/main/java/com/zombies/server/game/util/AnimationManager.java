package com.zombies.server.game.util;

import com.zombies.server.game.players.Player;

import java.util.ArrayList;

public class AnimationManager {

    private Animation currentAnimation;
    private Animation walking = new Animation(200, 0, 0, new String[]{"player_rightarm", "player_noarm", "player_leftarm", "player_noarm"});
    private Animation running = new Animation(50, 0, 0, new String[]{"player_rightarm", "player_noarm", "player_leftarm", "player_noarm"});
    private Animation standing = new Animation(0, 0, 0, new String[]{"player_noarm"});

    public void setAnimation(Player p) {
        if(Math.abs(p.getVx()) < 1 && Math.abs(p.getVy()) < 1){
            currentAnimation = standing;
        }else if(Math.abs(p.getVx()) > 140 || Math.abs(p.getVy()) > 140) {
            currentAnimation = running;
        }else {
            currentAnimation = walking;
        }
    }

    public Animation getAnimation() {
        return currentAnimation;
    }

    public String getImage() {
        return currentAnimation.get();
    }

}
