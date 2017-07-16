package com.zombies.client.communicator;

import org.json.JSONObject;

public class LocalClientEndpoint {

    public void onMessage(String message) {
        JSONObject json = new JSONObject(message);
        if (json.getString("packet type").equals("game packet")) {
//                        Client.food = new Gson().fromJson(json.getString("food"), Point.class);
//                        Type snakesMap = new TypeToken<HashMap<String, Snake>>() {
//                        }.getType();
//                        Client.snakes = new Gson().fromJson(json.getString("snakes"), snakesMap);
        }
    }

    public void sendMessage(String message) {

    }


}
