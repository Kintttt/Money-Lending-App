package com.moneylendingapp.controllers;

import com.moneylendingapp.dto.SignUpRequest;
import com.moneylendingapp.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;


@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@Validated @RequestBody SignUpRequest signUp) {
        return new ResponseEntity<>(userService.createUser(signUp), HttpStatus.CREATED);
    }
}
