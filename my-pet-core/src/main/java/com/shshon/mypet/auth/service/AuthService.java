package com.shshon.mypet.auth.service;

import com.shshon.mypet.auth.domain.LoginMember;
import com.shshon.mypet.auth.dto.TokenDto;
import com.shshon.mypet.auth.infra.JwtTokenProvider;
import com.shshon.mypet.member.domain.Member;
import com.shshon.mypet.member.domain.MemberRepository;
import com.shshon.mypet.member.exception.AuthorizationException;
import com.shshon.mypet.member.exception.MemberNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(MemberRepository memberRepository,
                       JwtTokenProvider jwtTokenProvider) {
        this.memberRepository = memberRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public TokenDto login(String email, String password) {
        memberRepository.findByEmail(email)
                .orElseThrow(MemberNotFoundException::new)
                .authenticate(password);

        return TokenDto.of(jwtTokenProvider.createToken(email));
    }

    public LoginMember findMemberByToken(String token) {
        if(!jwtTokenProvider.validateToken(token)) {
            throw new AuthorizationException();
        }
        String email = jwtTokenProvider.getPayload(token);
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(MemberNotFoundException::new);
        return LoginMember.from(member);
    }
}
