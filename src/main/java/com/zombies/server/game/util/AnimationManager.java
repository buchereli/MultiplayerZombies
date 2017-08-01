package com.zombies.server.game.util;

import com.zombies.server.game.players.Player;

public class AnimationManager {

    private Animation walking = new Animation(200, 0, 0, new String[]{"player_rightarm", "player_noarm", "player_leftarm", "player_noarm"});
    private Animation running = new Animation(50, 0, 0, new String[]{"player_rightarm", "player_noarm", "player_leftarm", "player_noarm"});
    private Animation standing = new Animation(0, 0, 0, new String[]{"player_noarm"});
    private Animation currentAnimation = standing;

    public Animation getAnimation() {
        return currentAnimation;
    }

    public void setAnimation(Player p) {
        if (currentAnimation.getFrame() == 0) {
            if (Math.abs(p.getVx()) < 1 && Math.abs(p.getVy()) < 1) {
                currentAnimation = standing;
            } else if (p.isRunning()) {
                currentAnimation = running;
            } else {
                currentAnimation = walking;
            }
        }
    }

    public String getImage() {
        return currentAnimation.get();
    }

}
