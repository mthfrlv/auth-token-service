package org.tasktracker.tasktrackerauthservice.config.property;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.time.Duration;

@ConfigurationProperties("token")
@Validated
public record TokenProperty(@NotBlank String issuer,
                            @NotBlank String keyId,
                            @NotNull Duration accessTokenTtl,
                            @NotNull Duration refreshTokenTtl,
                            @NotBlank String algorithm,
                            @NotBlank String signatureAlgorithm,
                            @NotBlank String privateKey,
                            @NotBlank String publicKey) {
}
