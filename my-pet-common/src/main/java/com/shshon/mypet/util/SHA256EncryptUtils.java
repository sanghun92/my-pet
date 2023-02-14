package com.shshon.mypet.util;

import com.shshon.mypet.common.exception.TextNotEncodableException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public abstract class SHA256EncryptUtils {

    private static final String MESSAGE_DIGEST_ALGORITHM = "SHA-256";
    private static final int BLOCK_SIZE = 16;

    private static final MessageDigest SHA256;

    static {
        try {
            SHA256 = MessageDigest.getInstance(MESSAGE_DIGEST_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static String encrypt(String text) {
        return encrypt(text, getRandomSalt());
    }

    private static String encrypt(String text, byte[] salt) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            byteArrayOutputStream.write(salt);
            byteArrayOutputStream.write(text.getBytes(StandardCharsets.UTF_8));

            byte[] encryptedText = SHA256.digest(byteArrayOutputStream.toByteArray());
            byteArrayOutputStream.reset();

            byteArrayOutputStream.write(salt);
            byteArrayOutputStream.write(encryptedText);
        } catch (IOException e) {
            throw new TextNotEncodableException(e);
        }

        return Base64Utils.encodeToString(byteArrayOutputStream.toByteArray());
    }

    public static boolean match(String source, String target) {
        byte[] bytes = Base64Utils.decodeFromString(target);
        byte[] salt = new byte[BLOCK_SIZE];
        System.arraycopy(bytes, 0, salt, 0, BLOCK_SIZE);
        return target.equals(encrypt(source, salt));
    }

    private static byte[] getRandomSalt() {
        byte[] salt = new byte[BLOCK_SIZE];
        try {
            SecureRandom.getInstance("SHA1PRNG").nextBytes(salt);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return salt;
    }
}
