package com.moneylendingapp.dto.responses;

import com.moneylendingapp.enums.EmploymentStatus;
import com.moneylendingapp.enums.Gender;
import com.moneylendingapp.enums.Role;
import lombok.*;
import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserModel {

    private Long id;

    private String firstName;

    private String lastName;

    private String username;

    private String email;

    private Gender gender;

    private Date dob;

    private String phoneNumber;

    private String address;

    private EmploymentStatus employmentStatus;

    private Role role;
}
