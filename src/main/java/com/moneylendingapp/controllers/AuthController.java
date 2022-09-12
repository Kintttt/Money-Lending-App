package com.moneylendingapp.controllers;

import com.moneylendingapp.dto.requests.SignUpRequest;
import com.moneylendingapp.dto.responses.SignUpResponse;
import com.moneylendingapp.services.DefaultUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final DefaultUserService userService;

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public SignUpResponse registerUser(@Validated @RequestBody SignUpRequest signUp) {
        return userService.createUser(signUp);
    }
}
