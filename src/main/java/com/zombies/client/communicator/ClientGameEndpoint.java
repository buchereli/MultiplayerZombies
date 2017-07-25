package com.zombies.client.communicator;

import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.MessageHandler;
import javax.websocket.Session;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * DO NOT MODIFY THIS CLASS UNLESS YOU KNOW WHAT YOU ARE DOING!
 */
public class ClientGameEndpoint extends Endpoint {
    private Session session;

    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {
        this.session = session;

        this.session.addMessageHandler((MessageHandler.Whole<ByteBuffer>) Communicator::processMessage);
    }

    public void sendMessage(String message) {
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}