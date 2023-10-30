package org.jeugenedev.simbir.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

@Component
public class CryptoUtils {
    private final String ALG = "SHA-256";
    private final MessageDigest messageDigest = MessageDigest.getInstance(ALG);
    @Value("${password.secret}")
    private String passwordSecret;

    public CryptoUtils() throws NoSuchAlgorithmException {
    }

    public String encrypt(String source) {
        source = passwordSecret + source;
        return HexFormat.of().formatHex(messageDigest.digest(source.getBytes(StandardCharsets.UTF_8)));
    }
}
