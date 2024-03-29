package com.shshon.mypet.member.service;

import com.shshon.mypet.member.domain.Member;
import com.shshon.mypet.member.domain.MemberRepository;
import com.shshon.mypet.member.dto.MemberDto;
import com.shshon.mypet.member.exception.AuthorizationException;
import com.shshon.mypet.member.exception.InValidMemberException;
import com.shshon.mypet.member.exception.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Member createMember(MemberDto memberDto) {
        validateNewMember(memberDto);
        return memberRepository.save(memberDto.toMember());
    }

    private void validateNewMember(MemberDto memberDto) {
        if (memberRepository.findByEmail(memberDto.email()).isPresent()) {
            throw InValidMemberException.duplicatedEmail();
        }
    }

    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(AuthorizationException::new);
    }

    public Member findById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(MemberNotFoundException::new);
    }
}
