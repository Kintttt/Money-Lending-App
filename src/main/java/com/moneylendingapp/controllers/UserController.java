package com.moneylendingapp.controllers;


import com.moneylendingapp.annotations.CurrentUser;
import com.moneylendingapp.dto.responses.UserModel;
import com.moneylendingapp.entities.User;
import com.moneylendingapp.util.Converter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @GetMapping
    public UserModel getUserDetails(@CurrentUser User user) {
        return Converter.userModelBuilder(user);
    }
}
