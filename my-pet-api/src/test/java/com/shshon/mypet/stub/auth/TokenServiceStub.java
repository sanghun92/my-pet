package com.shshon.mypet.stub.auth;

import com.shshon.mypet.auth.domain.LoginMember;
import com.shshon.mypet.auth.service.TokenService;

public class TokenServiceStub extends TokenService {

    public TokenServiceStub() {
        super(null, null, null);
    }

    @Override
    public LoginMember findLoginMemberBy(String token) {
        return new LoginMember(1L, "test@test.com", "0.0.0.1", "PC");
    }
}
