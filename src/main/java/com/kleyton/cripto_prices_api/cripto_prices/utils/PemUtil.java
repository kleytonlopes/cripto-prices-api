package com.kleyton.cripto_prices_api.cripto_prices.utils;

import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;

import java.io.StringReader;
import java.security.PrivateKey;
import java.security.Security;

public class PemUtil {
    public static PrivateKey getPrivateKeyFromPEM(String apiSecret) throws Exception {
        Security.addProvider(new BouncyCastleProvider());

        try (PEMParser pemParser = new PEMParser(new StringReader(apiSecret.replace("\\n", "\n")))) {
            JcaPEMKeyConverter converter = new JcaPEMKeyConverter().setProvider("BC");
            Object object = pemParser.readObject();

            if (object instanceof PrivateKeyInfo) {
                return converter.getPrivateKey((org.bouncycastle.asn1.pkcs.PrivateKeyInfo) object);
            } else if (object instanceof PEMKeyPair) {
                return converter.getPrivateKey(((PEMKeyPair) object).getPrivateKeyInfo());
            } else {
                throw new Exception("Unexpected private key format");
            }
        }
    }
}
