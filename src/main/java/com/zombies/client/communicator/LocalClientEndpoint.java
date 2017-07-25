package com.zombies.client.communicator;

import com.zombies.server.communicator.LocalServerEndpoint;

import java.nio.ByteBuffer;

public class LocalClientEndpoint {

    private static final LocalServerEndpoint session = new LocalServerEndpoint();

    public void onMessage(ByteBuffer message) {
        Communicator.processMessage(message);
    }

    public void sendMessage(String message) {
        session.onMessage(message);
    }


}
