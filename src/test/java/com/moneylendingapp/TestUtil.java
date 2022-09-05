package com.moneylendingapp;

import com.moneylendingapp.dto.SignUpRequest;
import com.moneylendingapp.entities.User;

import static com.moneylendingapp.enums.EmploymentStatus.CONTRACT;

public class TestUtil {

    public static SignUpRequest newUserRequest () {
        return SignUpRequest.builder()
                .username("Kiint")
                .firstName("Temi")
                .lastName("Kint")
                .employmentStatus("Contract")
                .email("tops@gmail.com")
                .password("1234567")
                .build();
    }

    public static User mockedUser () {
        return User.builder()
                .username("Kiint")
                .firstName("Temi")
                .lastName("Kint")
                .employmentStatus(CONTRACT)
                .email("tops@gmail.com")
                .password("1234567")
                .build();
    }
}
