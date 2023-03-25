package com.shshon.mypet.auth.infra;


public interface JwtTokenProvider {

    String createToken(String email);

    boolean validateToken(String token);

    String getPayload(String token);
}
