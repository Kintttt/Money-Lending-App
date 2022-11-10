package com.moneylendingapp.util;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class ApplicationPropertyConfig {

    @Value("${token.verification.link}")
    private String tokenVerificationLink;

    @Value("${token.expiration.time}")
    private long tokenExpireAfter;

}
