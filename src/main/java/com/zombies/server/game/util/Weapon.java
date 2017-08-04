package com.zombies.server.game.util;

/**
 * Created by Faylo on 8/1/2017.
 */
public class Weapon {
    public int clipSize;

    private double reloadSpeed;
    private double shotTimer;
    public double reloadTime;
    private double attackDMG;

    private int ammo, reloadSize;
    public int maxClipSize;
    private int range;

    public Weapon(double reloadTime, double attackDMG, double shotTimer, double reloadSpeed, int clipSize, int maxClipSize, int ammo, int range) {
        this.reloadTime = reloadTime;
        this.attackDMG = attackDMG;
        this.clipSize = clipSize;
        this.maxClipSize = maxClipSize;
        this.shotTimer = shotTimer;
        this.ammo = ammo;
        this.reloadSpeed = reloadSpeed;
        this.range = range;
    }

    public void reload() {
        reloadTime -= reloadSpeed;
        System.out.println("Reload time: "+reloadTime);
        if (reloadTime <= 0) {
            if (clipSize < maxClipSize) {
                reloadSize = maxClipSize - clipSize;
                if (reloadSize <= ammo) {
                    ammo -= reloadSize;
                    clipSize += reloadSize;
                }
            }
        }
    }

    public void update (int dt){
        if (clipSize == maxClipSize){
            reloadSpeed = dt;
        }
    }

//    public boolean cooldown() {
//        long sinceLastShot = System.currentTimeMillis() + 1000;
//        if (sinceLastShot >= shotTimer)
//            return true;
//        else
//            return false;
//    }

    public int getRange() {
        return range;
    }

    public double getDamage() {
        return attackDMG;
    }
}