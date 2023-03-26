package com.shshon.mypet.stub.auth;

import com.shshon.mypet.auth.domain.HttpRequestClient;
import com.shshon.mypet.auth.domain.LoginMember;
import com.shshon.mypet.auth.infra.JwtTokenProvider;
import com.shshon.mypet.member.domain.Member;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProviderStub implements JwtTokenProvider {

    @Override
    public String createToken(Member member, HttpRequestClient client) {
        return "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QG5hdmVyLmNvbSIsImlhdCI6MTY3OTgzNDM3MiwiZXhwIjoxNjc5ODM2MTcyfQ.gqlMnpsIUQV1O3C0kEHx8DycUSRUV0VtL2yja2MhrxY";
    }

    @Override
    public boolean validateToken(String token) {
        return true;
    }

    @Override
    public LoginMember claimsToLoginMember(String token) {
        return new LoginMember(1L, "test@test.com", "0.0.0.1", "PC");
    }
}
