package com.shshon.mypet.auth.domain;

import java.util.Optional;

public interface RefreshTokenRepository {

    void save(RefreshToken refreshToken, long timeToLiveSeconds);

    Optional<RefreshToken> findByToken(String refreshToken);
}
