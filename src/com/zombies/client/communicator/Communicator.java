package com.zombies.client.communicator;

import com.google.gson.Gson;
import com.zombies.client.Client;
import org.json.JSONObject;

import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.WebSocketContainer;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class Communicator {

    private static WebSocketContainer container;
    private static ClientGameEndpoint endpoint;
    private static ClientGameEndpoint localEndpoint;
    private static String userID;

    static void connect() {
        if (!Client.LOCAL) {
            container = ContainerProvider.getWebSocketContainer();
            endpoint = new ClientGameEndpoint();

            try {
                container.connectToServer(endpoint, new URI("ws://javaclassserver.herokuapp.com/zombies"));
            } catch (DeploymentException | IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        }
    }

    static void joinGame(String user) {
        userID = user;
        JSONObject message = new JSONObject();
        message.put("method", "addUser");
        message.put("userID", userID);

        if (Client.LOCAL)
            localEndpoint.sendMessage(message.toString());
        else
            endpoint.sendMessage(message.toString());
    }

    static void setLocation(Point location) {
        JSONObject message = new JSONObject();
        message.put("method", "setLocation");
        message.put("userID", userID);
        message.put("location", new Gson().toJson(location));


        if (Client.LOCAL)
            localEndpoint.sendMessage(message.toString());
        else
            endpoint.sendMessage(message.toString());
    }

}
