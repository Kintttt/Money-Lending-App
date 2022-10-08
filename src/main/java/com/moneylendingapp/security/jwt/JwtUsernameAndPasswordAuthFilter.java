//package com.moneylendingapp.security.jwt;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.security.Keys;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//import javax.crypto.SecretKey;
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.sql.Date;
//import java.time.LocalDate;
//
//@Slf4j
//@RequiredArgsConstructor
//public class JwtUsernameAndPasswordAuthFilter extends UsernamePasswordAuthenticationFilter {
//
//    private final AuthenticationManager authManager;
//    private final JwtConfig jwtConfig;
//    private final SecretKey secretKey;
//
//    @Override
//    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
//        String username = request.getParameter("username");
//        String password = request.getParameter("password");
//        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
//        return authManager.authenticate(authToken);
////        try {
////            UsernameAndPasswordAuthRequest authRequest = new ObjectMapper()
////                    .readValue(request.getInputStream(), UsernameAndPasswordAuthRequest.class);
////
////            Authentication authentication = new UsernamePasswordAuthenticationToken(
////                    authRequest.getUsername(),
////                    authRequest.getPassword()
////            );
////
////            return authManager.authenticate(authentication);
////        } catch (IOException e) {
////            log.error("Error is: "+e.getMessage());
////            throw new RuntimeException(e);
////        }
//    }
//
//    @Override
//    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
//                                            FilterChain chain, Authentication authResult) throws IOException, ServletException {
//
//        String token = Jwts.builder()
//                .setSubject(authResult.getName())
//                .claim("authorities", authResult.getAuthorities())
//                .setIssuedAt(new java.util.Date())
//                .setExpiration(Date.valueOf(LocalDate.now().plusDays(jwtConfig.getTokenExpirationAfterDays())))
//                .signWith(secretKey)
//                .compact();
//        log.info("token is {}", token);
//
//        response.addHeader(jwtConfig.getAuthorizationHeader(), "Bearer " + token);
//    }
//}
//
