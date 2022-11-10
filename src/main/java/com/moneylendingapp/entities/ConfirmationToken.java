package com.moneylendingapp.entities;

import lombok.*;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import javax.persistence.Id;
import java.time.LocalDateTime;

@RedisHash("token")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConfirmationToken {

    @Id
    private String id;

    private Long userId;

    private LocalDateTime expiresAt;

    private LocalDateTime confirmedAt;

    @TimeToLive
    private Long expiration= 900L;
}
