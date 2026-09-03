package org.tasktracker.tasktrackerauthservice.util;

import org.bouncycastle.util.encoders.Base64;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import static org.tasktracker.tasktrackerauthservice.constant.DefaultPemRsaHeader.*;

import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

@Component
@Validated
public class RSAKeyParser implements KeyParser<RSAPublicKey, RSAPrivateKey>{

    @Override
    public RSAPublicKey parsePublicKey(String key, String algorithm) {
        try {
            byte[] decoded = decodeKey(key);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decoded);
            KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
            return (RSAPublicKey) keyFactory.generatePublic(keySpec);
        } catch (Exception ex) {
            throw new IllegalStateException("Failed to parse public key", ex);
        }
    }

    @Override
    public RSAPrivateKey parsePrivateKey(String key, String algorithm) {
        try {
            byte[] decoded = decodeKey(key);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decoded);
            KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
            return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
        } catch (Exception ex) {
            throw new IllegalStateException("Failed to parse secret key", ex);
        }
    }

    private byte[] decodeKey(String key) {

        String normalized = key
                .replace(BEGIN_PRIVATE_KEY, "")
                .replace(END_PRIVATE_KEY, "")
                .replace(BEGIN_PUBLIC_KEY, "")
                .replace(END_PUBLIC_KEY, "")
                .replaceAll(INDENTATION, "");

        return Base64.decode(normalized);
    }
}
