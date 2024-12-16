package com.kleyton.cripto_prices_api.cripto_prices.utils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

public class SignatureUtil {

    public static String createSignature(String secretKey, String valueString) {
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
}
