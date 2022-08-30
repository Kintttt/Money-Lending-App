package com.moneylendingapp.services.impl;

import com.moneylendingapp.dto.SignUpRequest;
import com.moneylendingapp.entities.User;
import com.moneylendingapp.repositories.UserRepository;
import com.moneylendingapp.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static com.moneylendingapp.enums.EmploymentStatus.CONTRACT;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepoTest;
    private UserService userServiceTest;

    @BeforeEach
    void setUp() {
        userServiceTest = new UserServiceImpl(userRepoTest);
    }


    @Test
    void createUser() {
        //given
        User mockedUser = User.builder()
                .username("Kiint")
                .firstName("Temi")
                .lastName("Kint")
                .employmentStatus(CONTRACT)
                .email("tops@gmail.com")
                .password("1234567")
                .build();
        Mockito.doReturn(Optional.empty()).when(userRepoTest).findByUsername(anyString());
        Mockito.doReturn(mockedUser).when(userRepoTest).save(any(User.class));

        SignUpRequest newUserRequest = SignUpRequest.builder()
                .username("Kiint")
                .firstName("Temi")
                .lastName("Kint")
                .employmentStatus("Contract")
                .email("tops@gmail.com")
                .password("1234567")
                .build();

        //when
        String message = userServiceTest.createUser(newUserRequest);
        //then
        Assertions.assertEquals("User saved successfully", message);
        verify(userRepoTest, times(1)).save(any(User.class));
        verify(userRepoTest, times(1)).findByUsername(anyString());
    }













    //        User user = User.builder()
//                .firstName("Temi")
//                .lastName("Kint")
//                .username("Kintt")
//                .password("123456")
//                .employmentStatus(CONTRACT)
//                .email("tems@gmail.com")
//                .build();
//        when(userRepoTest.save(user)).thenReturn(user);
//        assertEquals(user, userServiceTest.createUser(user));


}