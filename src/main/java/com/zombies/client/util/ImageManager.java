package com.zombies.client.util;

import com.zombies.client.screens.Client;

import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by buche on 7/25/2017.
 */
public class ImageManager {

    private static final HashMap<String, ImageRotations> images = new HashMap<>();

    static {
        try {
            images.put("player_rightarm", new ImageRotations(ImageIO.read(Client.class.getResourceAsStream("/player_rightarm.png")), true));
            images.put("player_leftarm", new ImageRotations(ImageIO.read(Client.class.getResourceAsStream("/player_leftarm.png")), true));
            images.put("player_noarm", new ImageRotations(ImageIO.read(Client.class.getResourceAsStream("/player_noarm.png")), true));
            images.put("zombie", new ImageRotations(ImageIO.read(Client.class.getResourceAsStream("/zombie.png")), true));
            images.put("tile_0", new ImageRotations(ImageIO.read(Client.class.getResourceAsStream("/tile_0.png")), false));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static BufferedImage get(String image) {
        return images.get(image).get();
    }

    public static BufferedImage get(String image, Enums.Direction direction) {
        return images.get(image).get(direction);
    }


    static class ImageRotations {

        private final HashMap<Enums.Direction, BufferedImage> images = new HashMap<>();
        private BufferedImage image;
        private boolean rotatable;

        private ImageRotations(BufferedImage image, boolean rotatable) {
            this.image = image;
            this.rotatable = rotatable;

            if (rotatable) {
                images.put(Enums.Direction.NORTH, image);
                images.put(Enums.Direction.NORTH_EAST, rotate(image, .25 * Math.PI));
                images.put(Enums.Direction.EAST, rotate(image, .5 * Math.PI));
                images.put(Enums.Direction.SOUTH_EAST, rotate(image, .75 * Math.PI));
                images.put(Enums.Direction.SOUTH, rotate(image, 1 * Math.PI));
                images.put(Enums.Direction.SOUTH_WEST, rotate(image, 1.25 * Math.PI));
                images.put(Enums.Direction.WEST, rotate(image, 1.50 * Math.PI));
                images.put(Enums.Direction.NORTH_WEST, rotate(image, 1.75 * Math.PI));
            }
        }

        public BufferedImage get() {
            return image;
        }

        public BufferedImage get(Enums.Direction direction) {
            if (rotatable)
                return images.get(direction);
            else
                return image;
        }

        private BufferedImage rotate(BufferedImage bufferedImage, double radians) {
            AffineTransform transform = new AffineTransform();
            transform.rotate(radians, bufferedImage.getWidth() / 2, bufferedImage.getHeight() / 2);
            AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);
            bufferedImage = op.filter(bufferedImage, null);
            return bufferedImage;
        }
    }


}
