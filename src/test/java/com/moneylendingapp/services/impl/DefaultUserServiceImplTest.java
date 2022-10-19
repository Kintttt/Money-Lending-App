package com.moneylendingapp.services.impl;

import com.moneylendingapp.config.TestUtil;
import com.moneylendingapp.dto.requests.LoginRequest;
import com.moneylendingapp.dto.requests.SignUpRequest;
import com.moneylendingapp.exceptions.BadRequestException;
import com.moneylendingapp.repositories.UserRepository;
import com.moneylendingapp.security.jwt.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DefaultUserServiceImplTest {

    @Mock
    private UserRepository userRepo;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private JwtUtil jwtTokenUtil;

    @InjectMocks
    private DefaultUserServiceImpl userService;

    private SignUpRequest signUpRequest;
    private LoginRequest loginRequest;

    @BeforeEach
    void setUp() {
        signUpRequest = TestUtil.newUserRequest();
        loginRequest = TestUtil.loginRequest();
    }

    @Test
    void createUserFails_whenUsernameExistsAlready() {
        when(passwordEncoder.encode(signUpRequest.getPassword()))
                .thenReturn("ryydhhhhdhhhjj");
        when(userRepo.existsByUsernameIgnoreCase(signUpRequest.getUsername()))
                .thenReturn(true);

        assertThatThrownBy(() -> userService.createUser(signUpRequest))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("Username: " + signUpRequest.getUsername() + " is taken");
    }

    @Test
    void loginFails_whenUsernameDoesNotExist() {
        when(userRepo.findByUsername(loginRequest.getUsername()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.login(loginRequest))
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessage("Username: " + signUpRequest.getUsername() + " not found");

    }
}