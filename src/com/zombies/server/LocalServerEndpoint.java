package com.zombies.server;

import com.zombies.client.communicator.LocalClientEndpoint;
import com.zombies.server.game.Game;
import org.json.JSONObject;

public class LocalServerEndpoint {

    private final static LocalClientEndpoint client = new LocalClientEndpoint();
    private final static Game game = new Game();

    public static void broadcast(String user, String message) {
        client.onMessage(message);
    }

    public void onMessage(String message) {
        JSONObject jsonObject = new JSONObject(message);
        switch (jsonObject.getString("method")) {
            case "addPlayer":
                game.addPlayer(jsonObject.getString("user"));
                break;
            default:
                System.out.println("UNKNOWN METHOD CALL");
                break;
        }
    }

}
