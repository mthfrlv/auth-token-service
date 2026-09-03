package org.tasktracker.tasktrackerauthservice.config;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.tasktracker.tasktrackerauthservice.config.property.TokenProperty;
import org.tasktracker.tasktrackerauthservice.util.KeyParser;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Configuration
@RequiredArgsConstructor
public class TokenConfig {

    private final KeyParser<RSAPublicKey, RSAPrivateKey> keyParser;

    @Bean
    public KeyPair keyPair(TokenProperty tokenProperty){

        RSAPublicKey publicKey = keyParser.parsePublicKey(tokenProperty.publicKey(), tokenProperty.algorithm());
        RSAPrivateKey privateKey = keyParser.parsePrivateKey(tokenProperty.privateKey(), tokenProperty.algorithm());

        return new KeyPair(publicKey, privateKey);
    }

    @Bean
    public RSAKey rsaKey(KeyPair keyPair, TokenProperty tokenProperty){

        JWSAlgorithm signatureJwsAlgorithm = JWSAlgorithm.parse(tokenProperty.signatureAlgorithm());

        return new RSAKey.Builder((RSAPublicKey) keyPair.getPublic())
                .privateKey((RSAPrivateKey) keyPair.getPrivate())
                .algorithm(signatureJwsAlgorithm)
                .keyID(tokenProperty.keyId())
                .build();
    }

    @Bean
    public JwtEncoder jwtEncoder(RSAKey rsaKey) {

        JWKSource<SecurityContext> jwkSource = new ImmutableJWKSet<>(new JWKSet(rsaKey));

        return new NimbusJwtEncoder(jwkSource);
    }

    @Bean
    public JwtDecoder jwtDecoder(KeyPair keyPair) {
        return NimbusJwtDecoder
                .withPublicKey((RSAPublicKey) keyPair.getPublic())
                .build();
    }
}
