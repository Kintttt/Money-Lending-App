package com.moneylendingapp.services;

import com.moneylendingapp.dto.requests.SignUpRequest;
import com.moneylendingapp.dto.responses.SignUpResponse;

public interface DefaultUserService {
    SignUpResponse createUser(SignUpRequest signUpDto);
}