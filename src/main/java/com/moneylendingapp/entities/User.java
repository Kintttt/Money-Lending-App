package com.moneylendingapp.entities;

import com.moneylendingapp.enums.EmploymentStatus;
import com.moneylendingapp.enums.Gender;
import com.moneylendingapp.enums.Role;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {

    private String firstName;
    private String lastName;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Gender gender;
    private Date dob;
    private String phoneNumber;
    private String address;

    @Enumerated(EnumType.STRING)
    private EmploymentStatus employmentStatus;

    @Enumerated(EnumType.STRING)
    private Role roles;


}
