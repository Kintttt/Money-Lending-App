package com.moneylendingapp.services.impl;

import com.moneylendingapp.dto.requests.LoginRequest;
import com.moneylendingapp.dto.responses.LoginResponse;
import com.moneylendingapp.dto.responses.UserModel;
import com.moneylendingapp.exceptions.BadRequestException;
import com.moneylendingapp.dto.requests.SignUpRequest;
import com.moneylendingapp.entities.User;
import com.moneylendingapp.enums.EmploymentStatus;
import com.moneylendingapp.repositories.UserRepository;
import com.moneylendingapp.security.jwt.JwtUtil;
import com.moneylendingapp.services.DefaultUserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
public class DefaultUserServiceImpl implements DefaultUserService {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtUtil jwtTokenUtil;


    @Override
    public UserModel createUser(SignUpRequest signUpDto) {

        String encodedPassword = passwordEncoder.encode(signUpDto.getPassword());

        ensureUsernameIsUnique(signUpDto.getUsername());
        User user = User.builder()
                    .username(signUpDto.getUsername())
                    .address(signUpDto.getAddress())
                    .dob(signUpDto.getDob())
                    .email(signUpDto.getEmail())
                    .employmentStatus(EmploymentStatus.get(signUpDto.getEmploymentStatus()))
                    .firstName(signUpDto.getFirstName())
                    .lastName(signUpDto.getLastName())
                    .password(encodedPassword)
                    .build();

            userRepo.save(user);

        UserModel response = UserModel.builder()
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

    private void ensureUsernameIsUnique(String username) {
        if(userRepo.existsByUsernameIgnoreCase(username)){
            throw new BadRequestException(String.format("Username: %s is taken",username));
        }
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(), loginRequest.getPassword()));

            final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());
            final String jwtToken = jwtTokenUtil.generateToken(userDetails);
            Optional<User> user = userRepo.findByUsername(userDetails.getUsername());

            return LoginResponse.builder().id(user.get().getId()).token(jwtToken).build();

        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public User getLoggedInUser(){

        return userRepo.findByUsername(
                SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getName()).orElseThrow(()
                -> new BadRequestException("No logged in user found"));
    }

}
