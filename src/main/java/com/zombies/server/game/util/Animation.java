package com.zombies.server.game.util;

public class Animation {

    private int timeBetweenFrame;
    private int timePast;
    private int frame;
    private String[] imageString;

    public Animation(int timeBetweenFrame, int timePast, int frame, String[] imageString) {
        this.timeBetweenFrame = timeBetweenFrame;
        this.timePast = timePast;
        this.frame = frame;
        this.imageString = imageString;
    }

    public void update(int dt) {
        timePast += dt;
        if(timePast > timeBetweenFrame) {
            frame = (frame + 1) % imageString.length;
            timePast -= timeBetweenFrame;
        }
    }
    public String get() {
        return imageString[frame];
    }

    public int getFrame() {
        return frame;
    }

}