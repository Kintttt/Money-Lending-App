package com.moneylendingapp.integration;

import com.moneylendingapp.MoneyLendingAppApplication;
import com.moneylendingapp.config.MySqlTestContainerSetup;
import com.moneylendingapp.config.TestUtil;
import com.moneylendingapp.dto.requests.SignUpRequest;
import com.moneylendingapp.dto.responses.SignUpResponse;
import com.moneylendingapp.entities.User;
import com.moneylendingapp.repositories.UserRepository;
import com.moneylendingapp.services.DefaultUserService;
import com.moneylendingapp.services.impl.DefaultUserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = {MoneyLendingAppApplication.class}
)
public class UserServiceIntegrationTest extends MySqlTestContainerSetup{

    private User mockedUser;
    private SignUpRequest signUpRequest;

    @Autowired
    private UserRepository userRepoTest;
    private DefaultUserService userServiceTest;

    @Autowired
    private final PasswordEncoder passwordEncoder  = new BCryptPasswordEncoder();

    @BeforeEach
    void setUp() {
        userServiceTest = new DefaultUserServiceImpl(userRepoTest, passwordEncoder);
        mockedUser = TestUtil.mockedUser();
        signUpRequest  = TestUtil.newUserRequest();
    }

    @Test
    void shouldSuccessfullyCreateAUser() {

        SignUpResponse response = userServiceTest.createUser(signUpRequest);
        Optional<User> optionalUser = userRepoTest.findById(response.getId());

        Assertions.assertTrue(optionalUser.isPresent());
        User user = optionalUser.get();
        Assertions.assertEquals(user.getUsername(), signUpRequest.getUsername());
        Assertions.assertEquals(user.getFirstName(), signUpRequest.getFirstName());

    }


}
