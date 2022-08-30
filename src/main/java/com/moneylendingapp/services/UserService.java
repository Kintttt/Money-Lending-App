package com.moneylendingapp.services;

import com.moneylendingapp.dto.SignUpRequest;

public interface UserService {
    String createUser(SignUpRequest signUpDto);
}
