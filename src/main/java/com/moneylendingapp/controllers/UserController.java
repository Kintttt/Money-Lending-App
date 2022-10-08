package com.moneylendingapp.controllers;


import com.moneylendingapp.annotations.CurrentUser;
import com.moneylendingapp.dto.responses.UserModel;
import com.moneylendingapp.entities.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @GetMapping("/hi")
    public UserModel getUserDetails(@CurrentUser User user) {
        return UserModel.builder().id(user.getId())
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .address(user.getAddress())
                .build();
    }
}
