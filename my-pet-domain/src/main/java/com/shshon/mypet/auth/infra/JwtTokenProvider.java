package com.shshon.mypet.auth.infra;


import com.shshon.mypet.auth.domain.HttpRequestClient;
import com.shshon.mypet.auth.domain.LoginMember;
import com.shshon.mypet.member.domain.Member;

public interface JwtTokenProvider {

    String createToken(Member member, HttpRequestClient client);

    boolean validateToken(String token);

    LoginMember claimsToLoginMember(String token);
}
