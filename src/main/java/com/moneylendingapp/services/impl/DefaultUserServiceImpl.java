package com.moneylendingapp.services.impl;

import com.moneylendingapp.dto.responses.SignUpResponse;
import com.moneylendingapp.exceptions.BadRequestException;
import com.moneylendingapp.dto.requests.SignUpRequest;
import com.moneylendingapp.entities.User;
import com.moneylendingapp.enums.EmploymentStatus;
import com.moneylendingapp.repositories.UserRepository;
import com.moneylendingapp.services.DefaultUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class DefaultUserServiceImpl implements DefaultUserService {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public SignUpResponse createUser(SignUpRequest signUpDto) {

        ensureUserNameIsUnique(signUpDto.getUsername());
        User user = User.builder()
                    .username(signUpDto.getUsername())
                    .address(signUpDto.getAddress())
                    .dob(signUpDto.getDob())
                    .email(signUpDto.getEmail())
                    .employmentStatus(EmploymentStatus.get(signUpDto.getEmploymentStatus()))
                    .firstName(signUpDto.getFirstName())
                    .lastName(signUpDto.getLastName())
                    .password(
                            passwordEncoder.encode(signUpDto.getPassword()))
                    .gender(signUpDto.getGender())
                    .build();

            userRepo.save(user);

        SignUpResponse response = SignUpResponse.builder()
                .id(user.getId())
                .username(signUpDto.getUsername())
                .address(signUpDto.getAddress())
                .dob(signUpDto.getDob())
                .email(signUpDto.getEmail())
                .employmentStatus(signUpDto.getEmploymentStatus())
                .firstName(signUpDto.getFirstName())
                .lastName(signUpDto.getLastName())
                .gender(signUpDto.getGender())
                .build();

            return response;
    }

    private void ensureUserNameIsUnique(String username) {
        if(userRepo.existsByUsernameIgnoreCase(username)){
            throw new BadRequestException(String.format("Username: %s is taken",username));
        }
    }

}
