package com.moneylendingapp.services.impl;

import com.moneylendingapp.TestUtil;
import com.moneylendingapp.dto.requests.SignUpRequest;
import com.moneylendingapp.dto.responses.SignUpResponse;
import com.moneylendingapp.entities.User;
import com.moneylendingapp.exceptions.BadRequestException;
import com.moneylendingapp.repositories.UserRepository;
import com.moneylendingapp.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    private User mockedUser;
    private SignUpRequest signUpRequest;
    @Mock
    private UserRepository userRepoTest;
    private UserService userServiceTest;
    private final PasswordEncoder passwordEncoder  = new BCryptPasswordEncoder();


    @BeforeEach
    void setUp() {
        userServiceTest = new UserServiceImpl(userRepoTest, passwordEncoder);
        mockedUser = TestUtil.mockedUser();
        signUpRequest  = TestUtil.newUserRequest();

    }


    @Test
    void createUserTest() {

        Mockito.doReturn(Optional.empty())
                .when(userRepoTest).findByUsername(anyString());
        Mockito.doReturn(mockedUser)
                .when(userRepoTest).save(any(User.class));

        SignUpResponse response = userServiceTest.createUser(signUpRequest);

        Assertions.assertEquals("Jane", response.getFirstName());

        verify(userRepoTest).save(any(User.class));
        verify(userRepoTest).findByUsername(anyString());
    }

    @Test
    void usernameTakenTest() {

        Mockito.doReturn(Optional.of(mockedUser))
                .when(userRepoTest).findByUsername(anyString());

        Assertions.assertThrows(BadRequestException.class, () -> {
            userServiceTest.createUser(signUpRequest);
        });
    }

}