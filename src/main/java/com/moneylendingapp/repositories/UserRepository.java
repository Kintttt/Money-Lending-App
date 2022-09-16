package com.moneylendingapp.repositories;

import com.moneylendingapp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUsernameIgnoreCase(String username);

    Optional<User> findByUsername(String username);


}
