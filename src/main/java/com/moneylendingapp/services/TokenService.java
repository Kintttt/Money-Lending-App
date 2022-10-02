package com.moneylendingapp.services;

import com.moneylendingapp.advice.ApiResponseEnvelope;
import com.moneylendingapp.entities.User;


public interface TokenService {

    void saveToken(User user);

    ApiResponseEnvelope confirmToken(String token);

}
