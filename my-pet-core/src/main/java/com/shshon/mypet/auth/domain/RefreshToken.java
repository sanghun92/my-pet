package com.shshon.mypet.auth.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.persistence.Id;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;
import java.util.UUID;

@RedisHash(value = "refreshToken")
public record RefreshToken(
        @Id
        String token,
        Long memberId,
        String memberEmail,
        String ip,
        @JsonSerialize(using = LocalDateTimeSerializer.class)
        @JsonDeserialize(using = LocalDateTimeDeserializer.class)
        LocalDateTime issuedAt
) {

    public RefreshToken(Long memberId, String memberEmail, String ip) {
        this(
                UUID.randomUUID().toString(),
                memberId,
                memberEmail,
                ip,
                LocalDateTime.now()
        );
    }
}
