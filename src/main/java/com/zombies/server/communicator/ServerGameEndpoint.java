package com.zombies.server.communicator;

import com.zombies.server.game.util.Compressor;

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

    public static void broadcast(String user, String message) {
        if (clients.containsKey(user)) {
            Session session = clients.get(user);
            if (session != null && session.isOpen()) {
                try {
                    session.getBasicRemote().sendBinary(Compressor.compress(message));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else
                clients.remove(user);
        }
    }

    static void broadcast(String message) {
        for (Session session : sessions) {
            if (session != null && session.isOpen()) {
                try {
                    session.getBasicRemote().sendText(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static void setUser(String user, Session session) {
        clients.put(user, session);
        System.out.println("NEW USER " + user);
    }

    @OnOpen
    public void onCreateSession(Session session) {
        sessions.add(session);
        System.out.println("NEW CLIENT");
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        Communicator.processMessage(message, session);
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
}
