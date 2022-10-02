package com.moneylendingapp.services;

import com.moneylendingapp.advice.ApiResponseEnvelope;
import com.moneylendingapp.dto.requests.SignUpRequest;
import com.moneylendingapp.dto.responses.UserModel;


public interface DefaultUserService {
    UserModel createUser(SignUpRequest signUpDto);

    ApiResponseEnvelope confirmToken(String token);
}
