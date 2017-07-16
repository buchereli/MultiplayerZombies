package com.zombies.server;

import org.json.JSONObject;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * DO NOT MODIFY THIS CLASS UNLESS YOU KNOW WHAT YOU ARE DOING!
 */
@ServerEndpoint("/zombies")
public class ServerGameEndpoint {

    private static final ArrayList<Session> sessions = new ArrayList<>();
    private static final HashMap<String, Session> clients = new HashMap<>();

    @OnOpen
    public void onCreateSession(Session session) {
        sessions.add(session);
        System.out.println("NEW CLIENT");
    }

    @OnMessage
    public void onMessage(String message) {
        System.out.println("Message received: " + message);
        JSONObject jsonObject = new JSONObject(message);
        switch (jsonObject.getString("method")) {
            default:
                System.out.println("UNKNOWN METHOD CALL");
                break;
        }
    }

    @OnClose
    public void onCloseSession(Session session) {
        String userToRemove = "";
        for (String user : clients.keySet())
            if (clients.get(user) == session)
                userToRemove = user;
        if (!userToRemove.isEmpty())
            clients.remove(userToRemove);

        sessions.remove(session);
        System.out.println("USER " + userToRemove + " LEFT");
    }

    public static void broadcast(String user, String message) {
        if (clients.containsKey(user)) {
            Session session = clients.get(user);
            if (session != null && session.isOpen()) {
                try {
                    session.getBasicRemote().sendText(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else
                clients.remove(user);
        }
    }
}
