package com.moneylendingapp.services.impl;

import com.moneylendingapp.exceptions.BadRequestException;
import com.moneylendingapp.dto.SignUpRequest;
import com.moneylendingapp.entities.User;
import com.moneylendingapp.enums.EmploymentStatus;
import com.moneylendingapp.repositories.UserRepository;
import com.moneylendingapp.services.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;
    private PasswordEncoder passwordEncoder;

    @Override
    public String createUser(SignUpRequest signUpDto) {

        validateUsername(signUpDto.getUsername());
        User user = User.builder()
                    .username(signUpDto.getUsername())
                    .address(signUpDto.getAddress())
                    .dob(signUpDto.getDob())
                    .email(signUpDto.getEmail())
                    .employmentStatus(EmploymentStatus.get(signUpDto.getEmploymentStatus()))
                    .firstName(signUpDto.getFirstName())
                    .lastName(signUpDto.getLastName())
                    .password(passwordEncoder.encode(signUpDto.getPassword()))
                    .gender(signUpDto.getGender())
                    .build();


            userRepo.save(user);
            return "User saved successfully";
    }

    private void validateUsername(String username) {
        userRepo.findByUsername(username).ifPresent(user ->
        {throw new BadRequestException("Username: " +username + " is taken");
        }
        );

    }

}
