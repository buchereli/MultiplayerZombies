package com.zombies.server;

import com.google.gson.Gson;
import com.zombies.client.communicator.LocalClientEndpoint;
import com.zombies.server.game.Game;
import com.zombies.server.game.util.Compressor;
import org.json.JSONObject;

public class LocalServerEndpoint {

    private final static LocalClientEndpoint client = new LocalClientEndpoint();
    private final static Game game = new Game();
    private final static Gson gson = new Gson();

    public static void broadcast(String user, String message) {
        client.onMessage(Compressor.compress(message));
    }

    public void onMessage(String message) {
        JSONObject json = new JSONObject(message);
        switch (json.getString("method")) {
            case "addPlayer":
                game.addPlayer(json.getString("user"));
                break;
            case "setDirection":
                game.setDirection(json.getString("user"), gson.fromJson(json.getString("directions"), String[].class));
                break;
            default:
                System.out.println("UNKNOWN METHOD CALL... " + json.getString("method"));
                break;
        }
    }

}
