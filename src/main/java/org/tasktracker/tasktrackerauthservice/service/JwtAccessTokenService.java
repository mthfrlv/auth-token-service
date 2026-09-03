package org.tasktracker.tasktrackerauthservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import org.tasktracker.tasktrackerauthservice.config.property.TokenProperty;
import org.tasktracker.tasktrackerauthservice.security.model.CustomUserDetails;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class JwtAccessTokenService implements AccessTokenService<CustomUserDetails> {

    private final TokenProperty tokenProperty;
    private final JwtEncoder jwtEncoder;

    @Override
    public String generateAccess(CustomUserDetails object) {

        Instant issuedAt = Instant.now();
        Instant expiresAt = issuedAt.plus(tokenProperty.accessTokenTtl());

        JwsHeader jwsHeader = getJwsHeader(tokenProperty.keyId(), tokenProperty.signatureAlgorithm());
        JwtClaimsSet jwtClaimsSet = getJwtClaimsSet(issuedAt, expiresAt, tokenProperty.issuer(), object.getId(), object.getUsername());
        return getToken(jwsHeader, jwtClaimsSet);
    }

    private JwtClaimsSet getJwtClaimsSet(Instant issuedAt, Instant expiresAt, String issuer, Object userId, String username){
        return JwtClaimsSet.builder()
                .issuer(issuer)
                .subject(String.valueOf(userId))
                .claim("username", username)
                .issuedAt(issuedAt)
                .expiresAt(expiresAt)
                .build();
    }

    private JwsHeader getJwsHeader(String keyId, String signatureAlgorithm){
        return JwsHeader.with(SignatureAlgorithm.valueOf(signatureAlgorithm))
                .type("JWT")
                .keyId(keyId)
                .build();
    }

    private String getToken(JwsHeader jwsHeader, JwtClaimsSet jwtClaimsSet){
        return jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, jwtClaimsSet)).getTokenValue();
    }
}
