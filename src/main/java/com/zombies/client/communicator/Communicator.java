package com.zombies.client.communicator;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zombies.client.game.player.Player;
import com.zombies.client.game.supplies.SupplyCache;
import com.zombies.client.game.zombies.Zombie;
import com.zombies.client.screens.Client;
import com.zombies.client.util.Compressor;
import org.json.JSONObject;

import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.WebSocketContainer;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.util.ArrayList;

public class Communicator {

    private static WebSocketContainer container;
    private static ClientGameEndpoint endpoint;
    private static LocalClientEndpoint localEndpoint;
    private static String user;

    public static ArrayList<Zombie> zombies = new ArrayList<>();
    public static ArrayList<Player> players = new ArrayList<>();
    public static ArrayList<SupplyCache> supplies = new ArrayList<>();
    private static boolean updated = false;

    static void processMessage(ByteBuffer message) {
        String msg = Compressor.decompress(message);
//        System.out.println("RECEIVED MSG FROM SERVER OF SIZE: " + message.remaining() + " - " + msg.length());

        if (msg.equals("pong"))
            System.out.println("pong");
        else {
            JSONObject json = new JSONObject(msg);
            if (json.getString("packetType").equals("gamePacket")) {
                Type type = new TypeToken<ArrayList<Zombie>>() {
                }.getType();
                zombies = new Gson().fromJson(json.getString("zombies"), type);

                type = new TypeToken<ArrayList<Player>>() {
                }.getType();
                players = new Gson().fromJson(json.getString("players"), type);

                type = new TypeToken<ArrayList<SupplyCache>>() {
                }.getType();
                supplies = new Gson().fromJson(json.getString("supplies"), type);

                updated = true;
            }
        }
    }

    public static void connect() {
        if (Client.LOCAL) {
            localEndpoint = new LocalClientEndpoint();
            localEndpoint.sendMessage("ping");
        } else {
            container = ContainerProvider.getWebSocketContainer();
            endpoint = new ClientGameEndpoint();

            try {
                container.connectToServer(endpoint, new URI("ws://javaclassserver.herokuapp.com/zombies"));
            } catch (DeploymentException | IOException | URISyntaxException e) {
                e.printStackTrace();
            }

            endpoint.sendMessage("ping");
        }
    }

    public static void joinGame(String userName) {
        user = userName;
        JSONObject message = new JSONObject();
        message.put("method", "addPlayer");
        message.put("user", user);

        if (Client.LOCAL)
            localEndpoint.sendMessage(message.toString());
        else
            endpoint.sendMessage(message.toString());
    }

    public static void setDirection(String[] directions) {
        JSONObject message = new JSONObject();
        message.put("method", "setDirection");
        message.put("user", user);
        message.put("directions", new Gson().toJson(directions));


        if (Client.LOCAL)
            localEndpoint.sendMessage(message.toString());
        else
            endpoint.sendMessage(message.toString());
    }

    public static void fireShot() {
        JSONObject message = new JSONObject();
        message.put("method", "fireShot");
        message.put("user", user);

        if (Client.LOCAL)
            localEndpoint.sendMessage(message.toString());
        else
            endpoint.sendMessage(message.toString());
    }

    public static void newGame() {
        JSONObject message = new JSONObject();
        message.put("method", "newGame");
        endpoint.sendMessage(message.toString());
    }

    public static void update() {
        if (updated) {
            Client.zombies = zombies;
            Client.players = players;
            Client.supplies = supplies;
            updated = false;
        }
    }

}
