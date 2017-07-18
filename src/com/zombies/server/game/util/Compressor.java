package com.zombies.server.game.util;

import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Created by buche on 7/18/2017.
 */
public class Compressor {

    public static byte[] compress(String data) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream(data.length());
        GZIPOutputStream gzip;
        byte[] compressed = new byte[0];
        try {
            gzip = new GZIPOutputStream(bos);
            gzip.write(data.getBytes());
            gzip.close();
            compressed = bos.toByteArray();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return compressed;
    }

    public static String decompress(byte[] compressed) {
        ByteArrayInputStream bis = new ByteArrayInputStream(compressed);
        GZIPInputStream gis;
        StringBuilder sb = new StringBuilder();
        try {
            gis = new GZIPInputStream(bis);
            BufferedReader br = new BufferedReader(new InputStreamReader(gis, "UTF-8"));
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();
            gis.close();
            bis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }
}
