package com.moneylendingapp.repositories;

import com.moneylendingapp.entities.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.Optional;
import static com.moneylendingapp.enums.EmploymentStatus.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepoTest;

    @AfterEach
    void tearDown() {
        userRepoTest.deleteAll();
    }

    @Test
    void checkWhenUsernameExistsTest() {

        String username = "Kintt";
        User user = User.builder()
                .firstName("Temi")
                .lastName("Kint")
                .username(username)
                .password("123456")
                .employmentStatus(CONTRACT)
                .email("tems@gmail.com")
                .build();

        userRepoTest.save(user);

        Optional<User> usernameCheck = userRepoTest.findByUsername(username);

        assertThat(usernameCheck).isPresent();
    }

    @Test
    void checkWhenUsernameDoesNotExistTest() {

        String username = "Kintt";
        Optional<User> usernameCheck = userRepoTest.findByUsername(username);
        assertThat(usernameCheck).isNotPresent();
    }
}