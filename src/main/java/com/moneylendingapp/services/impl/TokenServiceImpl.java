package com.moneylendingapp.services.impl;

import com.moneylendingapp.advice.ApiResponseEnvelope;
import com.moneylendingapp.entities.Token;
import com.moneylendingapp.entities.User;
import com.moneylendingapp.exceptions.UserNotFoundException;
import com.moneylendingapp.repositories.TokenRepository;
import com.moneylendingapp.repositories.UserRepository;
import com.moneylendingapp.services.TokenService;
import com.moneylendingapp.util.EmailBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final TokenRepository tokenRepo;
    private final EmailSender emailService;
    private final UserRepository userRepo;
    private final EmailBuilder emailBuilder;


    @Override
    public void saveToken(User user) {

        String token = UUID.randomUUID().toString();
        log.info("Token: " + token);

        Token confirmationToken = Token.builder()
                .userId(user.getId())
                .id(token)
                .expiresAt(LocalDateTime.now().plusMinutes(15))
                .build();

        Token db = tokenRepo.save(confirmationToken);

        String tokenId = String.valueOf(db.getId());

        String link = "http://localhost:8082/api/v1/auth/confirm?token=" + tokenId;

        emailService.send(
                user.getEmail(),
                emailBuilder.buildEmail(user.getFirstName(), link));
    }


    @Transactional
    public ApiResponseEnvelope confirmToken(String token) {

        Token confirmationToken = tokenRepo
                .findById(token)
                .orElseThrow(() ->
                        new IllegalStateException("Token not found or is expired"));

        User user = userRepo.findById(confirmationToken
                .getUserId())
                .orElseThrow(() ->
                        new UserNotFoundException("User not found")
                );

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if(confirmationToken.getConfirmedAt() != null){
            throw new IllegalStateException("Email already confirmed");
        }


        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("Token expired");
        }

        confirmationToken.setConfirmedAt(LocalDateTime.now());
        tokenRepo.save(confirmationToken);

        user.setEmailVerified(true);
        user.setDateEmailVerified(LocalDateTime.now());

        System.out.println(confirmationToken.getConfirmedAt() + " is confirmation time");
        return ApiResponseEnvelope.builder()
                .result("Email Confirmed")
                .responseDate(LocalDateTime.now())
                .successStatus(true)
                .build();
    }

}
