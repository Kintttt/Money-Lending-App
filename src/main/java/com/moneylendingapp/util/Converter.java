package com.moneylendingapp.util;

import com.moneylendingapp.dto.responses.UserModel;
import com.moneylendingapp.entities.User;

public class Converter {

    public static UserModel userModelBuilder(User user) {
        return UserModel.builder()
                .id(user.getId())
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .address(user.getAddress())
                .role(user.getRole())
                .gender(user.getGender())
                .dob(user.getDob())
                .employmentStatus(user.getEmploymentStatus())
                .build();

    }
}
