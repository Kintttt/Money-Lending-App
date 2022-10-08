//package com.moneylendingapp.security.jwt;
//
//import io.jsonwebtoken.security.Keys;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.http.HttpHeaders;
//import org.springframework.stereotype.Component;
//
//import javax.crypto.SecretKey;
//
//@Component
//@ConfigurationProperties(prefix = "application.jwt")
//@Getter
//@Setter
//@NoArgsConstructor
//public class JwtConfig {
//
//    private String secretKey;
//    private int tokenExpirationAfterDays;
//
////    @Bean
////    public SecretKey getSecretKeyForSigning() {
////        return Keys.hmacShaKeyFor(secretKey.getBytes());
////    }
//
//    public String getAuthorizationHeader() {
//        return HttpHeaders.AUTHORIZATION;
//    }
//}
