package com.moneylendingapp.util;

import com.moneylendingapp.entities.User;
import com.moneylendingapp.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Clock;
import java.util.function.Function;

@Configuration
@RequiredArgsConstructor
public class Configs {

    private final UserRepository userRepository;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public Clock clock() {
        return Clock.systemDefaultZone();
    }

    @Bean
    public Function<UserDetails, User> fetchUser() {
        return (principal -> {

            String name = principal.getUsername();
            return userRepository.findByUsername(name).orElseThrow(() ->
                    new UsernameNotFoundException(String.format("Username: %s not found", name)));
        });
    }
}
