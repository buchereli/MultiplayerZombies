package com.zombies.client.util;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Created by buche on 7/18/2017.
 */
public class Compressor {

    public static ByteBuffer compress(String data) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream(data.length());
        GZIPOutputStream gzip;
        ByteBuffer compressed = null;
        try {
            gzip = new GZIPOutputStream(bos);
            gzip.write(data.getBytes());
            gzip.close();
            compressed = ByteBuffer.wrap(bos.toByteArray());
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return compressed;
    }

    public static String decompress(ByteBuffer compressed) {
        ByteArrayInputStream bis = new ByteArrayInputStream(compressed.array());
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
