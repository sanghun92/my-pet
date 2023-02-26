package com.shshon.mypet.member.service;

import com.shshon.mypet.member.domain.MemberRepository;
import com.shshon.mypet.member.exception.MemberNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class UpdateMemberService {

    private final MemberRepository memberRepository;

    public UpdateMemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public void certificateMember(String code) {
        memberRepository.findByCertificationCode(UUID.fromString(code))
                .orElseThrow(MemberNotFoundException::new)
                .onCertificate();
    }
}
