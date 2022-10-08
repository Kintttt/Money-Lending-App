package com.moneylendingapp.controllers;

import com.moneylendingapp.dto.requests.LoginRequest;
import com.moneylendingapp.dto.requests.SignUpRequest;
import com.moneylendingapp.dto.responses.LoginResponse;
import com.moneylendingapp.dto.responses.UserModel;
import com.moneylendingapp.security.jwt.JwtUtil;
import com.moneylendingapp.services.DefaultUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final DefaultUserService userService;
    private final UserDetailsService myUserDetailsService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtTokenUtil;


    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public UserModel registerUser(@Validated @RequestBody SignUpRequest signUp) {
        return userService.createUser(signUp);
    }

    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public LoginResponse login(@RequestBody @Validated LoginRequest loginRequest) throws Exception {
        return userService.login(loginRequest);
//        try {
//            Authentication authenticate = authenticationManager
//                    .authenticate(
//                            new UsernamePasswordAuthenticationToken(
//                                    loginRequest.getUsername(), loginRequest.getPassword()
//                            )
//                    );
//
//            final UserDetails userDetails = myUserDetailsService.loadUserByUsername(loginRequest.getUsername());
//            final String jwtToken = jwtTokenUtil.generateToken(userDetails);
//            LoginResponse loginResponse = LoginResponse.builder().id(1L).token(jwtToken).build();
//            return  ResponseEntity.ok(loginResponse);
//
//        } catch (Exception ex) {
//            throw new Exception(ex.getMessage());
//        }
    }
}
