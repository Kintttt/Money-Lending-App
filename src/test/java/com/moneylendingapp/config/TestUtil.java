package com.moneylendingapp.config;

import com.moneylendingapp.dto.requests.LoginRequest;
import com.moneylendingapp.dto.requests.SignUpRequest;
import com.moneylendingapp.entities.User;

import static com.moneylendingapp.enums.EmploymentStatus.CONTRACT;
import static com.moneylendingapp.enums.Role.*;

public class TestUtil {

    public static SignUpRequest newUserRequest () {
        return SignUpRequest.builder()
                .username("janedoe")
                .firstName("Jane")
                .lastName("Doe")
                .employmentStatus("Contract")
                .email("janedoe@gmail.com")
                .password("1234567")
                .build();
    }

    public static User mockedUser () {
        return User.builder()
                .username("janedoe")
                .firstName("Jane")
                .lastName("Doe")
                .employmentStatus(CONTRACT)
                .email("janedoe@gmail.com")
                .password("1234567")
                .role(USER)
                .build();
    }

    public static LoginRequest loginRequest () {
        return LoginRequest.builder()
                .username("janedoe")
                .password("1234567")
                .build();
    }
}
