//package com.moneylendingapp.security.jwt;
//
//import com.google.common.base.Strings;
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jws;
//import io.jsonwebtoken.JwtException;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.security.Keys;
//import lombok.AllArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import javax.crypto.SecretKey;
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//import java.util.stream.Collectors;
//
//@Slf4j
//@AllArgsConstructor
//public class JwtTokenVerifier extends OncePerRequestFilter {
//
//    private final JwtConfig jwtConfig;
//    private final SecretKey secretKey;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
//                                    FilterChain filterChain) throws ServletException, IOException {
//
//        String authorizationHeader = request.getHeader(jwtConfig.getAuthorizationHeader());
//        String token = authorizationHeader.replace("Bearer ", "");
//
//        System.out.println(authorizationHeader);
//
//        if(Strings.isNullOrEmpty(authorizationHeader) || !authorizationHeader.startsWith("Bearer ")) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        verifyToken(token);
//        filterChain.doFilter(request, response);
//    }
//
//    public String getToken(HttpServletRequest request) {
//        String authorizationHeader = request.getHeader(jwtConfig.getAuthorizationHeader());
//        String token = authorizationHeader.replace("Bearer ", "");
//        return token;
//    }
//
//    private void verifyToken(String token) {
//        try{
//            Jws<Claims> claimsJws = Jwts.parser()
//                    .setSigningKey(secretKey)
//                    .parseClaimsJws(token);
//
//            Claims body = claimsJws.getBody();
//            String username = body.getSubject();
//            var authorities = (List<Map<String, String>>) body.get("authorities");
//
//            Set<SimpleGrantedAuthority> grantedAuthorities = authorities.stream()
//                    .map(m -> new SimpleGrantedAuthority("authority"))
//                    .collect(Collectors.toSet());
//
//            Authentication authentication = new UsernamePasswordAuthenticationToken(
//                    username,
//                    null,
//                    grantedAuthorities
//            );
//
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        }catch (JwtException e){
//            log.error("Error occurred at: {}", e.getMessage());
//            throw new IllegalStateException("Token cannot be trusted");
//        }
//    }
//}
