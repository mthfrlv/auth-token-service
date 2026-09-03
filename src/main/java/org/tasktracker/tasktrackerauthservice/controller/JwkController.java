package org.tasktracker.tasktrackerauthservice.controller;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tasktracker.tasktrackerauthservice.controller.api.JwkApi;

import java.util.Map;

@RestController
@RequestMapping(value = "/api/internal/oauth2", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class JwkController implements JwkApi {

    private final RSAKey rsaKey;

    @Override
    @GetMapping("/jwk.json")
    public Map<String, Object> getJwk() {
        return new JWKSet(rsaKey.toPublicJWK()).toJSONObject();
    }
}
