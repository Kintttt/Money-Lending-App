package com.moneylendingapp.services.impl;

import com.moneylendingapp.config.TestUtil;
import com.moneylendingapp.dto.requests.LoginRequest;
import com.moneylendingapp.dto.requests.SignUpRequest;
import com.moneylendingapp.dto.responses.LoginResponse;
import com.moneylendingapp.dto.responses.UserModel;
import com.moneylendingapp.entities.User;
import com.moneylendingapp.enums.Role;
import com.moneylendingapp.exceptions.BadRequestException;
import com.moneylendingapp.repositories.UserRepository;
import com.moneylendingapp.security.UserDetailsImpl;
import com.moneylendingapp.security.jwt.JwtUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DefaultUserServiceImplTest {

    @Mock
    private UserRepository userRepo;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtil jwtTokenUtil;

    @Mock
    private UserDetailsServiceImpl userDetailsServiceImpl;

    private UserDetails usersDetails;

    @InjectMocks
    private DefaultUserServiceImpl userService;

    private SignUpRequest signUpRequest;
    private LoginRequest loginRequest;
    private User mockedUser;

    @BeforeEach
    void setUp() {
        signUpRequest = TestUtil.newUserRequest();
        loginRequest = TestUtil.loginRequest();
        mockedUser = TestUtil.mockedUser();
        usersDetails = new UserDetailsImpl(mockedUser);
    }

    @Test
    void createUserSuccessfulTest() {

        Mockito.doReturn(false)
                .when(userRepo).existsByUsernameIgnoreCase(anyString());
        Mockito.doReturn(mockedUser)
                .when(userRepo).save(any(User.class));

        UserModel response = userService.createUser(signUpRequest);

        Assertions.assertEquals("Jane", response.getFirstName());

        verify(userRepo).save(any(User.class));
        verify(userRepo).existsByUsernameIgnoreCase(anyString());
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

    @Test
    void loginSuccessfulTest() {

        when(userRepo.findByUsername(loginRequest.getUsername()))
                .thenReturn(Optional.of(mockedUser));

        doReturn(null).when(authenticationManager)
                .authenticate(any(UsernamePasswordAuthenticationToken.class));
        doReturn(usersDetails).when(userDetailsServiceImpl)
                .loadUserByUsername(loginRequest.getUsername());
        doReturn("jyrtdxhc.ghcfrtdsxfcgvhblkvcjfdhxtcjfygku.gyjtcrxerzsg")
                .when(jwtTokenUtil).generateToken(any(UserDetails.class));

        LoginResponse response = userService.login(loginRequest);

        Assertions.assertEquals(Role.USER, response.getRole());
        Assertions.assertNotNull(response.getToken());

        verify(userRepo).findByUsername(anyString());
        verify(userDetailsServiceImpl).loadUserByUsername(anyString());

    }

}