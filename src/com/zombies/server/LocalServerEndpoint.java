package com.zombies.server;

import com.zombies.client.communicator.LocalClientEndpoint;
import org.json.JSONObject;

public class LocalServerEndpoint {

    private final static LocalClientEndpoint client = new LocalClientEndpoint();

    public void onMessage(String message) {
        JSONObject jsonObject = new JSONObject(message);
        switch (jsonObject.getString("method")) {
            default:
                System.out.println("UNKNOWN METHOD CALL");
                break;
        }
    }

    public static void broadcast(String user, String message) {
        client.onMessage(message);
    }

}
