package com.moneylendingapp.integration.auth;

import com.moneylendingapp.MoneyLendingAppApplication;
import com.moneylendingapp.config.TestUtil;
import com.moneylendingapp.dto.requests.SignUpRequest;
import com.moneylendingapp.dto.responses.UserModel;
import com.moneylendingapp.entities.User;
import com.moneylendingapp.repositories.UserRepository;
import com.moneylendingapp.security.jwt.JwtUtil;
import com.moneylendingapp.services.UserService;
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

public class SignUpIntegrationTest {

    private SignUpRequest signUpRequest;

    @Autowired
    private UserRepository userRepoTest;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private UserService userServiceTest;



    @BeforeEach
    void setUp() {
        userServiceTest = new DefaultUserServiceImpl(userRepoTest, passwordEncoder,
                authenticationManager, userDetailsService, jwtTokenUtil);
        signUpRequest  = TestUtil.newUserRequest();
    }

    @Test
    void shouldSuccessfullyCreateAUser() {

        UserModel response = userServiceTest.createUser(signUpRequest);
        Optional<User> optionalUser = userRepoTest.findById(response.getId());

        Assertions.assertTrue(optionalUser.isPresent());
        User user = optionalUser.get();
        Assertions.assertEquals(user.getUsername(), signUpRequest.getUsername());
        Assertions.assertEquals(user.getFirstName(), signUpRequest.getFirstName());

    }
}
