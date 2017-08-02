package com.zombies.client.game.map;

import com.opencsv.CSVReader;
import com.zombies.client.util.ImageManager;

import java.awt.*;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * Created by buche on 8/1/2017.
 */
public class Map {

    private static final int TILE_SIZE = 640;
    private static String[][] tiles;

    public Map() {
        CSVReader reader;
        try {
            reader = new CSVReader(new FileReader(getClass().getClassLoader().getResource("map.csv").getFile()));
            List<String[]> myEntries = reader.readAll();

            tiles = myEntries.toArray(new String[myEntries.size()][]);

            System.out.println(tiles.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void draw(Graphics g, Point loc, Point shift) {
        int x = loc.x / TILE_SIZE;
        int y = loc.y / TILE_SIZE;

        for (int dx = -1; dx < 2; dx++)
            for (int dy = -1; dy < 2; dy++)
                if (x + dx >= 0 && y + dy >= 0 && x + dx < tiles.length && y + dy < tiles[0].length)
                    g.drawImage(ImageManager.get("tile_" + tiles[x + dx][y + dy]), (x + dx) * TILE_SIZE + shift.x,
                            (y + dy) * TILE_SIZE + shift.y, null);
    }

}
