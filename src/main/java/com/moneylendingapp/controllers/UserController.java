package com.moneylendingapp.controllers;
import com.moneylendingapp.advice.ApiResponseEnvelope;
import com.moneylendingapp.dto.responses.UserModel;
import com.moneylendingapp.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/resend-confirmation-token")
    public ApiResponseEnvelope resendConfirmationToken() {
        return userService.resendConfirmationToken();
    }
}
