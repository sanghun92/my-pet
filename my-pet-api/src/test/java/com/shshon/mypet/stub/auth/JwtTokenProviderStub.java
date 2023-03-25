package com.shshon.mypet.stub.auth;

import com.shshon.mypet.auth.infra.JwtTokenProvider;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProviderStub implements JwtTokenProvider {

    @Override
    public String createToken(String email) {
        return email;
    }

    @Override
    public boolean validateToken(String token) {
        return token != null;
    }

    @Override
    public String getPayload(String token) {
        return token;
    }
}
