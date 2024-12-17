package com.kleyton.cripto_prices_api.cripto_prices.utils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class SignatureUtil {

    public static String createSignatureHex(String secretKey, String valueString) {
        try {
            Mac sha256Hmac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            sha256Hmac.init(secretKeySpec);

            byte[] hash = sha256Hmac.doFinal(valueString.getBytes(StandardCharsets.UTF_8));
            StringBuilder hashString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hashString.append('0');
                hashString.append(hex);
            }
            return hashString.toString();
        } catch (Exception e) {
            throw new RuntimeException("Error creating HMAC-SHA256 signature", e);
        }
    }

    public static String createSignatureBytesEncoded64(String secretKey, String valueString) {

        return Base64.getEncoder().encodeToString(createSignatureBytes(secretKey, valueString));
    }

    private static byte[] createSignatureBytes(String secretKey, String valueString) {
        return hexStringToByteArray(createSignatureHex(secretKey,valueString));
    }

    private static byte[] hexStringToByteArray(String hex) {
        int length = hex.length();
        byte[] data = new byte[length / 2];
        for (int i = 0; i < length; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
                    + Character.digit(hex.charAt(i+1), 16));
        }
        return data;
    }
}

