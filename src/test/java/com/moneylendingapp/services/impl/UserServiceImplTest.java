package com.moneylendingapp.services.impl;

import com.moneylendingapp.TestUtil;
import com.moneylendingapp.dto.requests.SignUpRequest;
import com.moneylendingapp.dto.responses.SignUpResponse;
import com.moneylendingapp.entities.User;
import com.moneylendingapp.exceptions.BadRequestException;
import com.moneylendingapp.repositories.UserRepository;
import com.moneylendingapp.services.DefaultUserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    private User mockedUser;
    private SignUpRequest signUpRequest;
    @Mock
    private UserRepository userRepoTest;
    private DefaultUserService userServiceTest;
    @Mock
    private final PasswordEncoder passwordEncoder  = new BCryptPasswordEncoder();


    @BeforeEach
    void setUp() {
        userServiceTest = new DefaultUserServiceImpl(userRepoTest, passwordEncoder);
        mockedUser = TestUtil.mockedUser();
        signUpRequest  = TestUtil.newUserRequest();

    }


    @Test
    void createUserTest() {

        Mockito.doReturn(false)
                .when(userRepoTest).existsByUsername(anyString());
        Mockito.doReturn(mockedUser)
                .when(userRepoTest).save(any(User.class));

        SignUpResponse response = userServiceTest.createUser(signUpRequest);

        Assertions.assertEquals("Jane", response.getFirstName());

        verify(userRepoTest).save(any(User.class));
        verify(userRepoTest).existsByUsername(anyString());
    }

    @Test
    void usernameTakenTest() {

        Mockito.doReturn(true)
                .when(userRepoTest).existsByUsername(anyString());

        Assertions.assertThrows(BadRequestException.class, () -> {
            userServiceTest.createUser(signUpRequest);
        }, "Username is taken");
    }

}