package com.zombies.client.communicator;

import org.json.JSONObject;

import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.MessageHandler;
import javax.websocket.Session;
import java.io.IOException;

/**
 * DO NOT MODIFY THIS CLASS UNLESS YOU KNOW WHAT YOU ARE DOING!
 */
public class ClientGameEndpoint extends Endpoint {
    private Session session;

    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {
        this.session = session;

        this.session.addMessageHandler(new MessageHandler.Whole<String>() {
            @Override
            public void onMessage(String message) {
                System.out.println("Message received from server: " + message);
                JSONObject json = new JSONObject(message);
                if (json.getString("packet type").equals("game packet")) {
//                        Client.food = new Gson().fromJson(json.getString("food"), Point.class);
//                        Type snakesMap = new TypeToken<HashMap<String, Snake>>() {
//                        }.getType();
//                        Client.snakes = new Gson().fromJson(json.getString("snakes"), snakesMap);
                }
            }
        });
    }

    public void sendMessage(String message) {
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}