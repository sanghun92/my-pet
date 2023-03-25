package com.shshon.mypet.auth.service;

import com.shshon.mypet.auth.domain.LoginMember;
import com.shshon.mypet.auth.infra.JwtTokenProvider;
import com.shshon.mypet.member.domain.Member;
import com.shshon.mypet.member.domain.MemberRepository;
import com.shshon.mypet.member.exception.AuthorizationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthQueryService {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public LoginMember findMemberByToken(String token) {
        if (!jwtTokenProvider.validateToken(token)) {
            throw new AuthorizationException(token);
        }
        String email = jwtTokenProvider.getPayload(token);
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(AuthorizationException::new);
        return LoginMember.from(member);
    }
}
