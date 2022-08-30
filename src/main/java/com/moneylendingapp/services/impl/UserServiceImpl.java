package com.moneylendingapp.services.impl;

import com.moneylendingapp.exceptions.BadRequestException;
import com.moneylendingapp.dto.SignUpRequest;
import com.moneylendingapp.entities.User;
import com.moneylendingapp.enums.EmploymentStatus;
import com.moneylendingapp.repositories.UserRepository;
import com.moneylendingapp.services.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;


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
                    .password(signUpDto.getPassword())
                    .gender(signUpDto.getGender())
                    .build();


            userRepo.save(user);
            return "User saved successfully";
    }

    private void validateUsername(String username) {
        Optional<User> optionalUser = userRepo.findByUsername(username);
        if(optionalUser.isPresent()){
            throw new BadRequestException("Username: " +username +  " is taken");
        }
    }


}
