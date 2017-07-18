package com.zombies.server.game;

import com.zombies.server.game.players.Player;
import com.zombies.server.game.zombies.Zombie;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by buche on 7/18/2017.
 */
public class Game {
    private HashMap<String, Player> players;
    private ArrayList<Zombie> zombies;
    private ArrayList<String> dirs;
    private World world;

    public Game() {
        // Create a box2d world with no gravity
        world = new World(new Vec2(0, 0));

        // Create 100 zombies with random locations and add them to the world
        zombies = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            zombies.add(new Zombie(new Rectangle((int) (Math.random() * 1000), (int) (Math.random() * 1000),
                    10, 10), world));
        }

        players = new HashMap<>();
        dirs = new ArrayList<>();

        // Main game timer loop
        int time = 30;
        Timer timer = new Timer(time, ae -> {
            // Update player velocity vector
            for (Player player : players.values())
                player.move(dirs);

            // Update zombie velocity vector
            for (Zombie zed : zombies)
                zed.move(new ArrayList<>(players.values()));

            // Move all objects in world using box2d physics
            world.step(time * .001f, 6, 2);
        });
        timer.start();
    }

    // Add new player with username = user to world
    public void addPlayer(String user) {
        players.put(user, new Player(world));
    }

}
