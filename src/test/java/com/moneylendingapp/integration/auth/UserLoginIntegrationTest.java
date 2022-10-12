package com.moneylendingapp.integration.auth;

import com.moneylendingapp.MoneyLendingAppApplication;
import com.moneylendingapp.config.TestUtil;
import com.moneylendingapp.dto.requests.LoginRequest;
import com.moneylendingapp.dto.requests.SignUpRequest;
import com.moneylendingapp.dto.responses.LoginResponse;
import com.moneylendingapp.entities.User;
import com.moneylendingapp.repositories.UserRepository;
import com.moneylendingapp.security.jwt.JwtUtil;
import com.moneylendingapp.services.DefaultUserService;
import com.moneylendingapp.services.impl.DefaultUserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = {MoneyLendingAppApplication.class}
)
public class UserLoginIntegrationTest {

    private LoginRequest loginRequest;

    @Autowired
    private UserRepository userRepoTest;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtUtil jwtTokenUtil;

    private DefaultUserService userServiceTest;

    @BeforeEach
    void setUp() {
        userServiceTest = new DefaultUserServiceImpl(userRepoTest, passwordEncoder,
                authenticationManager, userDetailsService, jwtTokenUtil);
        SignUpRequest signUpRequest = TestUtil.newUserRequest();
        userServiceTest.createUser(signUpRequest);
        loginRequest = TestUtil.loginRequest();
    }

    @Test
    void shouldSuccessfullyLoginUser() throws Exception {

        Optional<User> optionalUser = userRepoTest.findByUsername(loginRequest.getUsername());
        LoginResponse loginResponse = userServiceTest.login(loginRequest);

        Assertions.assertTrue(optionalUser.isPresent());
        User user = optionalUser.get();

        Assertions.assertEquals(user.getId(), loginResponse.getId());
        Assertions.assertEquals(jwtTokenUtil.extractUsername(loginResponse.getToken()), user.getUsername());

    }

}
