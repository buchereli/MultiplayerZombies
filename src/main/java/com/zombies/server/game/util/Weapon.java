package com.zombies.server.game.util;

/**
 * Created by Faylo on 8/1/2017.
 */
public class Weapon {
    private double reloadSpeed;
    private double shotTimer;
    private double reloadTimer;
    private double attackDMG;

    private int ammo;
    private int clip, clipSize;
    private int range;

    public Weapon(double attackDMG, double shotTimer, double reloadSpeed, int clip, int clipSize, int ammo, int range) {
        this.reloadTimer = 0;
        this.attackDMG = attackDMG;
        this.clip = clip;
        this.clipSize = clipSize;
        this.shotTimer = shotTimer;
        this.ammo = ammo;
        this.reloadSpeed = reloadSpeed;
        this.range = range;
    }

    public void reload() {
        if (reloadTimer <= 0 && clip < clipSize) {
            int reloadSize = clipSize - clip;
            if (reloadSize <= ammo) {
                ammo -= reloadSize;
                clip = clipSize;
                reloadTimer = reloadSpeed;
            } else if (ammo > 0) {
                ammo = 0;
                clip += ammo;
                reloadTimer = reloadSpeed;
            }
        }
    }

    public void update(int dt) {
        if (reloadTimer > 0)
            reloadTimer -= dt;
    }

    public int getRange() {
        return range;
    }

    public double getDamage() {
        return attackDMG;
    }

    public boolean fire() {
        if (reloadTimer <= 0 && clip > 0) {
            clip--;
            return true;
        }
        return false;
    }
}