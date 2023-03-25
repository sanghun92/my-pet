package com.shshon.mypet.stub.auth;

import com.shshon.mypet.auth.domain.LoginMember;
import com.shshon.mypet.auth.service.AuthQueryService;

public class AuthQueryServiceStub extends AuthQueryService {

    public AuthQueryServiceStub() {
        super(null, null);
    }

    @Override
    public LoginMember findMemberByToken(String token) {
        return LoginMember.builder()
                .id(1L)
                .build();
    }
}
