package com.shshon.mypet.auth.infra;

import com.shshon.mypet.auth.domain.RefreshToken;
import com.shshon.mypet.auth.domain.RefreshTokenRepository;
import com.shshon.mypet.util.ObjectMapperDecorator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Repository
@RequiredArgsConstructor
public class RefreshTokenRepositoryImpl implements RefreshTokenRepository {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapperDecorator mapper;

    public void save(RefreshToken refreshToken, long timeToLiveSeconds) {
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(refreshToken.token(), refreshToken, timeToLiveSeconds, TimeUnit.SECONDS);
    }

    public Optional<RefreshToken> findByToken(String refreshToken) {
        Object findRefreshToken = redisTemplate.opsForValue().get(refreshToken);
        return mapper.convertValue(findRefreshToken, RefreshToken.class);
    }
}
