package com.zombies.server.game.util;

import com.zombies.server.game.players.Player;

public class AnimationManager {

    private Animation walking = new Animation(200, 0, 0, new String[]{"player_rightarm", "player_noarm", "player_leftarm", "player_noarm"});
    private Animation running = new Animation(50, 0, 0, new String[]{"player_rightarm", "player_noarm", "player_leftarm", "player_noarm"});
    private Animation standing = new Animation(0, 0, 0, new String[]{"player_noarm"});
    private Animation shooting = new Animation(50, 0, 1, new String[]{"player_pistol", "player_pistol", "player_pistolfire"});
    private Animation currentAnimation = standing;

    public Animation getAnimation() {
        return currentAnimation;
    }

    public void setAnimation(Player p, boolean isPlayerShooting) {
        if(isPlayerShooting) {
            p.setShooting(true);
            currentAnimation = shooting;
        }else {
            p.setShooting(false);
            if (currentAnimation.getFrame() == 0 || currentAnimation == shooting) {
                if (Math.abs(p.getVel().x) < 1 && Math.abs(p.getVel().y) < 1) {
                    currentAnimation = standing;
                } else if (p.isRunning()) {
                    currentAnimation = running;
                } else {
                    currentAnimation = walking;
                }
            }
        }
    }

    public void setAnimationShooting(){
        currentAnimation = shooting;
        if(currentAnimation.getFrame() == 0) {
            currentAnimation = standing;
        }
    }

    public String getImage() {
        return currentAnimation.get();
    }

}
