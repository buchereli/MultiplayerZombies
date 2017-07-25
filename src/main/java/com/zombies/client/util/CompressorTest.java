package com.zombies.client.util;

/**
 * Created by buche on 7/18/2017.
 */
public class CompressorTest {
    @org.junit.Test
    public void compressorTest() throws Exception {
        String string = "test";
        assert Compressor.decompress(Compressor.compress(string)).equals(string);
    }

    @org.junit.Test
    public void stringConversionTest() throws Exception {
        String string = "test";
        assert new String(string.getBytes()).equals(string);
    }

}