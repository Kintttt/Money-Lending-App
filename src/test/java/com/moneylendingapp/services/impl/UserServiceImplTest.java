package com.moneylendingapp.services.impl;

import com.moneylendingapp.config.TestUtil;
import com.moneylendingapp.dto.requests.SignUpRequest;
import com.moneylendingapp.dto.responses.UserModel;
import com.moneylendingapp.entities.User;
import com.moneylendingapp.exceptions.BadRequestException;
import com.moneylendingapp.repositories.UserRepository;
import com.moneylendingapp.security.jwt.JwtUtil;
import com.moneylendingapp.services.DefaultUserService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    private User mockedUser;
    private SignUpRequest signUpRequest;
    @Mock
    private UserRepository userRepoTest;
    private DefaultUserService userServiceTest;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private UserDetailsService userDetailsService;
    @Mock
    private JwtUtil jwtTokenUtil;


    @BeforeEach
    void setUp() {
        userServiceTest = new DefaultUserServiceImpl(userRepoTest, passwordEncoder
                , authenticationManager, userDetailsService, jwtTokenUtil);
        mockedUser = TestUtil.mockedUser();
        signUpRequest  = TestUtil.newUserRequest();
        passwordEncoder  = new BCryptPasswordEncoder();

    }


    @Test
    void createUserTest() {

        Mockito.doReturn(false)
                .when(userRepoTest).existsByUsernameIgnoreCase(anyString());
        Mockito.doReturn(mockedUser)
                .when(userRepoTest).save(any(User.class));

        UserModel response = userServiceTest.createUser(signUpRequest);

        Assertions.assertEquals("Jane", response.getFirstName());

        verify(userRepoTest).save(any(User.class));
        verify(userRepoTest).existsByUsernameIgnoreCase(anyString());
    }

    @Test
    void usernameTakenTest() {

        Mockito.doReturn(true)
                .when(userRepoTest).existsByUsernameIgnoreCase(anyString());

        assertThatThrownBy(() ->  userServiceTest.createUser(signUpRequest))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("Username: " + signUpRequest.getUsername() +" is taken");

    }

}