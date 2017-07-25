package com.zombies.server.communicator;

import com.zombies.client.communicator.LocalClientEndpoint;
import com.zombies.server.game.util.Compressor;

public class LocalServerEndpoint {

    private final static LocalClientEndpoint client = new LocalClientEndpoint();

    public static void broadcast(String user, String message) {
        client.onMessage(Compressor.compress(message));
    }

    public void onMessage(String message) {
        Communicator.processMessage(message, null);
    }

}
