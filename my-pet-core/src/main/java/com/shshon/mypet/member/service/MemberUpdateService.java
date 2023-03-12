package com.shshon.mypet.member.service;

import com.shshon.mypet.member.domain.MemberRepository;
import com.shshon.mypet.member.dto.MemberDto;
import com.shshon.mypet.member.exception.MemberNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class MemberUpdateService {

    private final MemberRepository memberRepository;

    public MemberUpdateService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public void certificateMember(String code) {
        memberRepository.findByCertificationCode(UUID.fromString(code))
                .orElseThrow(MemberNotFoundException::new)
                .onCertificate();
    }

    public void changePassword(MemberDto member) {
        memberRepository.findByEmail(member.getEmail())
                .orElseThrow(MemberNotFoundException::new)
                .changePassword(member.getPassword());
    }
}
