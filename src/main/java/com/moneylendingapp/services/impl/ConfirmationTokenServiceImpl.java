package com.moneylendingapp.services.impl;

import com.moneylendingapp.advice.ApiResponseEnvelope;
import com.moneylendingapp.entities.ConfirmationToken;
import com.moneylendingapp.entities.User;
import com.moneylendingapp.exceptions.UserNotFoundException;
import com.moneylendingapp.repositories.ConfirmationTokenRepository;
import com.moneylendingapp.repositories.UserRepository;
import com.moneylendingapp.services.ConfirmationTokenService;
import com.moneylendingapp.util.ApplicationPropertyConfig;
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
public class ConfirmationTokenServiceImpl implements ConfirmationTokenService {

    private final ConfirmationTokenRepository tokenRepo;
    private final EmailSender emailService;
    private final UserRepository userRepo;
    private final EmailBuilder emailBuilder;
    private final ApplicationPropertyConfig appConfig;


    @Override
    public void sendConfirmationToken(User user) {

        String token = UUID.randomUUID().toString();
        log.info("Confirmation Token: " + token);

        ConfirmationToken confirmationToken = ConfirmationToken.builder()
                .userId(user.getId())
                .id(token)
                .expiresAt(LocalDateTime.now().plusMinutes(appConfig.getTokenExpireAfter()))
                .build();

        ConfirmationToken db = tokenRepo.save(confirmationToken);
        String tokenId = String.valueOf(db.getId());
        String link = appConfig.getTokenVerificationLink() + tokenId;

        emailService.send(
                user.getEmail(),
                emailBuilder.buildEmail(user.getFirstName(), link));
    }


    @Transactional
    public ApiResponseEnvelope confirmToken(String token) {

        ConfirmationToken confirmationToken = tokenRepo
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