package com.zombies.server.communicator;

import com.google.gson.Gson;
import com.zombies.server.game.Game;
import com.zombies.server.game.util.AnimationManager;
import org.json.JSONObject;

import javax.websocket.Session;

/**
 * Created by buche on 7/19/2017.
 */
public class Communicator {

    public static final boolean LOCAL = true;
    private final static Gson gson = new Gson();
    private static Game game = null;

    static void processMessage(String message, Session session) {
        if (message.equals("ping")) {
            System.out.println("RECEIVED PING REQUEST");
            if (!LOCAL)
                ServerGameEndpoint.broadcast("pong");
        } else {
            JSONObject json = new JSONObject(message);
            switch (json.getString("method")) {
                case "addPlayer":
                    if (game == null)
                        game = new Game();
                    String user = json.getString("user");
                    game.addPlayer(user);
                    if (!LOCAL)
                        ServerGameEndpoint.setUser(user, session);
                    break;
                case "setDirection":
                    game.setDirection(json.getString("user"), gson.fromJson(json.getString("directions"), String[].class));
                    break;
                case "fireShot":
                    game.fireShot(json.getString("user"));
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
}
