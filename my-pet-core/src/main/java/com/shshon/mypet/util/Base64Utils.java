package com.shshon.mypet.util;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public abstract class Base64Utils {

    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    /**
     * Base64-encode the given byte array.
     *
     * @param src the original byte array
     * @return the encoded byte array
     */
    public static byte[] encode(byte[] src) {
        if (src.length == 0) {
            return src;
        }
        return Base64.getEncoder().encode(src);
    }

    /**
     * Base64-decode the given byte array.
     *
     * @param src the encoded byte array
     * @return the original byte array
     */
    public static byte[] decode(byte[] src) {
        if (src.length == 0) {
            return src;
        }
        return Base64.getDecoder().decode(src);
    }

    /**
     * Base64-encode the given byte array to a String.
     *
     * @param src the original byte array
     * @return the encoded byte array as a UTF-8 String
     */
    public static String encodeToString(byte[] src) {
        if (src.length == 0) {
            return "";
        }
        return new String(encode(src), DEFAULT_CHARSET);
    }

    /**
     * Base64-decode the given byte array from an UTF-8 String.
     *
     * @param src the encoded UTF-8 String
     * @return the original byte array
     */
    public static byte[] decodeFromString(String src) {
        if (src.isEmpty()) {
            return new byte[0];
        }
        return decode(src.getBytes(DEFAULT_CHARSET));
    }
}
