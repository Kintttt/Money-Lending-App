package com.moneylendingapp.services;

import com.moneylendingapp.dto.requests.LoginRequest;
import com.moneylendingapp.dto.requests.SignUpRequest;
import com.moneylendingapp.dto.responses.LoginResponse;
import com.moneylendingapp.dto.responses.UserModel;

import java.security.Principal;

public interface UserService {
    UserModel createUser(SignUpRequest signUpDto);
    LoginResponse login(LoginRequest request) throws Exception;
    UserModel userDetails(Principal principal);
}
