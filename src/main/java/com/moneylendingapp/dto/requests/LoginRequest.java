package com.moneylendingapp.dto.requests;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    @NotBlank(message = "Username field cannot be blank")
    private String username;

    @NotBlank(message = "Password field cannot be blank")
    private String password;
}
