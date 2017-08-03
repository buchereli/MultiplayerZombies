package com.zombies.server.game.util;

/**
 * Created by Faylo on 8/1/2017.
 */
public class Weapon {
    public int clipSize;

    private double reloadSpeed;
    private double shotTimer;
    private double reloadTime;
    private double attackDMG;

    private int ammo, reloadSize;
    private int maxClipSize;
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
        System.out.println("Reloading!");
        if (reloadTime <= 0) {
            reloadTime -= reloadSpeed;
            if (clipSize < maxClipSize) {
                reloadSize = maxClipSize - clipSize;
                reloadTime = reloadSpeed;
                ammo -= reloadSize;
                clipSize += reloadSize;
            }
        }
    }

    public void update (int dt){
        if (clipSize == maxClipSize){
            reloadTime -= dt;
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