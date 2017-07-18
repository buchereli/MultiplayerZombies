package com.zombies.client.communicator;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zombies.client.game.Client;
import com.zombies.client.game.player.Player;
import com.zombies.client.game.zombies.Zombie;
import com.zombies.server.LocalServerEndpoint;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class LocalClientEndpoint {

    private static final LocalServerEndpoint session = new LocalServerEndpoint();

    public void onMessage(String message) {
        JSONObject json = new JSONObject(message);
        if (json.getString("packetType").equals("gamePacket")) {
            Type type = new TypeToken<ArrayList<Zombie>>() {
            }.getType();
            Client.zombies = new Gson().fromJson(json.getString("zombies"), type);

            type = new TypeToken<ArrayList<Player>>() {
            }.getType();
            Client.players = new Gson().fromJson(json.getString("players"), type);
        }
    }

    public void sendMessage(String message) {
        session.onMessage(message);
    }


}
