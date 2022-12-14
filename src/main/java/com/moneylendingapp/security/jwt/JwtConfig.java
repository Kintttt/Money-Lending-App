package com.moneylendingapp.security.jwt;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "application.jwt")
@Getter
@Setter
@NoArgsConstructor
public class JwtConfig {

    private String secretKey;
    private int expiryDuration;

    public String getAuthorizationHeader() {
        return HttpHeaders.AUTHORIZATION;
    }
}
