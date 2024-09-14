package com.fresh.coding.sooatelapi.securities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@Setter
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {
    private String signingKey;
    private long expirationDate;
}
