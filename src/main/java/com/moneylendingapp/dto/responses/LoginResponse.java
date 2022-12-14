package com.moneylendingapp.dto.responses;

import com.moneylendingapp.enums.Role;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {

    private Long userId;
    private Role role;
    private String token;
}
