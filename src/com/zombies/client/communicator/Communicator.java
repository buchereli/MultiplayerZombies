package com.zombies.client.communicator;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zombies.client.game.Client;
import com.zombies.client.game.player.Player;
import com.zombies.client.game.zombies.Zombie;
import com.zombies.client.util.Compressor;
import org.json.JSONObject;

import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.WebSocketContainer;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class Communicator {

    private static WebSocketContainer container;
    private static ClientGameEndpoint endpoint;
    private static LocalClientEndpoint localEndpoint;
    private static String user;

    static void processMessage(byte[] message) {
        System.out.println("RECEIVED MSG FROM SERVER OF SIZE: " + message.length);

        JSONObject json = new JSONObject(Compressor.decompress(message));
        if (json.getString("packetType").equals("gamePacket")) {
            Type type = new TypeToken<ArrayList<Zombie>>() {
            }.getType();
            Client.zombies = new Gson().fromJson(json.getString("zombies"), type);

            type = new TypeToken<ArrayList<Player>>() {
            }.getType();
            Client.players = new Gson().fromJson(json.getString("players"), type);
        }
    }

    public static void connect() {
        if (Client.LOCAL) {
            localEndpoint = new LocalClientEndpoint();
        } else {
            container = ContainerProvider.getWebSocketContainer();
            endpoint = new ClientGameEndpoint();

            try {
                container.connectToServer(endpoint, new URI("ws://javaclassserver.herokuapp.com/zombies"));
            } catch (DeploymentException | IOException | URISyntaxException e) {
                e.printStackTrace();
            }
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

}
