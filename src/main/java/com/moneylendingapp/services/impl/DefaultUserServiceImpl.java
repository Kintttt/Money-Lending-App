package com.moneylendingapp.services.impl;

import com.moneylendingapp.dto.requests.LoginRequest;
import com.moneylendingapp.dto.requests.SignUpRequest;
import com.moneylendingapp.dto.responses.LoginResponse;
import com.moneylendingapp.dto.responses.UserModel;
import com.moneylendingapp.entities.User;
import com.moneylendingapp.enums.EmploymentStatus;
import com.moneylendingapp.enums.Role;
import com.moneylendingapp.exceptions.BadRequestException;
import com.moneylendingapp.repositories.UserRepository;
import com.moneylendingapp.security.jwt.JwtUtil;
import com.moneylendingapp.services.DefaultUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class DefaultUserServiceImpl implements DefaultUserService {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtTokenUtil;


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
                    .roles(Role.USER)
                    .build();

            userRepo.save(user);

        return UserModel.builder()
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
    }

    private void ensureUsernameIsUnique(String username) {
        if(userRepo.existsByUsernameIgnoreCase(username)){
            throw new BadRequestException(String.format("Username: %s is taken",username));
        }
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) throws Exception {

        User user = userRepo.findByUsername(loginRequest.getUsername())
                .orElseThrow(() ->
                        new UsernameNotFoundException(String.format("Username: %s not found", loginRequest.getUsername())));

        return authenticateUser(loginRequest, user);
    }

    private LoginResponse authenticateUser(LoginRequest loginRequest, User user) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(), loginRequest.getPassword()));

            UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());
            String jwtToken = jwtTokenUtil.generateToken(userDetails);
            log.info("Token = {}", jwtToken);

            return LoginResponse.builder()
                    .id(user.getId())
                    .token(jwtToken)
                    .role(user.getRoles())
                    .build();

        } catch (Exception ex) {
            log.error("Authentication failed for {}. {}", loginRequest.getUsername(), ex.getMessage());
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
