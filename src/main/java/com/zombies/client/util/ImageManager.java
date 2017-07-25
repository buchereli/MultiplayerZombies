package com.zombies.client.util;

import com.zombies.client.screens.Client;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by buche on 7/25/2017.
 */
public class ImageManager {

    private static final HashMap<String, BufferedImage> images = new HashMap<>();

    static {
        try {
            images.put("player", ImageIO.read(Client.class.getResourceAsStream("/player.png")));
            images.put("zombie", ImageIO.read(Client.class.getResourceAsStream("/zombie.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static BufferedImage get(String image) {
        return images.get(image);
    }


}
