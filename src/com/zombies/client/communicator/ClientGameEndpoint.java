package com.zombies.client.communicator;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zombies.client.game.Client;
import com.zombies.client.game.player.Player;
import com.zombies.client.game.zombies.Zombie;
import com.zombies.client.util.Compressor;
import org.json.JSONObject;

import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.MessageHandler;
import javax.websocket.Session;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * DO NOT MODIFY THIS CLASS UNLESS YOU KNOW WHAT YOU ARE DOING!
 */
public class ClientGameEndpoint extends Endpoint {
    private Session session;

    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {
        this.session = session;

        this.session.addMessageHandler((MessageHandler.Whole<byte[]>) Communicator::processMessage);
    }

    public void sendMessage(String message) {
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}