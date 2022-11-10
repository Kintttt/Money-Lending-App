package com.moneylendingapp.controllers;

import com.moneylendingapp.advice.ApiResponseEnvelope;
import com.moneylendingapp.dto.requests.LoginRequest;
import com.moneylendingapp.dto.requests.SignUpRequest;
import com.moneylendingapp.dto.responses.LoginResponse;
import com.moneylendingapp.dto.responses.UserModel;
import com.moneylendingapp.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;


    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public UserModel registerUser(@Validated @RequestBody SignUpRequest signUp) {
        return userService.createUser(signUp);
    }

    @GetMapping(path = "confirm")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseEnvelope confirm(@RequestParam("token") String token) {
        return userService.confirmToken(token);
    }

    @PostMapping("/login")
    @ResponseStatus(value = HttpStatus.OK)
    public LoginResponse login(@RequestBody @Validated LoginRequest loginRequest) throws Exception {
        return userService.login(loginRequest);
    }

}
