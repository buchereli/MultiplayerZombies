package com.zombies.server.communicator;

import com.google.gson.Gson;
import com.zombies.server.game.Game;
import org.json.JSONObject;

import javax.websocket.Session;

/**
 * Created by buche on 7/19/2017.
 */
public class Communicator {

    public static final boolean LOCAL = true;
    private final static Gson gson = new Gson();
    private static Game game = new Game();

    static void processMessage(String message, Session session) {
        JSONObject json = new JSONObject(message);
        switch (json.getString("method")) {
            case "addPlayer":
                String user = json.getString("user");
                game.addPlayer(user);
                if (!LOCAL)
                    ServerGameEndpoint.setUser(user, session);
                break;
            case "setDirection":
                game.setDirection(json.getString("user"), gson.fromJson(json.getString("directions"), String[].class));
                break;
            case "newGame":
                game = new Game();
                break;
            default:
                System.out.println("UNKNOWN METHOD CALL... " + json.getString("method"));
                break;
        }
    }
}
