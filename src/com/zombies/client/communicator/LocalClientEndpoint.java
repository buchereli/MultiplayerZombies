package com.zombies.client.communicator;

import com.zombies.server.LocalServerEndpoint;

public class LocalClientEndpoint {

    private static final LocalServerEndpoint session = new LocalServerEndpoint();

    public void onMessage(byte[] message) {
        Communicator.processMessage(message);
    }

    public void sendMessage(String message) {
        session.onMessage(message);
    }


}
