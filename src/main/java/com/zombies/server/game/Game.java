package com.zombies.server.game;

import com.google.gson.Gson;
import com.zombies.server.communicator.Communicator;
import com.zombies.server.communicator.LocalServerEndpoint;
import com.zombies.server.communicator.ServerGameEndpoint;
import com.zombies.server.game.players.ClientPlayer;
import com.zombies.server.game.players.Player;
import com.zombies.server.game.supplies.ClientSupplyCache;
import com.zombies.server.game.supplies.SupplyCache;
import com.zombies.server.game.util.Weapon;
import com.zombies.server.game.util.detection.CollisionCallbackHandler;
import com.zombies.server.game.util.detection.Ray;
import com.zombies.server.game.walls.Wall;
import com.zombies.server.game.zombies.ClientZombie;
import com.zombies.server.game.zombies.Zombie;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by buche on 7/18/2017.
 */
public class Game {
    public static final float PPM = 20.0f;
    public static final int MAP_SIZE = 5120;
    private CopyOnWriteArrayList<Player> players;
    private CopyOnWriteArrayList<Zombie> zombies;
    private ArrayList<SupplyCache> supplies;
    private World world;

    public Game() {
        // Create a box2d world with no gravity
        world = new World(new Vec2(0, 0));

        //Create a collision handler
        CollisionCallbackHandler callbackHandler = new CollisionCallbackHandler();
        //Attach it to our world
        world.setContactListener(callbackHandler);

        // Create 100 zombies with random locations and add them to the world
        zombies = new CopyOnWriteArrayList<>();
        for (int i = 0; i < 250; i++) {
            zombies.add(Zombie.normal(world));
        }
        for (int i = 0; i < 100; i++) {
            zombies.add(Zombie.fat(world));
        }

        // Walls surrounding world
        new Wall(world, new Rectangle(MAP_SIZE / 2, 0, MAP_SIZE, 5));
        new Wall(world, new Rectangle(0, MAP_SIZE / 2, 5, MAP_SIZE));
        new Wall(world, new Rectangle(MAP_SIZE, MAP_SIZE / 2, 5, MAP_SIZE));
        new Wall(world, new Rectangle(MAP_SIZE / 2, MAP_SIZE, MAP_SIZE, 5));

        supplies = new ArrayList<>();

        for(int i = 0; i < 10; i++) {
            supplies.add(SupplyCache.spawnSupplyCache(world));
        }


        players = new CopyOnWriteArrayList<>();

        // ServerMain game timer loop
        int time = 30;
        Timer timer = new Timer(time, ae -> {
            // Remove dead actors
            for (Player player : players)
                if (!player.isAlive())
                    player.destroy(world);
            players.removeIf(player -> !player.isAlive());
            for (Zombie zombie : zombies)
                if (!zombie.isAlive())
                    zombie.destroy(world);
            zombies.removeIf(zombie -> !zombie.isAlive());

            for (SupplyCache supply : supplies)
                if (!supply.isAlive())
                    supply.destroy(world);
            supplies.removeIf(supply -> !supply.isAlive());

            // Update player velocity vector
            for (Player player : players)
                player.update(30);

            // Update zombie velocity vector
            for (Zombie zed : zombies)
                zed.move(players);

            // Move all objects in world using box2d physics
            world.step(time * .001f, 6, 2);

            // Send message to clients
            ArrayList<ClientZombie> clientZombies = new ArrayList<>();
            for (Zombie zombie : zombies)
                clientZombies.add(zombie.clientZombie());

            ArrayList<ClientPlayer> clientPlayers = new ArrayList<>();
            for (Player player : players)
                clientPlayers.add(player.clientPlayer());

            ArrayList<ClientSupplyCache> clientSupplies = new ArrayList<>();
            for (SupplyCache supplyCache : supplies)
                clientSupplies.add(supplyCache.clientSupplyCache());

            JSONObject message = new JSONObject();
            message.put("packetType", "gamePacket");
            message.put("zombies", new Gson().toJson(clientZombies));
            message.put("players", new Gson().toJson(clientPlayers));
            message.put("supplies", new Gson().toJson(clientSupplies));
            for (Player player : players) {
                if (Communicator.LOCAL)
                    LocalServerEndpoint.broadcast(player.getUser(), message.toString());
                else
                    ServerGameEndpoint.broadcast(player.getUser(), message.toString());
            }
        });
        timer.start();
    }

    // Add new player with username = user to world
    public void addPlayer(String user) {
        for (Player player : players)
            if (player.getUser().equals(user))
                return;

        players.add(new Player(world, user));
    }

    public void setDirection(String user, String[] directions) {
        Player player = null;
        for (Player p : players)
            if (p.getUser().equals(user))
                player = p;

        if (player != null)
            player.setDirs(directions);

    }

    public void fireShot(String fromUser) {
        Player player = null;
        for (Player p : players)
            if (p.getUser().equals(fromUser))
                player = p;

        if (player != null) {
            if (player.fire()) {
                player.setShooting(true);
                Weapon weapon = player.weapon;
                Point endPoint = rotate(weapon.getRange(), player.getRotation());
                Ray.fireShot(world, player.getLoc(), new Vec2(player.getLoc().x + endPoint.x,
                        player.getLoc().y + endPoint.y), weapon.getDamage());
            }
        }
    }

    private Point rotate(int range, double radians) {
        Point xy = new Point();
        xy.x = (int) (range * Math.cos(radians));
        xy.y = (int) (range * Math.sin(radians));
        return xy;
    }

}
