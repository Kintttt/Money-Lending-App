package com.moneylendingapp.dto;

import com.moneylendingapp.enums.EmploymentStatus;
import com.moneylendingapp.enums.Gender;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequest {

    @NotBlank(message = "First name required")
    private String firstName;

    @NotBlank(message = "last name required")
    private String lastName;

    @NotBlank(message = "Username required")
    @Length(min = 5, max = 15, message = "Character length must be between 5 and 15")
    private String username;

    @Email(message = "Must be a valid email address", regexp = "^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w+)+$")
    private String email;

    @Size(min = 5, max = 15, message = "Character length must be between 5 and 15")
    private String password;
    private Gender gender;
    private Date dob;

    private String phoneNumber;
    private String address;

    @NotNull(message = "Employment status cannot be blank")
    private String employmentStatus;
}
