package com.moneylendingapp.controllers;
import com.moneylendingapp.dto.responses.UserModel;
import com.moneylendingapp.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    public UserModel getUserDetails(Principal user) {
        return userService.userDetails(user);
    }
}
